package com.example.jiamoufang.tutorialapp.adapter.holder;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.adapter.base.BaseViewHolder;
import com.example.jiamoufang.tutorialapp.adapter.listener.OnRecyclerViewListener;
import com.example.jiamoufang.tutorialapp.factory.ImageLoaderFactory;
import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.example.jiamoufang.tutorialapp.ui.activities.UserInfoActivity;

import java.text.SimpleDateFormat;

import butterknife.Bind;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMImageMessage;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMSendStatus;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by jiamoufang on 2017/12/22.
 */

public class SendImageHolder extends BaseViewHolder {

    @Bind(R.id.iv_avatar)
    protected ImageView iv_avatar;

    @Bind(R.id.iv_fail_resend)
    protected ImageView iv_fail_resend;

    @Bind(R.id.tv_time)
    protected TextView tv_time;

    @Bind(R.id.iv_picture)
    protected ImageView iv_picture;

    @Bind(R.id.tv_send_status)
    protected TextView tv_send_status;

    @Bind(R.id.progress_load)
    protected ProgressBar progress_load;
    BmobIMConversation mConversation;

    public SendImageHolder(Context context, ViewGroup root, BmobIMConversation conversation, OnRecyclerViewListener listener) {
        super(context, root, R.layout.item_chat_sent_image, listener);
        this.mConversation = conversation;
    }

    @Override
    public void bindData(Object object) {
        BmobIMMessage msg = (BmobIMMessage) object;
        /*
        * 获取用户信息
        * 必须在buildFromDB之前，否则会报错'Entity is detached from DAO context'
        * */
        final BmobIMUserInfo info = msg.getBmobIMUserInfo();
        ImageLoaderFactory.getLoader(mContext).loadAvatar(iv_avatar,
                info != null ? info.getAvatar() : null, R.mipmap.default_ss);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = dateFormat.format(msg.getCreateTime());
        tv_time.setText(time);
        /*
        * 从DB中获取Message?
        * 这句代码是何意，何用处
        * */
        final BmobIMImageMessage message = BmobIMImageMessage.buildFromDB(true, msg);
        //获取发送状态
        int status = message.getSendStatus();
        /*
        * 如果是发送失败或者图片上传失败,显示发送失败的信息
        * */
        if (status == BmobIMSendStatus.SEND_FAILED.getStatus() || status == BmobIMSendStatus.UPLOAD_FAILED.getStatus()) {
            iv_fail_resend.setVisibility(View.VISIBLE);
            progress_load.setVisibility(View.GONE);
            tv_send_status.setVisibility(View.INVISIBLE);
        } else if (status == BmobIMSendStatus.SENDING.getStatus()) { //正在发送中
            progress_load.setVisibility(View.VISIBLE);
            iv_fail_resend.setVisibility(View.GONE);
            tv_send_status.setVisibility(View.INVISIBLE);
        } else {  //已发送
            tv_send_status.setVisibility(View.VISIBLE);
            tv_send_status.setText("已发送");
            iv_fail_resend.setVisibility(View.GONE);
            progress_load.setVisibility(View.GONE);
        }
        /*
        * 如果发送的不是远程图片地址，则取本地地址
        * */
        ImageLoaderFactory.getLoader(mContext).load(iv_picture,
                TextUtils.isEmpty(message.getRemoteUrl())?message.getLocalPath(): message.getRemoteUrl(),R.mipmap.default_ss,null);
        /*
        * 点击头像，显示聊天对象的个人资料
        * */
        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("点击" + info.getName() + "的头像");
                Bundle bundle = new Bundle();
                User user = new User();
                BmobFile bmobFile = new BmobFile();
                bmobFile.setUrl(info.getAvatar());
                user.setAvatar(bmobFile);
                user.setUsername(info.getName());
                user.setObjectId(info.getUserId());
                bundle.putSerializable("u", user);
                startActivity(UserInfoActivity.class, bundle);
            }
        });
        /*
        * 点击发送的图片
        * */
        iv_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("点击图片：" + (TextUtils.isEmpty(message.getRemoteUrl())? message.getLocalPath() : message.getRemoteUrl()) + "");
                if (onRecyclerViewListener != null) {
                    onRecyclerViewListener.onItemClick(getAdapterPosition());
                }
            }
        });
        iv_picture.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onRecyclerViewListener != null) {
                    onRecyclerViewListener.onItemLongClick(getAdapterPosition());
                }
                return true;
            }
        });
        /*
        * 点击progressBar，重发
        * */
        iv_fail_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConversation.resendMessage(message, new MessageSendListener() {
                    @Override
                    public void done(BmobIMMessage bmobIMMessage, BmobException e) {
                        if (e == null) {
                            tv_send_status.setVisibility(View.VISIBLE);
                            tv_send_status.setText("已发送");
                            iv_fail_resend.setVisibility(View.GONE);
                            progress_load.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onStart(BmobIMMessage bmobIMMessage) {
                        progress_load.setVisibility(View.VISIBLE);
                        iv_fail_resend.setVisibility(View.GONE);
                        tv_send_status.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }

    public void showTime(boolean isShow) {
        tv_time.setVisibility(isShow? View.VISIBLE : View.GONE);
    }
}
