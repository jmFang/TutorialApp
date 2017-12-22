package com.example.jiamoufang.tutorialapp.model.bean;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.ui.activities.ChatActivity;

import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMConversationType;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;

/**
 * Created by jiamoufang on 2017/12/22.
 */

public class PrivateConversation extends Conversation{

    private BmobIMConversation conversation;
    private BmobIMMessage lastMsg;

    public PrivateConversation(BmobIMConversation conversation){
        this.conversation = conversation;
        cType = BmobIMConversationType.setValue(conversation.getConversationType());
        cId = conversation.getConversationId();
        if (cType == BmobIMConversationType.PRIVATE){
            name = conversation.getConversationTitle();
            if (TextUtils.isEmpty(name)) name = cId;
        }else{
            name = "未知会话";
        }
        List<BmobIMMessage> msgs =conversation.getMessages();
        if(msgs!=null && msgs.size()>0){
            lastMsg =msgs.get(0);
        }
    }

    @Override
    public void readAllMessages() {
        conversation.updateLocalCache();
    }

    @Override
    public Object getAvatar() {
        if (cType == BmobIMConversationType.PRIVATE){
            String avatar =  conversation.getConversationIcon();
            if (TextUtils.isEmpty(avatar)){//头像为空，使用默认头像
                return R.mipmap.icon_message_press;
            }else{
                return avatar;
            }
        }else{
            return R.mipmap.icon_message_press;
        }
    }

    @Override
    public String getLastMessageContent() {
        if(lastMsg!=null){
            String content =lastMsg.getContent();
            if(lastMsg.getMsgType().equals(BmobIMMessageType.TEXT.getType()) || lastMsg.getMsgType().equals("agree")){
                return content;
            }else if(lastMsg.getMsgType().equals(BmobIMMessageType.IMAGE.getType())){
                return "[图片]";
            }else if(lastMsg.getMsgType().equals(BmobIMMessageType.VOICE.getType())){
                return "[语音]";
            }else if(lastMsg.getMsgType().equals(BmobIMMessageType.LOCATION.getType())){
                return"[位置]";
            }else if(lastMsg.getMsgType().equals(BmobIMMessageType.VIDEO.getType())){
                return "[视频]";
            }else{//开发者自定义的消息类型，需要自行处理
                return "[未知]";
            }
        }else{//防止消息错乱
            return "";
        }
    }

    @Override
    public long getLastMessageTime() {
        if(lastMsg!=null) {
            return lastMsg.getCreateTime();
        }else{
            return 0;
        }
    }
    /*
    *
    * */
    @Override
    public int getUnReadCount() {
        //TODO 会话：4.3、查询指定会话下的未读消息数
        return (int) BmobIM.getInstance().getUnReadCount(conversation.getConversationId());
    }
    /*
    * 会话单击事件,需要传入parent的context才能启动另一个activity
    * @param context
    * */
    @Override
    public void onClick(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("c", conversation);
        if (bundle != null) {
            intent.putExtra(context.getPackageName(), bundle);
        }
        context.startActivity(intent);
    }
    /*
    * 会话长按事件,其实传入context是非必要的
    * @param context
    * */
    @Override
    public void onLongClick(Context context) {
        //TODO 会话：4.5、删除会话，以下两种方式均可以删除会话
        //BmobIM.getInstance().deleteConversation(conversation.getConversationId());
        BmobIM.getInstance().deleteConversation(conversation);
    }
}