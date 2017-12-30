package com.example.jiamoufang.tutorialapp.adapter;

import com.example.jiamoufang.tutorialapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a0924 on 2017/12/30.
 */

public//此为老师资讯类
class TeacherInformation {

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

    //利用静态方法创造该类的对象实体
    //这里用回圈创建20个实体,只是为了表现UI可以滑动功能.
    //PS 逻辑开发时可以连这个类都注解掉, 去创建自己需要的对象个数和对象的meta-data
    public static List<TeacherInformation> initTeacherInformation() {
        List<TeacherInformation> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new TeacherInformation(R.drawable.hit_activity_1, "小初教师", 190, 14, "何老师"));
        }
        return list;
    }
}
