package com.example.jiamoufang.tutorialapp.utils;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiamoufang on 2018/1/1.
 */

public class ActivityCollector extends Application{

    private List<Activity> activities = new ArrayList<>();
    private static ActivityCollector instance;

    public ActivityCollector(){}

    /*
    * 单例获取
    * */
    public synchronized static ActivityCollector getInstance() {
        if (instance == null) {
            instance = new ActivityCollector();
        }
        return instance;
    }
    /*
    * add activity
    * @fangjiamou
    * */
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    /*
    * exit application, finish all activities
    * @fangjiamou
    * */
    public void exitApp() {
        try {
            for (Activity activity : activities) {
                if (activity != null) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    /*
    * kill processes
    * @fangjiamou
    * */

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}
