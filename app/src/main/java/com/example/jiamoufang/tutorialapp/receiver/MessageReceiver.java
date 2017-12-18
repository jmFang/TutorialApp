package com.example.jiamoufang.tutorialapp.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.jiamoufang.tutorialapp.R;
import com.orhanobut.logger.Logger;

import cn.bmob.newim.event.MessageEvent;
import cn.bmob.push.PushConstants;

/**
 * Created by jiamoufang on 2017/12/18.
 */

public class MessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String msg = intent.getStringExtra("msg");
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            Logger.i("客户端收到推送消息：" + msg);
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = new NotificationCompat.Builder(context)
                    .setContentTitle("新消息")
                    .setContentText(msg)
                    .build();
            manager.notify(1,notification);
        }
    }
}
