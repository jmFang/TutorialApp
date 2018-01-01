package com.example.jiamoufang.tutorialapp.adapter.entities;

/**
 * Created by jiamoufang on 2017/12/31.
 * 学员发布的招聘教员的信息
 */

public class TeacherWanted {
    //学段 小学，初中，高中，大学
    private String school;
    //学年:学段的第几年
    private String grade;
    //科目
    private String subject;
    //订单号
    private String orderId;
    //家教地址
    private String address;
    //期望价格
    private String payment;
    //期望教师学历
    private String educatedLevel;
    //状态
    private Boolean orderStatus;
    //浏览次数
    private Integer visitTimes;

    public TeacherWanted(String school, String grade, String subject,String address,String payment,String educatedLevel) {
        this.school = school;
        this.grade = grade;
        this.subject = subject;
        this.address = address;
        this.payment = payment;
        this.educatedLevel = educatedLevel;
        this.orderId = "4587dfdfd745";
        this.visitTimes = 0;
        this.orderStatus = false;
    }

    public void setVisitTimes(Integer visitTimes) {
        this.visitTimes = visitTimes;
    }

    public Integer getVisitTimes() {
        return visitTimes;
    }
    public void setOrderStatus(Boolean orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Boolean getOrderStatus() {
        return orderStatus;
    }

    public String getSubject() {
        return subject;
    }

    public String getAddress() {
        return address;
    }

    public String getEducatedLevel() {
        return educatedLevel;
    }

    public String getGrade() {
        return grade;
    }

    public String getPayment() {
        return payment;
    }

    public String getSchool() {
        return school;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEducatedLevel(String educatedLevel) {
        this.educatedLevel = educatedLevel;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
