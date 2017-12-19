package com.example.jiamoufang.tutorialapp.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.event.RefreshEvent;
import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.example.jiamoufang.tutorialapp.ui.base.BaseActivity;
import com.example.jiamoufang.tutorialapp.ui.fragment.ConversationFragment;
import com.example.jiamoufang.tutorialapp.ui.fragment.HomePageFragment;
import com.example.jiamoufang.tutorialapp.ui.fragment.MySettingsFragment;
import com.example.jiamoufang.tutorialapp.ui.fragment.ShareFragment;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConversationListener;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

public class MainActivity extends BaseActivity{
    /*四个Tab按钮*/
    @Bind(R.id.btn_conversation)
    TextView btn_conversation;
    @Bind(R.id.btn_settings)
    TextView btn_settings;
    @Bind(R.id.btn_home)
    TextView btn_home;
    @Bind(R.id.btn_share)
    TextView btn_share;

    /*消息tips*/
    @Bind(R.id.iv_conversation_tips)
    ImageView iv_conversation_tips;
    /*主页tips*/
    @Bind(R.id.iv_home_tips)
    ImageView iv_home_tips;
    /*底部四个tab所在的容器*/
    @Bind(R.id.main_bottom)
    LinearLayout mMainBottom;
    /*分割线*/
    @Bind(R.id.line)
    LinearLayout mLine;
    /*fragment容器*/
    @Bind(R.id.fragment_container)
    RelativeLayout mFragmentContainer;

    private TextView[] mTabs;
    private ConversationFragment conversationFragment;
    private HomePageFragment homePageFragment;
    private MySettingsFragment mySettingsFragment;
    private ShareFragment shareFragment;

    private int index;
    private int currentTabIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final User user = BmobUser.getCurrentUser(User.class);
        /*TODO 连接：2.1 登录成功、注册成功后或处于登录状态重新打开应用后执行连接IM服务器的操作
        * 判断用户是否登录，并且当连接状态是未连接，则进行连接
        * */
        if (!TextUtils.isEmpty(user.getObjectId())
                && BmobIM.getInstance().getCurrentStatus().getCode() != ConnectionStatus.CONNECTED.getCode()) {
            BmobIM.connect(user.getObjectId(), new ConnectListener() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        //服务器连接成功后发送一个更新事件，同步更新聊天会话和主页的小红点
                        EventBus.getDefault().post(new RefreshEvent());
                        //TODO 会话 3.1 更新用户资料，用户再会话聊天界面以及个人信息页面显示
                        // 应该传入MoreInfo的objectId
                        BmobIM.getInstance().updateUserInfo(new BmobIMUserInfo(user.getObjectId(), user.getUsername(),user.getMoreInfo().getObjectId()));
                    }
                }
            });
        }

    }

}
