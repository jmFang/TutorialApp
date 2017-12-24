package com.example.jiamoufang.tutorialapp.share.presenter;

import com.example.jiamoufang.tutorialapp.share.bean.Post;
import com.example.jiamoufang.tutorialapp.share.model.BmobModel;
import com.example.jiamoufang.tutorialapp.share.view.ShowPostView;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by jiamoufang on 2017/12/23.
 * 显示点赞情况
 */

public class ShowPostPresenter {
    private ShowPostView mShowPostsView;
    private BmobModel mBmobModel;

    /*
    * 构造方法，绑定数据
    * */
    public ShowPostPresenter(ShowPostView showPostView) {
        mShowPostsView = showPostView;
        mBmobModel = new BmobModel();
    }

    /*
    * 显示点赞
    * @param page
    * @param count
    * */
    public  void showPosts(int page, int count) {
        mShowPostsView.showDialog();
        mBmobModel.findPosts(page, count)
                .subscribe(new Action1<List<Post>>() {
                    @Override
                    public void call(List<Post> posts) {
                        mShowPostsView.hideDialog();
                        mShowPostsView.showPosts(posts);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mShowPostsView.hideDialog();
                        mShowPostsView.showError(throwable);
                    }
                });
    }

}
