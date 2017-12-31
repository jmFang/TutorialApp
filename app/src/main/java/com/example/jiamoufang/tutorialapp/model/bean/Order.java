package com.example.jiamoufang.tutorialapp.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by duang1996 on 2017/12/22.
 */


/* 家教订单类，包含家教订单发布的详细信息
 * 数据关联：一份订单对应一个学生
 */

public class Order extends BmobObject {

    /*
     * 订单关联的学员,需要用到性别、住址这两个信息
     */
    private User user;

    /* 订单关联的老师，接该订单的老师
     *  如果订单还没人接的话则为空
     */
    private User teacher;

    /*订单状态，是否已经有人接此单
     * false:没人接 ； true:已经有人接了
     */
    private Boolean status;

    /*
     *科目
     */
    private String subject;

    /* 教授的年级:
     * 小学：1~6； 初中7~9； 高中：10~12； 大学：13； 其他：0
     */
    private Integer grade;


    /*
     *薪酬，可以是一个范围的表示
     */
    private String salary;

    /*
     * 授课时间
     */
    private String time;

    /*对老师的性别要求
    * 男：true ； 女： false
    * */
    private Boolean sex;

    /*
     *x学员的基本情况描述
     */
    private String des;

    /*教学方式
     *老师上门：true; 学生上门：false
     */
    private Boolean mode;
    /*
    * 教学地址（与用户User Class的地址区别开）
    * */
    private String address;

    /*
    * 所期望的教师的学历
    * */
    private String educatedLevel;

    public void setEducatedLevel(String educatedLevel) {
        this.educatedLevel = educatedLevel;
    }

    public String getEducatedLevel() {
        return educatedLevel;
    }

    public Order() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getTeacher() { return teacher;}

    public void setTeacher(User teacher) { this.teacher = teacher;}

    public Boolean getStatus() { return status;}

    public void setStatus(Boolean status) {this.status = status;}

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSalary() {
        return salary;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Boolean getMode() {
        return mode;
    }

    public void setMode(Boolean mode) {
        this.mode = mode;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
