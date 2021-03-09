package com.swpu.pojo;
//描述分页对象
public class MyPage {
    //定义页码，赋初始值1，第一页
    private int page=1;
    //定义每页显示的数据条数，初值5，每页5条
    private int row = 5;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}
