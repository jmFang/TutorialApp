package com.example.jiamoufang.tutorialapp.model.intface;

import com.example.jiamoufang.tutorialapp.model.bean.User;

import cn.bmob.newim.listener.BmobListener1;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by jiamoufang on 2017/12/19.
 */

/* 用户查询监听器
* 在UserModel 的 updateUserInfo 方法会用到
* 届时会重写done
* */
public abstract class QueryUserListener extends BmobListener1<User> {
    public abstract void done(User user, BmobException e);
    @Override
    protected void postDone(User user, BmobException e) {
        done(user, e);
    }
}
