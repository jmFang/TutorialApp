package com.example.jiamoufang.tutorialapp.share.bean;

import com.example.jiamoufang.tutorialapp.model.bean.User;

import cn.bmob.v3.BmobObject;

/**
 * Created by jiamoufang on 2017/12/23.
 * 评论表
 */

public class Comment extends BmobObject {
    User commentator;
    Post post;
    Comment replyToComment;
    User replyToUser;
    String content;

    public User getCommentator() {
        return commentator;
    }

    public void setCommentator(User commentator) {
        this.commentator = commentator;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment getReplyToComment() {
        return replyToComment;
    }

    public void setReplyToComment(Comment replyToComment) {
        this.replyToComment = replyToComment;
    }

    public User getReplyToUser() {
        return replyToUser;
    }

    public void setReplyToUser(User replyToUser) {
        this.replyToUser = replyToUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
