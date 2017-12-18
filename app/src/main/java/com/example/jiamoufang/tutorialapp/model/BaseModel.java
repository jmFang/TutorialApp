package com.example.jiamoufang.tutorialapp.model;

import android.content.Context;

import com.example.jiamoufang.tutorialapp.BmobIMApplication;

/**
 * Created by jiamoufang on 2017/12/18.
 */

public abstract class BaseModel {
    /*
    * 获取全局Context，全局Context来自Application
    * */
    public Context getContext() {
        return BmobIMApplication.INSTANCE();
    }
}
