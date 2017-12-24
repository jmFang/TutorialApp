package com.example.jiamoufang.tutorialapp.model;

import android.content.Context;

import com.example.jiamoufang.tutorialapp.BmobIMApplication;

/**
 * Created by jiamoufang on 2017/12/18.
 */

public abstract class BaseModel {

    public int CODE_NULL = 1000;
    public static int CODE_NOT_EQUEAL = 1001;

    public static final int DEFAULT_LIMIT = 20;
    /*
    * 获取全局Context，全局Context来自Application
    * */
    public Context getContext() {
        return BmobIMApplication.INSTANCE();
    }
}
