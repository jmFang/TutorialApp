package com.example.jiamoufang.tutorialapp.model.bean;

/**
 * Created by jiamoufang on 2017/12/30.
 */

//此为老师资讯类
public class TeacherInformation {
    private int pictureID;
    private String info;
    private float price;
    private int teachingAge;
    private String name;

    public TeacherInformation(int pictureID, String info, float price, int teachingAge, String name) {
        this.pictureID = pictureID;
        this.info = info;
        this.price = price;
        this.teachingAge = teachingAge;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeachingAge() {
        return teachingAge;
    }

    public void setTeachingAge(int teachingAge) {
        this.teachingAge = teachingAge;
    }

    public int getPictureID() {
        return pictureID;
    }

    public void setPictureID(int pictureID) {
        this.pictureID = pictureID;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}
