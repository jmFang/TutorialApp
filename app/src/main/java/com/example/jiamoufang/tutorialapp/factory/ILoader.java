package com.example.jiamoufang.tutorialapp.factory;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by jiamoufang on 2017/12/21.
 * 图片加载接口
 */

public interface ILoader {
    /*TODO 加载圆形图片
    * @param iv
    * @param url
    * @param defaultRes
    * */
    void loadAvatar(ImageView iv, String url, int defaultRes);

    /*TODO 加载图片，添加监听器
    * @param iv
    * @param url
    * @param defaultRes
    * @param listener
    * */
    void load(ImageView iv, String url, int defaultRes, ImageLoadingListener listener);
}
