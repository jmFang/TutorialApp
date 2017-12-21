package com.example.jiamoufang.tutorialapp.ui.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.support.transition.BuildConfig;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.jiamoufang.tutorialapp.Config;
import com.koushikdutta.async.http.socketio.SocketIORequest;
import com.orhanobut.logger.Logger;

/**
 * Created by jiamoufang on 2017/12/21.
 * 项目中Fragment的基类
 */

public class BaseFragment extends Fragment {
    /*
    * 主线程操作
    * */
    protected void runOnMain(Runnable runnable) {
        getActivity().runOnUiThread(runnable);
    }

    protected final static String NULL = "";

    private Toast toast;

    /*
    * Toast提示
    * */
    public void toast(final Object object) {
        try {
            runOnMain(new Runnable() {
                @Override
                public void run() {
                    if (toast == null) {
                        toast = Toast.makeText(getActivity(), NULL, Toast.LENGTH_SHORT);
                        toast.setText(object.toString());
                        toast.show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * 隐藏软键盘
    * */
    public void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /*
    * 启动指定的Activity
    * @param target
    * @param bundle
    * */
    public void startActivity(Class<? extends Activity> target, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(),target);
        if (bundle != null) {
            intent.putExtra(getActivity().getPackageName(),bundle);
        }
        getActivity().startActivity(intent);
    }

    /*
    * 日志
    * @param msg
    * */
    public void log(String msg) {
        if (Config.DEBUG) {  //Config来自项目的Config配置类
            Logger.i(msg);
        }
    }
}
