package com.swpu.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.swpu.dao.UserInfoDao;
import com.swpu.pojo.UserInfo;
import com.swpu.util.MdFive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service //UserInfoServiceImpl的对象创建交给spring管理
public class UserInfoServiceImpl implements UserInfoService {
    //创建一个userInfoDao的实现类对象
    @Autowired(required = false)
    UserInfoDao userInfoDao;

    //创建加密工具类对象
    @Autowired
    MdFive mdFive;

    //获取发件人邮箱
    @Value("${spring.mail.username}")
    String sendEmail;

    //创建发送邮件的对象
    @Autowired
    JavaMailSender javaMailSender;

    //创建操作redis库的操作对象
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    /**
     * 检查用户是否已经被锁定，如果是，返回剩余锁定时间，如果否，返回-1
     * @param username  username
     * @return  时间
     */
    private long getUserLoginTimeLock(String username) {
        String key = "user:" + username + ":lockTime";
        //获取key 过期时间
        long lockTime = redisTemplate.getExpire(key, TimeUnit.SECONDS);

        if(lockTime > 0){//查询用户是否已经被锁定，如果是，返回剩余锁定时间，如果否，返回-1
            return lockTime;
        }else{
            return -1;
        }
    }
    /**
     * 获取当前用户已失败次数
     * @param username  username
     * @return  已失败次数
     */
    private int getUserFailCount(String username){
        String key = "user:" + username + ":failCount";
        //从redis中获取当前用户已失败次数
        Object object = redisTemplate.opsForValue().get(key);
        if(object != null){
            return (int)object;
        }else{
            return -1;
        }
    }
    /**
     * 设置失败次数
     * @param username  username
     */
    private void setFailCount(String username){
        //获取当前用户已失败次数
        long count = this.getUserFailCount(username);
        String key = "user:" + username + ":failCount";
        if(count < 0){//判断redis中是否有该用户的失败登陆次数，如果没有，设置为1，过期时间为1分钟，如果有，则次数+1
            redisTemplate.opsForValue().set(key,1,60,TimeUnit.SECONDS);
        }else{
            redisTemplate.opsForValue().increment(key,new Double(1));
        }
    }
    @Override
    public String login(UserInfo user,HttpServletRequest request) {//登录

        //判断用户是否被锁定
        if(this.getUserLoginTimeLock(user.getUserName())>0){
            return "用户被锁定，请"+this.getUserLoginTimeLock(user.getUserName())+"秒后重试";
        }else {
            UserInfo u = userInfoDao.selectByUserName(user);
            if(u==null){
                return "用户名错误";
            }
            //加密密码
            String pwd = mdFive.encrpt(user.getUserPwd(),u.getSalt());
            //设值
            user.setUserPwd(pwd);
            //查询数据层的登录方法，并且拿到返回值
            UserInfo userInfo = userInfoDao.login(user);
            //若查询到值，userInfo不等于null
            if(userInfo!=null){
                //将用户信息放入session中
                HttpSession session = request.getSession();
                session.setAttribute("user",userInfo);
                return "登录成功！";
            }else {
                if(getUserFailCount(user.getUserName())>=3){
                     String key = "user:" + user.getUserName() + ":lockTime";
                     redisTemplate.opsForValue().set(key, "时间",120,TimeUnit.SECONDS);
                     return "用户被锁定，请120秒后重试";
                }else{
                    setFailCount(user.getUserName());
                    return "登录失败！已经失败"+getUserFailCount(user.getUserName())+"次，失败三次后用户锁定";
                }
            }
        }
    }

    @Override
    public String register(UserInfo user) {//注册


        int num = userInfoDao.valName(user);
        if(num>0){
            return "用户被注册";
        }else{
            //自动生产一个盐值
            Random rd = new Random();
            String salt = rd.nextInt(100000)+"";
            //加密密码
            String pwd = mdFive.encrpt(user.getUserPwd(),salt);
            //设值
            user.setUserPwd(pwd);
            user.setSalt(salt);
            int n = userInfoDao.register(user);
            if(n>0){
                return "注册成功";
            }
        }
        return "注册失败";
    }

    @Override
    public HashMap<String, Object> sendCode(String email, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            //从session中获取当前用户信息
            HttpSession session = request.getSession();
            //创建普通邮件对象
            SimpleMailMessage message = new SimpleMailMessage();
            //设置发件人邮箱
            message.setFrom(sendEmail);
            //设置收件人邮箱
            message.setTo(email);
            //设置邮件主题
            message.setSubject("教务处信息管理系统验证码");
            // 生成随机验证码
            Random rd = new Random();
            String valCode = rd.nextInt(9999)+"";
            //设置邮件正文
            message.setText("你的验证码是："+valCode+"，若非本人操作，请忽略");
            //发送邮件
            javaMailSender.send(message);
            //发送成功
            //把验证码存入session中
            //session.setAttribute("valCode",valCode);

            //验证码放入redis
            redisTemplate.opsForValue().set("valCode",valCode,180,TimeUnit.SECONDS);
            map.put("info","发送成功");
            return map;

        } catch (Exception e) {
            System.out.println("发送邮件时发生异常！");
            e.printStackTrace();
        }
        map.put("info","发送邮件异常");
        return map;
    }

    @Override
    public HashMap<String, Object> select(UserInfo user) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        //1设置分页参数
        PageHelper.startPage(user.getPage(),user.getRow());
        List<UserInfo> list = new ArrayList<>();
        if(user.getConValue().equals("")){

            list = userInfoDao.select();
            //判断redis缓存中是否有数据
            String key = "wen";
            Object obj = redisTemplate.opsForValue().get(key);
/*            if(obj==null){
                list = userInfoDao.select();
                redisTemplate.opsForValue().set(key,list,10,TimeUnit.MINUTES);
            }else{
                list = (List<UserInfo>) obj;
            }*/
        }else{
            ////2查询数据库表数据  根据用户选择的条件判断用户
            if(user.getCondition().equals("编号")){
                //设置用户输入的查询条件
                user.setUserId(Integer.parseInt(user.getConValue()));
                list = userInfoDao.findByUserId(user);
            }else if(user.getCondition().equals("用户名")){
                //设置用户输入的查询条件
                user.setUserName(user.getConValue());
                list = userInfoDao.findByUserName(user);
            }else{
                list = userInfoDao.select();
            }
        }

        //3查询的数据转换成分页对象
        PageInfo<UserInfo> page = new PageInfo<>(list);

        //获取分页的当前页的集合
        map.put("list",page.getList());
        //总条数
        map.put("total",page.getTotal());
        //总页数
        map.put("totalPage",page.getPages());
        //上一页
        if(page.getPrePage()==0){
            map.put("pre",1);
        }else{
            map.put("pre",page.getPrePage());
        }
        //下一页
        if(page.getNextPage()==0){//最后一页时固定最后一页
            map.put("next",page.getPages());
        }else{//不是最后一页，获取下一页
            map.put("next",page.getNextPage());
        }
        //当前页
        map.put("currentPage",page.getPageNum());
        //当前页数据条数
        map.put("curPageSize",page.getSize());

        map.put("row",user);
        return map;
    }

    @Override
    public UserInfo selectByUserId(UserInfo user) {

        return userInfoDao.selectByUserId(user);
    }

    @Override
    public String update(UserInfo user) {

        int v = userInfoDao.valName(user);
        //验证是否重名
        if(v==0){
            int num = userInfoDao.update(user);
            if(num>0){
                return "修改成功";
            }
        }else {
            return "用户名重名";
        }
            return "修改失败";
    }

    @Override
    public String del(UserInfo user) {
        int n = userInfoDao.del(user);
        if(n>0){
            return "删除成功";
        }else {
            return "删除失败";
        }
    }

    @Override
    //修改密码
    public String updatePwd(UserInfo user,HttpServletRequest request) {
        //取出存入session中的用户信息
        HttpSession session = request.getSession();
        UserInfo u = (UserInfo) session.getAttribute("user");
        //取出正确的密码，已经加过密了
        String pwd = u.getUserPwd();

        String oo = user.getOldPwd();
        String ss = u.getSalt();

        //加密输入的旧密码
        String oldPwd = mdFive.encrpt(user.getOldPwd(),u.getSalt());
        //判断用户输入的旧密码是否正确
        if(oldPwd.equals(pwd))
        {//判断旧密码（暗文）是否正确
            //加密输入的新密码
            String p = mdFive.encrpt(user.getNewPwd(),u.getSalt());
            //存入加密后的新密码
            u.setUserPwd(p);
            //更新数据库
            int n = userInfoDao.updatePwd(u);
            if(n>0){
                return "修改密码成功";
            }else {
                return "修改密码失败";
            }
        }else{
            return "旧密码错误";
        }
    }

    //修改头像
    @Override
    public String updateAvatar(UserInfo user, HttpServletRequest request) {
        //取出存入session中的用户信息
        HttpSession session = request.getSession();
        UserInfo u = (UserInfo) session.getAttribute("user");
        //存入要修改的用户id
        user.setUserId(u.getUserId());
        int n = userInfoDao.updateAvatar(user);
        if(n>0){
            return "保存成功";
        }else {
            return "保存失败";
        }
    }

}
