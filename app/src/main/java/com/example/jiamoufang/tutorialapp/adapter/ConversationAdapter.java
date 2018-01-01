package com.example.jiamoufang.tutorialapp.adapter;

import android.content.Context;
import android.view.View;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.adapter.base.BaseRecyclerAdapter;
import com.example.jiamoufang.tutorialapp.adapter.base.BaseRecyclerHolder;
import com.example.jiamoufang.tutorialapp.adapter.base.IMultipleItem;
import com.example.jiamoufang.tutorialapp.model.bean.Conversation;
import com.example.jiamoufang.tutorialapp.utils.TimeUtil;

import java.util.Collection;

import cn.bmob.newim.bean.BmobIMConversationType;

/**
 * Created by jiamoufang on 2017/12/21.
 * 使用进一步封装的
 */

public class ConversationAdapter extends BaseRecyclerAdapter<Conversation>{

    private Context mContext;

    public ConversationAdapter(Context context, IMultipleItem<Conversation> items, Collection<Conversation> datas) {
        super(context, items, datas);
        mContext = context;
    }

    @Override
    public void bindView(BaseRecyclerHolder holder, Conversation conversation, int position) {
        holder.setText(R.id.tv_recent_msg,conversation.getLastMessageContent());
        holder.setText(R.id.tv_recent_time, TimeUtil.getChatTime(false,conversation.getLastMessageTime()));
        //会话图标
        Object obj = conversation.getAvatar();
        if(obj instanceof String){
            String avatar=(String)obj;
            holder.setImageView(avatar, R.mipmap.default_ss, R.id.iv_recent_avatar);
        }else{
            int defaultRes = (int)obj;
            holder.setImageView(null, defaultRes, R.id.iv_recent_avatar);
        }
        //会话标题
        holder.setText(R.id.tv_recent_name, conversation.getName());
        //查询指定未读消息数
        long unread = conversation.getUnReadCount();
        if(unread>0){
            holder.setVisible(R.id.tv_recent_unread, View.VISIBLE);
            holder.setText(R.id.tv_recent_unread, String.valueOf(unread));
        }else{
            holder.setVisible(R.id.tv_recent_unread, View.GONE);
        }
    }

    /**
     * 获取指定会话类型指定会话id的会话位置
     * @param type
     * @param targetId
     * @return
     */
    public int findPosition(BmobIMConversationType type, String targetId) {
        int index = this.getCount();
        int position = -1;
        while(index-- > 0) {
            if((getItem(index)).getcType().equals(type) && (getItem(index)).getcId().equals(targetId)) {
                position = index;
                break;
            }
        }
        return position;
    }

}
