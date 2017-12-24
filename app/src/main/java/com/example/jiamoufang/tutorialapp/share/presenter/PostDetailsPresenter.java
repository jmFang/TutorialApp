package com.example.jiamoufang.tutorialapp.share.presenter;

import com.bumptech.glide.load.resource.NullEncoder;
import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.example.jiamoufang.tutorialapp.share.bean.Comment;
import com.example.jiamoufang.tutorialapp.share.bean.Love;
import com.example.jiamoufang.tutorialapp.share.bean.Post;
import com.example.jiamoufang.tutorialapp.share.model.BmobModel;
import com.example.jiamoufang.tutorialapp.share.view.PostDetailsView;

import java.util.List;

import javax.xml.transform.sax.TemplatesHandler;

import rx.functions.Action1;

/**
 * Created by jiamoufang on 2017/12/23.
 * 帖子的点赞、评论等其他详情
 */

public class PostDetailsPresenter {
    private PostDetailsView mPublishCommentView;
    private BmobModel mBmobModel;

    /*
    * 构造方法，绑定数据
    * */
    public PostDetailsPresenter(PostDetailsView postDetailsView) {
        mPublishCommentView = postDetailsView;
        mBmobModel = new BmobModel();
    }

    /*
    * 发o表评论
    * @param post
    * @param replyToComment
    * @param replyToUser
    * @param content
    * */
    public void publishComment(Post post, Comment replyToComment, User replyToUser, String content) {
        mPublishCommentView.showDialog();
        mBmobModel.publishComment(post, replyToComment, replyToUser, content)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mPublishCommentView.hideDialog();
                        mPublishCommentView.publishCommentSuccess();  //发表成功
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mPublishCommentView.hideDialog();
                        mPublishCommentView.showError(throwable);  //发表失败
                    }
                });
    }

    /*
    * 删除评论
    * @param objectId
    * */
    public void deleteComment(String objectId) {
        mPublishCommentView.showDialog();
        mBmobModel.deleteComment(objectId)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mPublishCommentView.hideDialog();
                        mPublishCommentView.deleteCommentSuccess();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mPublishCommentView.hideDialog();
                        mPublishCommentView.showError(throwable);
                    }
                });
    }

    /*
    * 查找某个帖子的所有评论
    * @param post
    * @param page
    * @param count
    * */
    public void findComments(Post post, int page, int count) {
        mPublishCommentView.showDialog();
        mBmobModel.findComments(post, page, count)
                .subscribe(new Action1<List<Comment>>() {
                    @Override
                    public void call(List<Comment> comments) {
                        mPublishCommentView.hideDialog();
                        mPublishCommentView.showComments(comments);  // 查找成功，在UI界面显示所有评论
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mPublishCommentView.hideDialog();
                        mPublishCommentView.showError(throwable);
                    }
                });
    }

    /*
    * 给某个帖子点赞
    * @param post
    * */
    public void love(Post post) {
        mPublishCommentView.showDialog();
        mBmobModel.love(post)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mPublishCommentView.hideDialog();
                        mPublishCommentView.loveSuccess();  //在UI界面显示点赞成功
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mPublishCommentView.hideDialog();
                        mPublishCommentView.showError(throwable); //点赞失败，在UI界面显示失败的信息
                    }
                });
    }

    /*
    * 取消对某个帖子的点赞
    * @param post
    * */
    public void unlove(String objectId) {
        mPublishCommentView.showDialog();
        mBmobModel.unlove(objectId)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mPublishCommentView.hideDialog();
                        mPublishCommentView.unloveSuccess();  //取消点赞成功，在UI界面显示
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mPublishCommentView.hideDialog();
                        mPublishCommentView.showError(throwable); //取消点赞失败， 在UI界面显示失败信息
                    }
                });
    }

    /*
    * 删除帖子
    * @param objectId
    * */
    public void deletePost(String objectId) {
        mPublishCommentView.showDialog();
        mBmobModel.deletePost(objectId)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mPublishCommentView.hideDialog();
                        mPublishCommentView.deletePostSuccess(); //删除帖子成功
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mPublishCommentView.hideDialog();
                        mPublishCommentView.showError(throwable); //删除帖子失败
                    }
                });
    }

    /*
    * 对某个帖子显示所有点赞
    * @param post
    * */
    public void showLoves(Post post) {
        mPublishCommentView.showDialog();
        mBmobModel.findLoves(post)
                .subscribe(new Action1<List<Love>>() {
                    @Override
                    public void call(List<Love> loves) {
                        mPublishCommentView.hideDialog();
                        mPublishCommentView.showLoves(loves); //操作成功，回调，在UI界面显示点赞
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mPublishCommentView.hideDialog();
                        mPublishCommentView.showError(throwable); //操作失败，在UI界面显示失败的信息
                    }
                });
    }
}
