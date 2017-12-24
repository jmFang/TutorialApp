package com.example.jiamoufang.tutorialapp.share.bean;

import com.example.jiamoufang.tutorialapp.model.bean.User;

import cn.bmob.v3.BmobObject;

/**
 * Created by jiamoufang on 2017/12/23.
 * 点赞表
 */

public class Love extends BmobObject {
    User lover;
    Post post;

    public User getLover() {
        return lover;
    }

    public void setLover(User lover) {
        this.lover = lover;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
