package com.example.jiamoufang.tutorialapp.share.view;

import com.example.jiamoufang.tutorialapp.share.bean.Post;

import java.util.List;

/**
 * Created by jiamoufang on 2017/12/23.
 */

public interface ShowPostView extends BmobView{
    void showPosts(List<Post> posts);
}
