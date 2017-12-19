package com.example.jiamoufang.tutorialapp.model.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 用户注册登录时的基本信息
 * Created by jiamoufang on 2017/12/18.
 */

public class User extends BmobUser {
    /*用户的更多信息
    * 类型为Pointer
    * */
    private BmobFile avatar;
    private Information moreInfo;

    public void setMoreInfo(Information moreInfo) {
        this.moreInfo = moreInfo;
    }

    public User(){}

    public Information getMoreInfo() {
        return moreInfo;
    }

    public void setAvatar(BmobFile avatar) {
        this.avatar = avatar;
    }

    public BmobFile getAvatar() {
        return avatar;
    }
}
