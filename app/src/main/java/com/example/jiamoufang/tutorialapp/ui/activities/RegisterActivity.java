package com.example.jiamoufang.tutorialapp.ui.activities;

import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.animation.Animator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.event.FinishEvent;
import com.example.jiamoufang.tutorialapp.model.UserModel;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

import static android.R.attr.onClick;

public class RegisterActivity extends AppCompatActivity {
    @Bind(R.id.et_username)
    EditText mEtUsername;
    @Bind(R.id.et_password)
    EditText mEtPassword;
    @Bind(R.id.et_repeatpassword)
    EditText mEtRepeatPassword;
    @Bind(R.id.bt_go)
    Button mBtGo;
    @Bind(R.id.cv_add)
    CardView mmCVAdd;
    @Bind(R.id.fab)
    FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  //设置全屏
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            showEnterAnimation();
        }
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRevealClose();  //点击fab收起注册界面的CradView
            }
        });

    }

    /*TODO 动画效果
    * 进入活动界面时的动画
    * */
    private void showEnterAnimation() {
        android.transition.Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new android.transition.Transition.TransitionListener() {
            @Override
            public void onTransitionStart(android.transition.Transition transition) {
                mmCVAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(android.transition.Transition transition) {
                transition.removeListener(this);
                animateRevealShow(); //下放注册界面的CardView
            }

            @Override
            public void onTransitionCancel(android.transition.Transition transition) {

            }

            @Override
            public void onTransitionPause(android.transition.Transition transition) {

            }

            @Override
            public void onTransitionResume(android.transition.Transition transition) {

            }
        });
    }

    private void animateRevealShow() {
        /*向下伸展的曲线动画
        * 动画对象是mmCard
        * 以mFab.getWidth()/2为初始半径
        * mmCVAdd.getWidth()/2为最大半径
        * */
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(mmCVAdd, mmCVAdd.getWidth()/2, 0, mFab.getWidth()/2, mmCVAdd.getHeight());
        mAnimator.setDuration(500);
        /*动画是逐渐加速的*/
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mmCVAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        mAnimator.start();
    }

    /*TODO 动画效果
    * 收起注册界面的卡片，切换到登录界面
    * */
    private void animateRevealClose() {
        Animator mAnimatior = ViewAnimationUtils.createCircularReveal(mmCVAdd, mmCVAdd.getWidth()/2, 0, mmCVAdd.getHeight(), mFab.getWidth()/2);
        mAnimatior.setDuration(500);
        mAnimatior.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimatior.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mmCVAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                mFab.setImageResource(R.drawable.plus);
                RegisterActivity.super.onBackPressed();/*通过模拟按下返回键回到了登录界面*/
            }
        });
        mAnimatior.start();
    }

    @Override
    public void onBackPressed() {
        animateRevealClose();
    }
    @OnClick(R.id.bt_go)
    public void onViewClicked(){
        UserModel.getInstance().register(mEtUsername.getText().toString(), mEtPassword.getText().toString(), mEtRepeatPassword.getText().toString(), new LogInListener() {
            @Override
            public void done(Object o, BmobException e) {
                if (e == null) {
                    /*发出完成登录的事件消息，通知相应的fragment或activity*/
                    EventBus.getDefault().post(new FinishEvent());
                    //Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                } else {
                    Logger.e(e.getMessage() + "(" + e.getErrorCode() + ")");
                    Toast.makeText(RegisterActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}
