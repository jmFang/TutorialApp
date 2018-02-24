package com.example.jiamoufang.tutorialapp.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by jiamoufang on 2017/12/30.
 */

//此为老师资讯类
public class TeacherInformation extends BmobObject{

    //教龄
    private int teachingAge;
    //名字,作为唯一键与User表外联
    private String username;
    // teaching experience（经验）introduction
    private String experience;
    //teaching experience（经历）
    private String practice;
    // 毕业/在读院校自我介绍
    private String school;
    //效果满意率
    private Integer satisfyingOfEffect;
    //服务满意率
    private Integer satisfyingOfService;
    //总服务次数
    private Integer totalSeriveTimes;
    //教学箴言
    private String teachingSaying;

    //关联用户
    private User user;

    public TeacherInformation(User user) {
        teachingAge = 0;
        username = user.getUsername();
        experience = "";
        practice = "";
        school = "";
        satisfyingOfEffect = Integer.valueOf(100);
        satisfyingOfService = Integer.valueOf(100);
        totalSeriveTimes =Integer.valueOf(0);
        teachingSaying = "";
        this.user = user;
    }
    public TeacherInformation(TeacherInformation teacherInformation) {
        teachingAge = teacherInformation.getTeachingAge();
        username = teacherInformation.getUsername();
        experience = teacherInformation.getExperience();
        practice = teacherInformation.getPractice();
        school = teacherInformation.getSchool();
        satisfyingOfEffect = teacherInformation.getSatisfyingOfEffect();
        satisfyingOfService = teacherInformation.getSatisfyingOfService();
        totalSeriveTimes = teacherInformation.getTotalSeriveTimes();
        user = teacherInformation.getUser();
    }

    public int getTeachingAge() {
        return teachingAge;
    }

    public User getUser() {
        return user;
    }

    public void setTeachingAge(int teachingAge) {
        this.teachingAge = teachingAge;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getSatisfyingOfEffect() {
        return satisfyingOfEffect;
    }

    public Integer getSatisfyingOfService() {
        return satisfyingOfService;
    }

    public Integer getTotalSeriveTimes() {
        return totalSeriveTimes;
    }

    public String getExperience() {
        return experience;
    }

    public String getPractice() {
        return practice;
    }

    public String getSchool() {
        return school;
    }

    public String getTeachingSaying() {
        return teachingSaying;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public void setPractice(String practice) {
        this.practice = practice;
    }

    public void setSatisfyingOfEffect(Integer satisfyingOfEffect) {
        this.satisfyingOfEffect = satisfyingOfEffect;
    }

    public void setSatisfyingOfService(Integer satisfyingOfService) {
        this.satisfyingOfService = satisfyingOfService;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setTeachingSaying(String teachingSaying) {
        this.teachingSaying = teachingSaying;
    }

    public void setTotalSeriveTimes(Integer totalSeriveTimes) {
        this.totalSeriveTimes = totalSeriveTimes;
    }

}
