package com.example.jiamoufang.tutorialapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.jiamoufang.tutorialapp.ui.activities.MainActivity;

import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.BmobIMMessageHandler;

/**
 * Created by jiamoufang on 2017/12/18.
 */

public class DemoMessageHandler extends BmobIMMessageHandler {

    private Context context;
    public DemoMessageHandler(Context context) {
        this.context = context;
    }
    @Override
    public void onMessageReceive(final MessageEvent event) {
        //当接收到服务器发来的消息时，此方法被调用
        super.onMessageReceive(event);
        Log.d("收到",event.getMessage().toString());
        MainActivity.tv_message.append("接收到："+event.getMessage().getContent().toString()+"\n");
    }

    @Override
    public void onOfflineReceive(final OfflineMessageEvent event) {
        //每次调用connect方法时会查询一次离线消息，如果有，此方法会被调用
    }
}
