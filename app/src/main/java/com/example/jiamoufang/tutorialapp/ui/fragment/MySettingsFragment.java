package com.example.jiamoufang.tutorialapp.ui.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.factory.ImageLoaderFactory;
import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.example.jiamoufang.tutorialapp.ui.activities.CurrentUserInfoSettingActivity;
import com.example.jiamoufang.tutorialapp.ui.activities.MyOrdersActivity;
import com.example.jiamoufang.tutorialapp.ui.activities.MyStudentsActivity;
import com.example.jiamoufang.tutorialapp.ui.activities.MyTeachersActivity;
import com.example.jiamoufang.tutorialapp.ui.activities.OrderActivity;
import com.example.jiamoufang.tutorialapp.ui.base.ParentWithNaviFragment;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/*
* preparing work by fangjiamou
* */
public class MySettingsFragment  extends ParentWithNaviFragment{
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
        //get current user
        /*initialize*/
        initUserInfo();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Mydebug","执行了这个函数吗");
        initUserInfo();
    }

    private void initUserInfo() {
        currentUser = BmobUser.getCurrentUser(User.class);

        /*如果用户当前头像不为null
        * 尝试到云端数据库获取图片
        * */
        if (currentUser.getAvatar() != null) {
            //测试过URL是正确的
            Glide.with(this).load(currentUser.getAvatar().getUrl()).into(my_photo);
            // ImageLoaderFactory.getLoader().loadAvatar(, currentUser.getAvatar().getUrl(), R.mipmap.default_ss);
        } else {
            Glide.with(this).load(R.mipmap.default_ss).into(my_photo);
        }
        //用户实名or昵称or用户名
        if (currentUser.getRealName()!= null) {
            user_name.setText(currentUser.getRealName());
        } else {
            user_name.setText(currentUser.getUsername());
        }
        //用户角色
        if (currentUser.getRole()  == null) {
            user_identity.setText("未知");
        } else {
            user_identity.setText(currentUser.getRole() == true ? "教员":"学员");
        }
    }

    /*
    * 继承ParentWithNaviFragment，具体化NaviBar 标题
    * by fangjiamou
    * */
    @Override
    protected String title() {
        return null;
    }

    /*
    * handlers for click events
    * by fangjiamou
    * */
    @OnClick({R.id.info_config,R.id.my_photo,R.id.ll_my_teacher,R.id.ll_my_student, R.id.ll_my_orders})
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
                bundle1.putSerializable("user",currentUser);
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
                Bundle bundle6 = new Bundle();
                startActivity(MyTeachersActivity.class, bundle6);
                break;
            case R.id.ll_my_student:
                /*
                * look over your students that you like
                * show them in another Activity
                * */
                Bundle bundle7 = new Bundle();
                startActivity(MyStudentsActivity.class, bundle7);
                break;
            case R.id.ll_my_orders:
                /*
                * look over your orders (you may be student or teacher, it is both ok)
                * show them in another Activity
                * */
                Bundle bundle8 = new Bundle();
                bundle8.putSerializable("user", currentUser);
                startActivity(MyOrdersActivity.class, bundle8);
                break;
            default:
                break;
        }
    }
}
