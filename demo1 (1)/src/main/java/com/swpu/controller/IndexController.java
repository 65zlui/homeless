package com.swpu.controller;

import com.swpu.pojo.UserInfo;
import com.swpu.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Controller
public class IndexController {
    @Autowired
    UserInfoService userInfoService;
    //获取图片上传的配置路径
    @Value("${file.address}")
    String fileAddress;
    //静态资源文件访问路径
    @Value("${file.staticPath}")
    String upload;

    //访问index页面
    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }
    //访问修改密码页面
    @RequestMapping("/updatePwd")
    public String updatePwd(){
        return "updatePwd";
    }
    //访问修改头像页面
    @RequestMapping("/updateAvatar")
    public String updateAvatar(){
        return "updateAvatar";
    }
    //处理修改密码的ajax请求
    @RequestMapping("/updatePwdAjax")
    @ResponseBody
    public HashMap<String,Object> updatePwdAjax(UserInfo user, HttpServletRequest request){
        HashMap<String,Object> map = new HashMap<String,Object>();
        String info = userInfoService.updatePwd(user,request);
        map.put("info",info);
        return map;
    }

    @RequestMapping("/getUser")
    @ResponseBody
    public HashMap<String,Object> getUser(HttpServletRequest request){
        HashMap<String,Object> map = new HashMap<String,Object>();
        HttpSession session = request.getSession();
        UserInfo user = (UserInfo)session.getAttribute("user");
        map.put("info",user.getUserName());
        return map;
    }

    //头像更改
    @RequestMapping("/upload")
    @ResponseBody
    public HashMap<String,Object> upload(MultipartFile file){
        HashMap<String,Object> map = new HashMap<>();
        try {
            //定义文件上传的目录
            String timeDir="";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            timeDir = sdf.format(new Date());
            //定义文件上传的前缀
            String pre="";
            //保证文件上传到服务器名字唯一
            pre = UUID.randomUUID()+"";
            //获取文件后缀名
            //记录图片后缀
            String hou="";
            if(file!=null){
                String originalName = file.getOriginalFilename();//.jpg
                hou=originalName.substring(originalName.lastIndexOf(".")+1);//jpg
            }
            //定义文件上传的全路径
            String filePath=fileAddress+"\\"+timeDir+"\\"+pre+"."+hou;

            //取当前目录 filePath
            File f = new File(filePath);

            //判断目录是否存在，不存在就创建目录
            if(!f.isDirectory()){
                //创建目录
                f.mkdirs();
            }
            //上传文件
            file.transferTo(f);
            map.put("code",0);
            //返回给前端 用户访问这个图片的路径
            String path = upload+"\\"+timeDir+"\\"+pre+"."+hou;
            map.put("src",path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    //处理修改密码的ajax请求
    @RequestMapping("/saveAvatarAjax")
    @ResponseBody
    public HashMap<String,Object> saveAvatarAjax(UserInfo user, HttpServletRequest request){
        HashMap<String,Object> map = new HashMap<String,Object>();
        String info = userInfoService.updateAvatar(user,request);
        map.put("info",info);
        return map;
    }
}
