package com.example.jiamoufang.tutorialapp.event;

import com.example.jiamoufang.tutorialapp.share.bean.Comment;

/**
 * Created by jiamoufang on 2017/12/24.
 * 评论回复的通知
 */

public class ReplyToEvent {
    Comment replyTo;

    public ReplyToEvent(Comment replyTo) {
        this.replyTo = replyTo;
    }

    public Comment getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(Comment replyTo) {
        this.replyTo = replyTo;
    }
}
