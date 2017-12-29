package com.example.jiamoufang.tutorialapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.event.DeleteCommentEvent;
import com.example.jiamoufang.tutorialapp.event.ReplyToEvent;
import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.example.jiamoufang.tutorialapp.share.bean.Comment;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

/**
 * Created by jiamoufang on 2017/12/24.
 * 评论区的适配器
 */

public class CommentAdapter extends RecyclerView.Adapter {


    private Context mContext;
    private List<Comment> mComments;

    public CommentAdapter(Context context, List<Comment> comments) {
        mContext = context;
        mComments = comments;
    }
    /*
    * 加载布局View,绑定布局
    * */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_comment, null, false);
        return new CommentHolder(view);
    }
    /*
    * 绑定ViewHolder与数据
    * */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        CommentHolder commentHolder = (CommentHolder) holder;
        final Comment comment = mComments.get(position);
        //如果评论者的头像为null，则加载默认的头像,否则根据URL请求加载
        if (comment.getCommentator().getAvatar() == null)
            Glide.with(mContext).load(R.mipmap.default_ss).into(commentHolder.mIvCommentAvatar);
        else
            Glide.with(mContext).load(comment.getCommentator().getAvatar().getFileUrl()).into(commentHolder.mIvCommentAvatar);

        //如果目前对评论的回复为null，那么该评论来自评论者，否则应该该评论是作者对评论者的回复
        if (comment.getReplyToComment() == null) {
            commentHolder.mTvCommentContent.setText(comment.getContent());
        } else {
            //改变被回复者用户名的颜色以突出是作者对读者的回复
            String content = "回复" + "<font color='#007BFF' >" + comment.getReplyToUser().getUsername() + "</font>" + "：" + comment.getContent();
            commentHolder.mTvCommentContent.setText(Html.fromHtml(content));
        }
        commentHolder.mTvCommentTime.setText(comment.getCreatedAt());
        //该条评论的发送方的名字
        commentHolder.mTvCommentUserName.setText(comment.getCommentator().getUsername());

        /*
        * TODO 长按删除自己的评论，使用event bus通知
        * 云数据库删除评论
        * */
        commentHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (BmobUser.getCurrentUser().getObjectId().equals(comment.getCommentator().getObjectId())) {
                    EventBus.getDefault().post(new DeleteCommentEvent(comment));
                } else {
                    EventBus.getDefault().post(new DeleteCommentEvent(null));
                }
                return false;
            }
        });
        /*
        * TODO 点击回复别人的评论，使用event bus通知
        * 云数据库增加评论
        * */
        commentHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String objectId = BmobUser.getCurrentUser(User.class).getObjectId();
                if (!objectId.equals(comment.getCommentator().getObjectId())) {
                    EventBus.getDefault().post(new ReplyToEvent(comment));
                } else {
                    EventBus.getDefault().post(new ReplyToEvent(null));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    class CommentHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_comment_avatar)
        ImageView mIvCommentAvatar;
        @Bind(R.id.tv_comment_user_name)
        TextView mTvCommentUserName;
        @Bind(R.id.tv_comment_time)
        TextView mTvCommentTime;
        @Bind(R.id.tv_comment_content)
        TextView mTvCommentContent;
        public CommentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
