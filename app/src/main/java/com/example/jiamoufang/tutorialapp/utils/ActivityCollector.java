package com.example.jiamoufang.tutorialapp.utils;

import android.app.Activity;
import android.app.Application;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiamoufang on 2018/1/2.
 */

public class ActivityCollector extends Application {

    private static ActivityCollector instance = new ActivityCollector();
    public static ActivityCollector getInstance() {
        return instance;
    }
    private List<Activity> activities = new ArrayList<>();

    private ActivityCollector(){}

    /*
    * add activity
    * */
    public void addActivity(Activity activity) {
        activities.add(activity);
    }
    /*
    * finish all activities
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
    * kill process
    * */

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}
