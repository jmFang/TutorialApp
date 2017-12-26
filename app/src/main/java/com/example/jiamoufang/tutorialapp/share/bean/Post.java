package com.example.jiamoufang.tutorialapp.share.bean;

import com.example.jiamoufang.tutorialapp.model.bean.User;

import cn.bmob.v3.BmobObject;

/**
 * Created by jiamoufang on 2017/12/23.
 * 帖子表
 */

public class Post extends BmobObject {
    User author;
    String content;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
