package com.example.jiamoufang.tutorialapp.factory;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.jiamoufang.tutorialapp.utils.DisplayConfig;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by jiamoufang on 2017/12/21.
 */

public class UniversalImageLoader implements ILoader{

    protected ImageLoader imageLoader;

    public UniversalImageLoader( Context context){
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }


    /*
    * 为view加载图片
    * */
    @Override
    public void loadAvatar(ImageView iv, String url, int defaultRes) {
        if(!TextUtils.isEmpty(url)){
            //如果url不为空，去url找图片
            display(iv,url,true,defaultRes,null);
        } else {
            iv.setImageResource(defaultRes);
        }
    }

    @Override
    public void load(ImageView iv, String url, int defaultRes,ImageLoadingListener listener) {
        if(!TextUtils.isEmpty(url)){
            display(iv,url.trim(),false,defaultRes,listener);
        } else {
            iv.setImageResource(defaultRes);
        }
    }

    /**
     * 展示图片
     * @param iv
     * @param url
     * @param defaultRes
     * @param listener
     */
    private void display(ImageView iv,String url,boolean isCircle,int defaultRes,ImageLoadingListener listener){
        if(!url.equals(iv.getTag())){//增加tag标记，减少UIL的display次数
            iv.setTag(url);
            //不直接display imageview改为ImageAware，解决ListView滚动时重复加载图片
            ImageAware imageAware = new ImageViewAware(iv, false);
            ImageLoader.getInstance().displayImage(url, imageAware, DisplayConfig.getDefaultOptions(isCircle,defaultRes),listener);
        }
    }

    /**
     * 初始化ImageLoader
     * @param context
     */
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPoolSize(3);
        config.memoryCache(new WeakMemoryCache());
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
//        config.writeDebugLogs(); // Remove for release app
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
}
