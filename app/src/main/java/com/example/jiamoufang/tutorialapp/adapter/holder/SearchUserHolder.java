package com.example.jiamoufang.tutorialapp.adapter.holder;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.adapter.base.BaseViewHolder;
import com.example.jiamoufang.tutorialapp.adapter.listener.OnRecyclerViewListener;
import com.example.jiamoufang.tutorialapp.factory.ImageLoaderFactory;
import com.example.jiamoufang.tutorialapp.model.bean.Conversation;
import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.example.jiamoufang.tutorialapp.ui.activities.UserInfoActivity;

import butterknife.Bind;

/**
 * Created by jiamoufang on 2017/12/22.
 */

public class SearchUserHolder extends BaseViewHolder{
    //头像
    @Bind(R.id.avatar)
    public ImageView avatar;
    //用户名
    @Bind(R.id.name)
    public TextView name;
    //查看用户资料
    @Bind(R.id.btn_add)
    public Button btn_add;
    //以上控件与id的绑定在父类BaseViewHolder就完成了！
    private Context mContext;

    public SearchUserHolder(Context context, ViewGroup root, OnRecyclerViewListener onRecyclerViewListener) {
        super(context, root, R.layout.item_search_user, onRecyclerViewListener);
        mContext = context;
    }

    @Override
    public void bindData(Object object) {
        final User user = (User) object;
        //如果云端没有该头像图片，则用本地的默认图片
        if (user.getAvatar() != null) {
            Glide.with(mContext).load(user.getAvatar().getUrl()).into(avatar);
        } else {
            Glide.with(mContext).load(R.mipmap.default_smg).into(avatar);
        }
       // ImageLoaderFactory.getLoader().loadAvatar(avatar, user.getAvatar() == null ? null:user.getAvatar().getFileUrl(), R.mipmap.default_ss);
        name.setText(user.getUsername());
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                //串行化
                bundle.putSerializable("u", user);
                //启动用户信息界面
                startActivity(UserInfoActivity.class, bundle);
            }
        });
    }
}
