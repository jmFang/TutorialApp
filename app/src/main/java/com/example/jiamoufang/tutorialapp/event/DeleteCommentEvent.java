package com.example.jiamoufang.tutorialapp.event;

import com.example.jiamoufang.tutorialapp.share.bean.Comment;

/**
 * Created by jiamoufang on 2017/12/24.
 * 删除评论的通知
 */

public class DeleteCommentEvent {
    Comment comment;

    public DeleteCommentEvent(Comment comment) {
        this.comment = comment;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
