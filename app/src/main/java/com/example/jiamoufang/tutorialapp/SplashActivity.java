package com.example.jiamoufang.tutorialapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jiamoufang.tutorialapp.model.UserModel;
import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.example.jiamoufang.tutorialapp.ui.activities.LogActivity;
import com.example.jiamoufang.tutorialapp.ui.activities.MainActivity;
import com.example.jiamoufang.tutorialapp.ui.activities.RegisterActivity;

import cn.bmob.v3.BmobUser;

/*启动界面
* */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                User user = UserModel.getInstance().getCurrentUser();
                BmobUser.logOut();
                if (user == null) {
                    Intent it = new Intent(SplashActivity.this, LogActivity.class);
                    startActivity(it);
                    finish();
                } else {
                    Intent it = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(it);
                    finish();
                }
            }
        }, 1000);
    }
}
