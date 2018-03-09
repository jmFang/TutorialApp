package com.example.jiamoufang.tutorialapp.adapter;

        import android.content.Context;
        import android.support.v7.widget.RecyclerView;
        import android.view.ViewGroup;

        import com.example.jiamoufang.tutorialapp.adapter.base.BaseViewHolder;
        import com.example.jiamoufang.tutorialapp.adapter.holder.AgreeHolder;
        import com.example.jiamoufang.tutorialapp.adapter.holder.ReceiveImageHolder;
        import com.example.jiamoufang.tutorialapp.adapter.holder.ReceiveLocationHolder;
        import com.example.jiamoufang.tutorialapp.adapter.holder.ReceiveTextHolder;
        import com.example.jiamoufang.tutorialapp.adapter.holder.ReceiveVideoHolder;
        import com.example.jiamoufang.tutorialapp.adapter.holder.ReceiveVoiceHolder;
        import com.example.jiamoufang.tutorialapp.adapter.holder.SendImageHolder;
        import com.example.jiamoufang.tutorialapp.adapter.holder.SendLocationHolder;
        import com.example.jiamoufang.tutorialapp.adapter.holder.SendVideoHolder;
        import com.example.jiamoufang.tutorialapp.adapter.holder.SendVoiceHolder;
        import com.example.jiamoufang.tutorialapp.adapter.listener.OnRecyclerViewListener;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;

        import cn.bmob.newim.bean.BmobIMConversation;
        import cn.bmob.newim.bean.BmobIMMessage;
        import cn.bmob.newim.bean.BmobIMMessageType;
        import cn.bmob.v3.BmobUser;

/**
 * Created by jiamoufang on 2017/12/22.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //文本
    private final int TYPE_RECEIVER_TXT = 0;
    private final int TYPE_SEND_TXT = 1;
    //图片
    private final int TYPE_SEND_IMAGE = 2;
    private final int TYPE_RECEIVER_IMAGE = 3;
    //位置
    private final int TYPE_SEND_LOCATION = 4;
    private final int TYPE_RECEIVER_LOCATION = 5;
    //语音
    private final int TYPE_SEND_VOICE =6;
    private final int TYPE_RECEIVER_VOICE = 7;
    //视频
    private final int TYPE_SEND_VIDEO =8;
    private final int TYPE_RECEIVER_VIDEO = 9;

    //同意添加好友成功后的样式
    private final int TYPE_AGREE = 10;

    /**
     * 显示时间间隔:10分钟
     */
    private final long TIME_INTERVAL = 10 * 60 * 1000;
    private List<BmobIMMessage> msgs = new ArrayList<>();

    /*监听器*/
    private OnRecyclerViewListener onRecyclerViewListener;

    private String currentUid="";
    BmobIMConversation mConversation;

    /*
    * 构造方法
    * */
    public ChatAdapter(Context context, BmobIMConversation conversation) {
        try {
            currentUid = BmobUser.getCurrentUser().getObjectId();
        }catch (Exception e) {
            e.printStackTrace();
        }
        this.mConversation = conversation;
    }

    /*
    * 获取消息的数量
    * */
    public int getCount() {
        return this.msgs == null ? 0 : msgs.size();
    }
    /*
    * 获取消息
    * @param position
    * */
        public BmobIMMessage getItem(int position) {
            return this.msgs == null ? null : (position >= this.msgs.size() ? null : this.msgs.get(position));
        }

    /*
    * 确定消息所属的位置
    * @param message
    * @return position
    * */
    public int findPosition(BmobIMMessage message) {
        int index = this.getCount();
        int position = -1;
        while ( index-- > 0) {
            if (message.equals(this.getItem(index))) {
                position = index;
                break;
            }
        }
        return position;
    }

    /*
    * 确定消息所属的位置
    * @param id
    * @return position
    * */
    public int findPosition(long id) {
        int index = this.getCount();
        int position = -1;
        while (index-- > 0) {
            if (this.getItemId(index) == id) {
                position = index;
                break;
            }
        }
        return position;
    }

    /*
    * 添加消息:向前插入
    * @param messages
    * */
    public void addMessages(List<BmobIMMessage> messages) {
        msgs.addAll(0,messages);
        notifyDataSetChanged();
    }

    /*
    * 添加消息：向后添加
    * */
    public void addMessage(BmobIMMessage message) {
        msgs.addAll(Arrays.asList(message));
        notifyDataSetChanged();
    }

    /*
    * 移除消息
    * @param position
    * */
    public void remove(int position) {
        msgs.remove(position);
        notifyDataSetChanged();
    }

    /*
    * 获取最新的一条消息
    * @return
    * */
    public BmobIMMessage getFirstMessage() {
        if (msgs != null && msgs.size() > 0) {
            return msgs.get(0);
        } else {
            return null;
        }
    }

    /*
    * 初始化：加载布局
    * */

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_SEND_TXT) {
            return new SendTextHolder(parent.getContext(), parent, mConversation, onRecyclerViewListener);
        } else if (viewType == TYPE_SEND_IMAGE) {
            return new SendImageHolder(parent.getContext(), parent, mConversation, onRecyclerViewListener);
        } else if (viewType == TYPE_SEND_LOCATION) {
            return new SendLocationHolder(parent.getContext(), parent, mConversation, onRecyclerViewListener);
        } else if (viewType == TYPE_SEND_VOICE) {
            return new SendVoiceHolder(parent.getContext(), parent, mConversation, onRecyclerViewListener);
        } else if (viewType == TYPE_RECEIVER_TXT) {
            return new ReceiveTextHolder(parent.getContext(), parent, onRecyclerViewListener);
        } else if (viewType == TYPE_RECEIVER_IMAGE) {
            return new ReceiveImageHolder(parent.getContext(), parent, onRecyclerViewListener);
        } else if (viewType == TYPE_RECEIVER_LOCATION) {
            return new ReceiveLocationHolder(parent.getContext(), parent, onRecyclerViewListener);
        } else if (viewType == TYPE_RECEIVER_VOICE) {
            return new ReceiveVoiceHolder(parent.getContext(), parent, onRecyclerViewListener);
        } else if (viewType == TYPE_SEND_VIDEO) {
            return new SendVideoHolder(parent.getContext(), parent,mConversation, onRecyclerViewListener);
        } else if (viewType == TYPE_RECEIVER_VIDEO) {
            return new ReceiveVideoHolder(parent.getContext(), parent, onRecyclerViewListener);
        }else if(viewType ==TYPE_AGREE) {
            return new AgreeHolder(parent.getContext(),parent,onRecyclerViewListener);
        }else{//开发者自定义的其他类型，可自行处理
            return null;
        }
    }

    /*
    * 数据绑定
    * */

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((BaseViewHolder)holder).bindData(msgs.get(position));

        if (holder instanceof ReceiveTextHolder) {
            ((ReceiveTextHolder)holder).showTime(shouldShowTime(position));
        } else if (holder instanceof ReceiveImageHolder) {
            ((ReceiveImageHolder)holder).showTime(shouldShowTime(position));
        }else if (holder instanceof ReceiveLocationHolder) {
            ((ReceiveLocationHolder)holder).showTime(shouldShowTime(position));
        }else if (holder instanceof ReceiveVoiceHolder) {
            ((ReceiveVoiceHolder)holder).showTime(shouldShowTime(position));
        }else if (holder instanceof SendTextHolder) {
            ((SendTextHolder)holder).showTime(shouldShowTime(position));
        }else if (holder instanceof SendImageHolder) {
            ((SendImageHolder)holder).showTime(shouldShowTime(position));
        }else if (holder instanceof SendLocationHolder) {
            ((SendLocationHolder)holder).showTime(shouldShowTime(position));
        }else if (holder instanceof SendVoiceHolder) {
            ((SendVoiceHolder)holder).showTime(shouldShowTime(position));
        }else if (holder instanceof SendVideoHolder) {//随便模拟的视频类型
            ((SendVideoHolder)holder).showTime(shouldShowTime(position));
        }else if (holder instanceof ReceiveVideoHolder) {
            ((ReceiveVideoHolder)holder).showTime(shouldShowTime(position));
        }else if (holder instanceof AgreeHolder) {//同意添加好友成功后的消息
            ((AgreeHolder)holder).showTime(shouldShowTime(position));
        }
    }

    /*
    * 获取消息类型
    * */
    @Override
    public int getItemViewType(int position) {
        BmobIMMessage message = msgs.get(position);
        if(message.getMsgType().equals(BmobIMMessageType.IMAGE.getType())){
            return message.getFromId().equals(currentUid) ? TYPE_SEND_IMAGE: TYPE_RECEIVER_IMAGE;
        }else if(message.getMsgType().equals(BmobIMMessageType.LOCATION.getType())){
            return message.getFromId().equals(currentUid) ? TYPE_SEND_LOCATION: TYPE_RECEIVER_LOCATION;
        }else if(message.getMsgType().equals(BmobIMMessageType.VOICE.getType())){
            return message.getFromId().equals(currentUid) ? TYPE_SEND_VOICE: TYPE_RECEIVER_VOICE;
        }else if(message.getMsgType().equals(BmobIMMessageType.TEXT.getType())){
            return message.getFromId().equals(currentUid) ? TYPE_SEND_TXT: TYPE_RECEIVER_TXT;
        }else if(message.getMsgType().equals(BmobIMMessageType.VIDEO.getType())){
            return message.getFromId().equals(currentUid) ? TYPE_SEND_VIDEO: TYPE_RECEIVER_VIDEO;
        }else if(message.getMsgType().equals("agree")) {//显示欢迎
            return TYPE_AGREE;
        }else{
            return -1;
        }
    }

    /*
    * 获取消息数目
    * */
    @Override
    public int getItemCount() {
        return msgs.size();
    }

    /*
    * 设置消息监听器
    * */
    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    /*
    * 的否显示消息时间
    * */
    private boolean shouldShowTime(int position) {
        if (position == 0) {
            return true;
        }
        long lastTime = msgs.get(position - 1).getCreateTime();
        long curTime = msgs.get(position).getCreateTime();
        return curTime - lastTime > TIME_INTERVAL;
    }
}
