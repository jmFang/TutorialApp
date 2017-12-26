package com.example.jiamoufang.tutorialapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.share.bean.Post;
import com.example.jiamoufang.tutorialapp.ui.activities.PostDetailsActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jiamoufang on 2017/12/23.
 */

public class PostAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<Post> mPosts;

    public PostAdapter(Context context, List<Post> posts) {
        mContext = context;
        mPosts = posts;
    }

    /*
    * 加载布局View，绑定布局
    * */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_post, null, false);
        //与PostHolder绑定
        return new PostHolder(view);
    }

    /*
    * 数据绑定，事件监听
    * */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Post post = mPosts.get(position);
        PostHolder postHolder = (PostHolder) holder;
        if (post.getAuthor().getAvatar() == null) {
            Glide.with(mContext).load(R.mipmap.default_ss).into(postHolder.mIvUserAvatar);
        } else {
            //到云端请求
            Glide.with(mContext).load(post.getAuthor().getAvatar().getFileUrl()).into(postHolder.mIvUserAvatar);
        }
        postHolder.mTvPostContent.setText(post.getContent());
        postHolder.mTvPostTime.setText(post.getCreatedAt());
        postHolder.mTvUserName.setText(post.getAuthor().getUsername());
        postHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, PostDetailsActivity.class).putExtra("post", post));
            }
        });
    }

    /*
    *获取post数目
    * */
    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    class PostHolder extends RecyclerView.ViewHolder {
        //头像
        @Bind(R.id.iv_user_avatar)
        ImageView mIvUserAvatar;
        //用户名
        @Bind(R.id.tv_user_name)
        TextView mTvUserName;
        //评论内容
        @Bind(R.id.tv_post_content)
        TextView mTvPostContent;
        @Bind(R.id.tv_post_time)
        TextView mTvPostTime;

        public PostHolder(View itemView) {
            super(itemView);
            //将PostHolder实例与布局实例化产生的View绑定
            ButterKnife.bind(this, itemView);
        }
    }
}
