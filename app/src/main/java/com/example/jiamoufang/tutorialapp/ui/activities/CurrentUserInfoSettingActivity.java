package com.example.jiamoufang.tutorialapp.ui.activities;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.example.jiamoufang.tutorialapp.ui.base.BaseActivity;
import com.example.jiamoufang.tutorialapp.ui.base.ParentWithNaviActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class CurrentUserInfoSettingActivity extends ParentWithNaviActivity{

    /*
    * user @Bind to get components here
    * and bind them in method onCreate()
    * */
   /* @Bind()*/
   @Bind(R.id.ll_my_avatar)
    ConstraintLayout ll_my_avatar;
    @Bind(R.id.ll_my_nick)
    ConstraintLayout ll_my_nick;
    @Bind(R.id.ll_my_role)
    ConstraintLayout ll_my_role;
    @Bind(R.id.ll_my_sex)
    ConstraintLayout ll_my_sex;
    @Bind(R.id.ll_my_city)
    ConstraintLayout ll_my_city;
    @Bind(R.id.ll_my_address)
    ConstraintLayout ll_my_address;
    @Bind(R.id.ll_my_phone)
    ConstraintLayout ll_my_phone;
    @Bind(R.id.ll_my_weChat)
    ConstraintLayout ll_my_weChat;
    @Bind(R.id.ll_my_password)
    ConstraintLayout ll_my_password;
    //退出当前用户
    @Bind(R.id.bt_logout)
    Button bt_logout;

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_user_info_setting);
        //初始化导航栏
        initNaviView();
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
    * @by fangjiamou
    * */
    private void initUserInfo() {

    }

   /*
    * handlers for click events
    * @by fangjiamou
    * */
    @OnClick({R.id.ll_my_avatar, R.id.ll_my_nick,R.id.ll_my_role,R.id.ll_my_sex,R.id.ll_my_city,R.id.ll_my_address,
            R.id.ll_my_phone,R.id.ll_my_weChat,R.id.ll_my_password,R.id.bt_logout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_my_avatar:
                handlerForChangeAvatar();
                break;
            case R.id.ll_my_nick:
                handlerForChangeNickname();
                break;
            case R.id.ll_my_role:
                handlerForChangeRole();
                break;
            case R.id.ll_my_sex:
                handlerForChangeSex();
                break;
            case R.id.ll_my_city:
                handlerForChangeCity();
                break;
            case R.id.ll_my_address:
                handlerForChangeAddress();
                break;
            case R.id.ll_my_phone:
                handlerForChangeMyPhone();
                break;
            case R.id.ll_my_weChat:
                handlerForChangeMyWeChat();
                break;
            case R.id.ll_my_password:
                handlerForChangeMyPassword();
                break;
            case R.id.bt_logout:
                saveAllChanges();// all changes are saved before logout
                handlerForLogout();
                break;
            default:
                break;
        }
    }

    /*
    * TODO Click 1.10 :click R.id.bt_logout
    * attention!!!
    * you should finish all the running activities before you logout
    * when you logout, just jump to the LogActivity(登录界面)
    * @fangjiamou
    * */
    private void handlerForLogout() {

    }
    /*
    * TODO Click 1.9 :click R.id.ll_my_password
    * tips: refers to TODO Click 1.1:click R.id.ll_my_nick
    * */
    private void handlerForChangeMyPassword() {
    }
    /*
    * TODO Click 1.8 :click R.id.ll_my_weChat
    * tips: refers to TODO Click 1.1:click R.id.ll_my_nick
    * */
    private void handlerForChangeMyWeChat() {
    }
    /*
    * TODO Click 1.7 :click R.id.ll_my_phone
    * tips: refers to TODO Click 1.1:click R.id.ll_my_nick
    * */
    private void handlerForChangeMyPhone() {
    }
    /*
    * TODO Click 1.6 :click R.id.ll_my_address
    * tips: refers to TODO Click 1.1:click R.id.ll_my_nick
    * */
    private void handlerForChangeAddress() {
    }
    /*
    * TODO Click 1.5 :click R.id.ll_my_city
    * tips: refers to TODO Click 1.1:click R.id.ll_my_nick
    * */
    private void handlerForChangeCity() {
    }

    /*
    * TODO Click 1.4 :click R.id.ll_my_sex
    * tips: refers to TODO Click 1.1:click R.id.ll_my_nick
    * */
    private void handlerForChangeSex() {
    }

    /*
    * TODO Click 1.3: click R.id.ll_my_role
    * tips: refers to TODO Click 1.1:click R.id.ll_my_nick
    * */
    private void handlerForChangeRole() {
    }

    /*
    * TODO Click 1.2:click R.id.ll_my_avatar:
    * tips: fetch picture from external storage or have a picture taken
    *       then replace the current picture
    *       @fangjiamou
    * */
    private void handlerForChangeAvatar() {

    }
    /*
    * TODO Click 1.1:click R.id.ll_my_nick
    * tips: pop out a dialog to update the nickname
    *       or, make the TextView to EditText,but you should mind that before the user clicks the EditText
    *       it should be like a "TextView"
    * */
    private void handlerForChangeNickname() {
    }
    /*
    * set toolBar's title，here it should return null
    * @return null
    * */
    @Override
    protected String title() {
        return "我的资料";
    }
    /*
    * set click listener for left or right clickable components
    * @by fangjiamou
    * */
    @Override
    protected void setNaviListener(ToolBarListener listener) {
        super.setNaviListener(new ToolBarListener() {
            @Override
            public void clickLeft() {
                saveAllChanges();
                finish();
            }

            @Override
            public void clickRight() {
                //do nothing
            }
        });
    }
    /*
    * tips: save all changes, and update those changes in cloud-server
    *       when the user click return, this method should be called.
    *       @fangjiamou
    * */
    private void saveAllChanges() {

    }
}
