package com.example.jiamoufang.tutorialapp.model;

import android.icu.lang.UScript;
import android.nfc.NfcEvent;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.orhanobut.logger.LogLevel;

import org.w3c.dom.Text;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DeleteBatchListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by jiamoufang on 2017/12/18.
 */

public class UserModel extends BaseModel {
    /*
    * 单例模式
    * 提供静态实例
    * */

    private static UserModel ourInstance = new UserModel();
    public static UserModel getInstance() {
        return ourInstance;
    }
    private UserModel(){}

    /* TODO 用户管理：1.1 登录
    * @param username
    * @param password
    * @param listener
    * */
    public void login(String username, String password, final LogInListener listener) {
        if (TextUtils.isEmpty(username)) {
            listener.done(null, new BmobException(CODE_NULL, "请填写用户名"));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            listener.done(null, new BmobException(CODE_NULL, "请填写密码"));
            return;
        }
        final User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    Toast.makeText(getContext(), "userId:"+ user.getObjectId(), Toast.LENGTH_SHORT).show();
                    listener.done(getCurrentUser(), null);
                } else {
                    listener.done(user,e);
                }
            }
        });
    }

    /*TODO 用户管理：1.2 获取当前用户
    * @return
    * */
    public User getCurrentUser() {
        return BmobUser.getCurrentUser(User.class);
    }

    /*TODO 用户管理： 1.3 注册
    * @param username
    * @param password
    * @param pwdagain
    * @param listener
    * */

    public void register(String username, String password, String pwdagain, final LogInListener listener) {
        if (TextUtils.isEmpty(username)) {
            listener.done(null, new BmobException(CODE_NULL, "请填写用户名"));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            listener.done(null, new BmobException(CODE_NULL, "请填写密码"));
            return;
        }
        if (TextUtils.isEmpty(pwdagain)) {
            listener.done(null, new BmobException(CODE_NULL, "请填写确认密码"));
            return;
        }
        if (!password.equals(pwdagain)) {
            listener.done(null, new BmobException(CODE_NULL, "两次密码输入不一致，请重新输入"));
            return;
        }
        final User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    listener.done(null, null);
                    Toast.makeText(getContext(), "userId:"+ user.getObjectId(), Toast.LENGTH_SHORT).show();
                } else {
                    listener.done(null, e);
                }
            }
        });
    }

    /*TODO 用户管理：1.4 登出
    * @return
    * */
    public void logout() {
        BmobUser.logOut();
    }



}
