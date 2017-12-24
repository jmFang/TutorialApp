package com.example.jiamoufang.tutorialapp.model.intface;

import cn.bmob.newim.listener.BmobListener1;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by jiamoufang on 2017/12/19.
 */

public abstract class UpdateCacheListener extends BmobListener1 {
    public abstract void done(BmobException e);

    @Override
    protected void postDone(Object o, BmobException e) {
        done(e);
    }
}
