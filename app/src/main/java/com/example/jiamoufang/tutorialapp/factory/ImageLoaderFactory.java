package com.example.jiamoufang.tutorialapp.factory;

/**
 * Created by jiamoufang on 2017/12/21.
 */

public class ImageLoaderFactory {
    private static volatile ILoader sInstance;
    private ImageLoaderFactory(){}

    public static ILoader getLoader() {
        if (sInstance == null) {
            synchronized (ImageLoaderFactory.class) {
                if (sInstance == null) {
                    sInstance = new UniversalImageLoader();
                }
            }
        }
        return sInstance;
    }
}
