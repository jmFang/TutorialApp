package com.example.jiamoufang.tutorialapp.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.example.jiamoufang.tutorialapp.ui.base.BaseActivity;

import butterknife.Bind;

public class CurrentUserInfoSettingActivity extends BaseActivity implements View.OnClickListener{

    /*
    * user @Bind to get components here
    * and bind them in method onCreate()
    * */
   /* @Bind()*/

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_user_info_setting);
        /**/
        /*
        * get bundle of current user
        * getBundle is from BaseActivity
        * */
        currentUser = (User)getBundle().getSerializable("user");
        /*
        * initialize user info
        * */
        initUserInfo();
    }
    /*
    * initialize user info
    * by fangjiamou
    * */
    private void initUserInfo() {

    }

    /*
    * handlers for click events
    * by fangjiamou
    * */
    @Override
    public void onClick(View v) {

    }
}
