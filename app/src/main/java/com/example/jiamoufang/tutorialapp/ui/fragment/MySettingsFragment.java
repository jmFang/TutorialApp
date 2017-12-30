package com.example.jiamoufang.tutorialapp.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.example.jiamoufang.tutorialapp.ui.activities.CurrentUserInfoSettingActivity;
import com.example.jiamoufang.tutorialapp.ui.activities.OrderActivity;
import com.example.jiamoufang.tutorialapp.ui.base.ParentWithNaviFragment;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

/*
* preparing work by fangjiamou
* */
public class MySettingsFragment  extends ParentWithNaviFragment implements View.OnClickListener{
    /*人物头像*/
    @Bind(R.id.my_photo)
    ImageView my_photo;
    /*用户名*/
    @Bind(R.id.user_name)
    TextView user_name;
    /*用户角色“
    * 学生 or老师
    * */
    @Bind(R.id.user_identity)
    TextView user_identity;
    /*个人资料的设置按钮*/
    @Bind(R.id.info_config)
    ImageView info_config;
    /*日程*/
    @Bind(R.id.img_schedule)
    ImageView img_schedule;
    /*积分*/
    @Bind(R.id.img_score)
    ImageView img_score;
    /*信誉*/
    @Bind(R.id.img_flame)
    ImageView img_flame;
    /*爱心值*/
    @Bind(R.id.img_love)
    ImageView img_love;
    /*我的老师*/
    @Bind(R.id.ll_my_teacher)
    ConstraintLayout ll_my_teacher;
    /*我的学生*/
    @Bind(R.id.ll_my_student)
    ConstraintLayout ll_my_student;
    /*我的订单*/
    @Bind(R.id.ll_my_orders)
    ConstraintLayout ll_my_orders;

    private User currentUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_settings, container, false);
        initNaviView();
        ButterKnife.bind(this, rootView);
        //将图片做成Bitmap,并将其制作切割,再设置成订单图片
        //(此处代码为UI测试所需用到的逻辑,在逻辑开发时可以注解之>)
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.default_ss);
        ImageView img = rootView.findViewById(R.id.my_photo);
        img.setImageDrawable(OrderActivity.getRoundedShape(icon, getResources()));

        /*
        * 获取当前用户资料，渲染界面信息
        * put your code here
        * */
        currentUser = BmobUser.getCurrentUser(User.class);
        /*initialize*/
        initUserInfo();
        return rootView;
    }

    /*
    * initialize the information of current user
    * by fangjiamou
    * */
    private void initUserInfo() {

    }

    /*
    * 继承ParentWithNaviFragment，具体化NaviBar 标题
    * by fangjiamou
    * */
    @Override
    protected String title() {
        return "我的";
    }

    /*
    * handlers for click events
    * by fangjiamou
    * */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_photo:
                /*
                * you'd better get the current user here(fill the bundle), and transmit it to CurrentUserInfoSettingActivity
                * or get the current user's information after starting CurrentUserInfoSettingActivity
                * it is up to you, but i prefer the former which is more efficient
                * */
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",currentUser);
                startActivity(CurrentUserInfoSettingActivity.class, bundle);
                break;
            case R.id.info_config:
                /*
                * you'd better get the current user here(fill the bundle), and transmit it to CurrentUserInfoSettingActivity
                * or get the current user's information after starting CurrentUserInfoSettingActivity
                * it is up to you, but i prefer the former which is more efficient
                * */
                Bundle bundle1 = new Bundle();
                startActivity(CurrentUserInfoSettingActivity.class, bundle1);
                break;
            case R.id.img_schedule:
                /*
                * do nothing, because we don't have enough time
                * */
                break;
            case R.id.img_score:
                /*
                * do nothing, because we don't have enough time
                * */
                break;
            case R.id.img_flame:
                 /*
                * do nothing, because we don't have enough time
                * */
                break;
            case R.id.img_love:
                 /*
                * do nothing, because we don't have enough time
                * */
                break;
            case R.id.ll_my_teacher:
                /*
                * look over your teachers that you like
                * show them in another Activity
                * */
                break;
            case R.id.ll_my_student:
                /*
                * look over your students that you like
                * show them in another Activity
                * */
                break;
            case R.id.ll_my_orders:
                /*
                * look over your orders (you may be student or teacher, it is both ok)
                * show them in another Activity
                * */
                break;
            default:
                break;
        }
    }
}
