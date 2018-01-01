package com.example.jiamoufang.tutorialapp.ui.activities;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.model.UserModel;
import com.example.jiamoufang.tutorialapp.utils.ActivityCollector;
import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

public class LogActivity extends AppCompatActivity {
    /*使用butterknife.Bind 注解框架
    * */
    @Bind(R.id.et_username)
    EditText mEtUsername;
    @Bind(R.id.et_password)
    EditText mEtPassword;
    @Bind(R.id.bt_go)
    Button mBtGo;
    @Bind(R.id.login_cardView)
    CardView mCardView;
    @Bind(R.id.fab)
    FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  //设置全屏
        setContentView(R.layout.activity_log);
        /*绑定注解
        * @comment by fangjiamouo
        * */
        ButterKnife.bind(this);

    }
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)

    /*使用注释完成点击事件的注册
    * 再也不用担心写一大堆findViewById了
    * @comment by fangjiamouo
    * */
    @OnClick({R.id.bt_go, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                /*切换的进出动画效果注册*/
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);
                /*如果API >= 21, 即SDK >= 6.0
                * 是保证动画效果可用的最低API要求
                * @comment by fangjiamouo
                * */
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, mFab, mFab.getTransitionName());
                    startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(this, RegisterActivity.class));
                }
                break;
            case R.id.bt_go:
                UserModel.getInstance().login(mEtUsername.getText().toString(), mEtPassword.getText().toString(), new LogInListener() {
                    @Override
                    public void done(Object o, BmobException e) {
                        if (e == null) {
                            Explode explode = new Explode();
                            explode.setDuration(500);
                            //Toast.makeText(LogActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            /*动画*/
                            getWindow().setExitTransition(explode);
                            getWindow().setEnterTransition(explode);
                            ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LogActivity.this);
                            Intent it2 = new Intent(LogActivity.this, MainActivity.class);
                            startActivity(it2, oc2.toBundle());
                            ActivityCollector.getInstance().addActivity(LogActivity.this);
                        } else {
                            Logger.e(e.getMessage() + "(" + e.getErrorCode() + ")");
                            Toast.makeText(LogActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

    /*
    * from currentUserInfoSettingActivity to LogActivity,
    * some activity resource not be released, releases them here
    * */

    @Override
    public void onBackPressed() {
        ActivityCollector.getInstance().exitApp();
        super.onBackPressed();
    }
}
