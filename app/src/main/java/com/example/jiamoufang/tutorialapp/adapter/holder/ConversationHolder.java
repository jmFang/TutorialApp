package com.example.jiamoufang.tutorialapp.adapter.holder;

import android.content.Context;
import android.media.Image;
import android.util.TimeUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.adapter.base.BaseViewHolder;
import com.example.jiamoufang.tutorialapp.adapter.listener.OnRecyclerViewListener;
import com.example.jiamoufang.tutorialapp.utils.TimeUtil;
import com.example.jiamoufang.tutorialapp.utils.ViewUtil;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.Bind;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMMessageType;

/**
 * Created by jiamoufang on 2017/12/21.
 */

public class ConversationHolder extends BaseViewHolder {

    @Bind(R.id.iv_recent_avatar)
    public ImageView iv_recent_avatar;
    @Bind(R.id.tv_recent_name)
    public TextView tv_recent_name;
    @Bind(R.id.tv_recent_msg)
    public TextView tv_recent_msg;
    @Bind(R.id.tv_recent_time)
    public TextView tv_recent_time;
    @Bind(R.id.tv_recent_unread)
    public TextView tv_recent_unread;
    private Context mContext;

    public ConversationHolder(Context context, ViewGroup root, OnRecyclerViewListener listener) {
        super(context, root, R.layout.item_conversation, listener);
        mContext = context;
    }
    @Override
    public void bindData(Object object) {
        BmobIMConversation conversation = (BmobIMConversation)object;
        List<BmobIMMessage> msgs = conversation.getMessages();

        if(msgs != null && msgs.size() > 0) {
            BmobIMMessage lastMsg = msgs.get(0);
            String content = lastMsg.getContent();
            if (lastMsg.getMsgType().equals(BmobIMMessageType.TEXT.getType())) {
                tv_recent_msg.setText(content);
            } else if (lastMsg.getMsgType().equals(BmobIMMessageType.IMAGE.getType())) {
                tv_recent_msg.setText("[图片]");
            } else if (lastMsg.getMsgType().equals(BmobIMMessageType.VOICE.getType())) {
                tv_recent_msg.setText("[语音]");
            } else if (lastMsg.getMsgType().equals(BmobIMMessageType.LOCATION.getType())) {
                tv_recent_msg.setText("[位置]" + content);
            } else {
                tv_recent_msg.setText("未知"); //自定义消息类型
            }
            tv_recent_time.setText(TimeUtil.getChatTime(false, lastMsg.getCreateTime()));
        }
        /*
        * 会话图标设置
        * */
        if (conversation.getConversationIcon() != null)
            Glide.with(mContext).load(conversation.getConversationIcon()).into(iv_recent_avatar);
        else
            Glide.with(mContext).load( R.mipmap.icon_message_press).into(iv_recent_avatar);
        //ViewUtil.setAvatar(conversation.getConversationIcon(), R.mipmap.default_ss, iv_recent_avatar);
        /*
        * 会话标题
        * */
        tv_recent_name.setText(conversation.getConversationTitle());
        /*
        * 查询指定会话下的未读消息数
        * 设置未读的红点和消息数
        * */
        long unread = BmobIM.getInstance().getUnReadCount(conversation.getConversationId());
        if (unread > 0) {
            tv_recent_unread.setVisibility(View.VISIBLE);
            tv_recent_unread.setText(String.valueOf(unread));
        } else {
            tv_recent_unread.setVisibility(View.GONE);
        }
    }

}
