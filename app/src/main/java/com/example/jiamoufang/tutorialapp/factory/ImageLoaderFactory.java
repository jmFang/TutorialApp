package com.example.jiamoufang.tutorialapp.factory;

import android.content.Context;

import com.example.jiamoufang.tutorialapp.model.bean.Conversation;

/**
 * Created by jiamoufang on 2017/12/21.
 */

public class ImageLoaderFactory {
    private static volatile ILoader sInstance;
    private ImageLoaderFactory(){}

    public static ILoader getLoader(final Context context) {
        if (sInstance == null) {
            synchronized (ImageLoaderFactory.class) {
                if (sInstance == null) {
                    sInstance = new UniversalImageLoader(context);
                }
            }
        }
        return sInstance;
    }
}
