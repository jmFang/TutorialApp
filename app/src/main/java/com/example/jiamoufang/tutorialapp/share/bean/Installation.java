package com.example.jiamoufang.tutorialapp.share.bean;

import com.example.jiamoufang.tutorialapp.model.bean.User;

import cn.bmob.v3.BmobInstallation;

/**
 * Created by jiamoufang on 2017/12/23.
 * 设备表
 * 使用消息推送服务时才会用到（本项目没有用到）
 */

public class Installation extends BmobInstallation {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
