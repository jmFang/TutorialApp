package com.example.jiamoufang.tutorialapp.utils;

/**
 * Created by jiamoufang on 2017/12/21.
 */

public class Util {
    public static boolean checkSdCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }
}
