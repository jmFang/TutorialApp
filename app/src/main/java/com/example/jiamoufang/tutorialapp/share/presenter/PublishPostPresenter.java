package com.example.jiamoufang.tutorialapp.share.presenter;

import com.example.jiamoufang.tutorialapp.share.model.BmobModel;
import com.example.jiamoufang.tutorialapp.share.view.PublishPostView;

import cn.bmob.newim.BmobIM;
import rx.functions.Action1;

/**
 * Created by jiamoufang on 2017/12/23.
 * 发表帖子
 */

public class PublishPostPresenter {
    private PublishPostView mPublishCommentView;
    private BmobModel mBmobModel;

    /*
    * 构造方法，与View绑定
    * */
    public PublishPostPresenter(PublishPostView publishPostCommentView) {
        mPublishCommentView = publishPostCommentView;
        mBmobModel = new BmobModel();
    }
    /*
    * push到云端
    * */
    public void publishPost(String content) {

        mPublishCommentView.showDialog();
        //调用数据库操作方法
        mBmobModel.publishPost(content)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mPublishCommentView.hideDialog();
                        mPublishCommentView.publishSuccess();  //显示发布成功后的提示
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mPublishCommentView.hideDialog();
                        mPublishCommentView.showError(throwable);//显示发布失败后的提示
                    }
                });
    }
}
