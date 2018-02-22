package com.example.jiamoufang.tutorialapp.model.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 用户注册登录时的基本信息
 * Created by jiamoufang on 2017/12/18.
 */

public class User extends BmobUser {


    private BmobFile avatar;

    /*真实姓名*/
    private String realName;

    /*用户角色：
    * 老师 : true
    * 学生 : false
    * */
    private Boolean role;

    /*性别
    * 男：true； 女： false
    * */
    private Boolean sex;

    /*学历
    * 小学，初中，高中，大专，本科，硕士，博士分别为 0,1,2,3,4,5,6
    * */
    private Integer educatedLevel;

    /*城市*/
    private String city;

    /*常用地址
    * */
    private String address;
    public User(){}
    public void setAddress(String address) {
        this.address = address;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setEducatedLevel(Integer educatedLevel) {
        this.educatedLevel = educatedLevel;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public void setAvatar(BmobFile avatar) {
        this.avatar = avatar;
    }

    public void setRole(Boolean role) {
        this.role = role;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getRealName() {
        return realName;
    }

    public Boolean getRole() {
        return role;
    }

    public Boolean getSex() {
        return sex;
    }

    public Integer getEducatedLevel() {
        return educatedLevel;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public BmobFile getAvatar() {
        return avatar;
    }
}
