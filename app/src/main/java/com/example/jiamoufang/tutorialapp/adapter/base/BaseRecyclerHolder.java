package com.example.jiamoufang.tutorialapp.adapter.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.ImageVideoModelLoader;
import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.factory.ImageLoaderFactory;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by jiamoufang on 2017/12/21.
 */

public class BaseRecyclerHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> mViews =  new SparseArray<>(8);
    public int layoutId;
    protected Context context;

    /*
    * 构造函数
    * */
    public BaseRecyclerHolder(int layoutId, View itemView) {
        super(itemView);
        this.layoutId = layoutId;
        context = itemView.getContext();
    }
    /*
    * 获取SparseArray
    * */

    public SparseArray<View> getAllViews() {
        return mViews;
    }

    /*TODO 获取item
    * @param viewId
    * */
    protected <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);  //itemView来自ViewHolder父类
            mViews.put(viewId, view);
        }
        return (T) view;
    }
    /*TODO 转换对象
    * @param viewId
    * @param text
    * @return BaseRecyclerHolder object
    * */
    public BaseRecyclerHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /*TODO 使能
    * @param viewId
    * @param enable
    * @return
    * */
    public BaseRecyclerHolder setEnabled(int viewId, boolean enable) {
        View view = getView(viewId);
        view.setEnabled(enable);
        return this;
    }

    /*TODO 点击事件
    * @param viewId
    * @param listener
    * @return
    * */
    public BaseRecyclerHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    /*TODO 设置可见性
    * @param viewId
    * @param visibility
    * @return
    * */
    public BaseRecyclerHolder setVisible(int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    /*TODO 设置ImageResource
    * @param viewId
    * @param drawableId
    * @return
    * */
    public BaseRecyclerHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);
        return this;
    }

    /*TODO 设置图片
    * @param viewId
    * @param bm
    * @return
    * */
    public BaseRecyclerHolder setImageBitmap(int viwId, Bitmap bm) {
        ImageView view = getView(viwId);
        view.setImageBitmap(bm);
        return this;
    }

    /*TODO 设置头像
    * @param avatar
    * @param defaultRes
    * @param viewId
    * @return
    * */
    public BaseRecyclerHolder setImageView(String avatar, int defaultRes, int viewId) {
        ImageView iv = getView(viewId);
        //Glide.with(context).load(R.mipmap.default_smg).into(iv_avatar);
       ImageLoaderFactory.getLoader(context).loadAvatar(iv, avatar, defaultRes);
        return this;
    }
}
