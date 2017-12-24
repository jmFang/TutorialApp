package com.example.jiamoufang.tutorialapp.share.view;

import com.example.jiamoufang.tutorialapp.share.bean.Comment;
import com.example.jiamoufang.tutorialapp.share.bean.Love;

import java.util.List;

/**
 * Created by jiamoufang on 2017/12/23.
 */

public interface PostDetailsView extends BmobView{
    /*
    * 评论相关
    * */
    void publishCommentSuccess();
    void deleteCommentSuccess();
    void showComments(List<Comment> comments);

    /*
    * 帖子相关
    * */
    void deletePostSuccess();

    /*
    * 点赞相关
    * */
    void loveSuccess();
    void unloveSuccess();
    void showLoves(List<Love> loves);

}
