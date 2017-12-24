package com.example.jiamoufang.tutorialapp.model.bean;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import java.io.Serializable;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMConversationType;

/**
 * Created by jiamoufang on 2017/12/21.
 * 对BmobIMConversation的抽象封装，方便扩展会话类型
 */
public abstract class Conversation implements Serializable,Comparable{
    /*
    * 会话ID
    * */
    protected String cId;
    /*
    * 会话类型
    * */
    protected BmobIMConversationType cType;

    /*
    * 会话名称
    * */
    protected String name;
    /*
    * 获取头像，用于会话界面
    * */
    abstract public Object getAvatar();
    /*
    * 获取最后一条消息的时间
    * */
    abstract public long getLastMessageTime();
    /*
    * 获取最后一条消息的内容
    * */
    abstract public String getLastMessageContent();
    /*
    * 获取未读会话的个数
    * */
    abstract public int getUnReadCount();
    /**
     * 将所有消息标记为已读
     */
    abstract public void readAllMessages();

    /*TODO click事件
    * @param context
    * */
    abstract public void onClick(Context context);

    /*TODO long click事件
    * @param context
    * */
    abstract public void onLongClick(Context context);

    /*
    * 获取会话名称
    * */

    public String getName() {
        return name;
    }
    /*
    * 获取会话ID
    * */

    public String getcId() {
        return cId;
    }
    /*
    * 获取会话类型
    * */

    public BmobIMConversationType getcType() {
        return cType;
    }
    /*
    * 重写equals方法，用于比较两个会话
    * */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        // obj是一个Conversation对象，copy一个副本
        Conversation that = (Conversation) obj;
        //cid不同则不是同一个会话
        if (!cId.equals(that.cId)) return false;
        //类型不同也不是同一个会话
        return cType == that.cType;
    }

    /*
    * 重写hashCode方法，对会话id进行加密
    * */

    @Override
    public int hashCode() {
        int result = cId.hashCode();
        result = 31 * result + cType.hashCode();
        return result;
    }
    /*
    * 重写compareTo方法，比较两个会话的时间先后
    * */

    @Override
    public int compareTo(@NonNull Object object) {
        //如果object是Conversation类型
        if (object instanceof Conversation) {
            //copy一个副本
            Conversation anotherConversation = (Conversation) object;
            long timeGap = anotherConversation.getLastMessageTime() - getLastMessageTime();
            //我们很熟悉的compare方法的返回值
            if (timeGap > 0) return 1;
            else if (timeGap < 0) return -1;
            return 0;
        } else {
            throw new ClassCastException();
        }
    }
}
