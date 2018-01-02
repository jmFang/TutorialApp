package com.example.jiamoufang.tutorialapp;

import android.app.Application;

import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.listener.BmobIMMessageHandler;
import cn.bmob.v3.Bmob;

/**
 * Created by jiamoufang on 2017/12/18.
 */
public class BmobIMApplication extends Application {
    /*一个静态实例，提供给其他需要的地方调用
    * 单例模式
    * */
    private static BmobIMApplication INSTANCE;
    /*一个getter，提供给UserModel*/
    public static BmobIMApplication INSTANCE() {
        return INSTANCE;
    }
    /*设置INSTANCE*/
    private void setINSTANCE(BmobIMApplication app) {
        setBmobApplication(app);
    }
    private static void setBmobApplication(BmobIMApplication bmobApplication) {
        BmobIMApplication.INSTANCE = bmobApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setINSTANCE(this);
        Logger.init("jmfang");

        Bmob.initialize(this, "b94b1a62e1e3666cd004dd65992fcba1");
        BmobIM.init(this);
        BmobIM.registerDefaultMessageHandler(new DemoMessageHandler(this));

        if (getApplicationInfo().packageName.equals(getMyProcessName())) {
           Bmob.initialize(this, "b94b1a62e1e3666cd004dd65992fcba1");
            BmobIM.init(this);
            BmobIM.registerDefaultMessageHandler(new DemoMessageHandler(this));
        }
    }

    public static String getMyProcessName() {
        try {
            File file = new File("proc" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferReader = new BufferedReader(new FileReader(file));
            String myProcessName = mBufferReader.readLine().trim();
            mBufferReader.close();
            return myProcessName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
