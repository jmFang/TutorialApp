package com.example.jiamoufang.tutorialapp.ui.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.example.jiamoufang.tutorialapp.model.UserModel;
import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.example.jiamoufang.tutorialapp.ui.base.BaseActivity;
import com.example.jiamoufang.tutorialapp.ui.fragment.ConversationFragment;
import com.example.jiamoufang.tutorialapp.ui.fragment.HomePageFragment;
import com.example.jiamoufang.tutorialapp.ui.fragment.MySettingsFragment;
import com.example.jiamoufang.tutorialapp.ui.fragment.ShareFragment;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
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
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.newim.listener.ConversationListener;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.newim.notification.BmobNotificationManager;
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
    private Fragment mCuurentFragment = null;

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
                        BmobIM.getInstance().updateUserInfo(new BmobIMUserInfo(user.getObjectId(), user.getUsername(),
                                user.getMoreInfo() == null? null:user.getMoreInfo().getObjectId()));
                    } else {
                        toast(e.getMessage());
                    }
                }
            });

            /*TODO 连接：2.2 登录连接状态
            * 可通过BmobIM.getInstance().getCurrentStatus()获取当前长连接状态
            * */
            BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
                @Override
                public void onChange(ConnectionStatus connectionStatus) {
                    toast(connectionStatus.getMsg());
                    Logger.i(BmobIM.getInstance().getCurrentStatus().getMsg());
                }
            });
        } //end if

        //需要解决内存泄露问题？
    }

    @Override
    protected void initView() {
        super.initView();
        initTab();
    }

    private void initTab() {
        mTabs = new TextView[4];
        mTabs[0] = btn_home;
        mTabs[1] = btn_conversation;
        mTabs[2] = btn_share;
        mTabs[3] = btn_settings;

        conversationFragment = new ConversationFragment();
        homePageFragment = new HomePageFragment();
        shareFragment = new ShareFragment();
        mySettingsFragment = new MySettingsFragment();

        onTabSelect(btn_home);

    }
    /*TODO 选择fragment
    * param view
    * */
    public void onTabSelect(View view) {
        switch (view.getId()) {
            case R.id.btn_home:
                index = 0;
                switchFragment(mCuurentFragment, homePageFragment);
                break;
            case R.id.btn_conversation:
                index = 1;
                switchFragment(mCuurentFragment, conversationFragment);
                break;
            case R.id.btn_share:
                index = 2;
                switchFragment(mCuurentFragment, shareFragment);
                break;
            case R.id.btn_settings:
                index = 3;
                switchFragment(mCuurentFragment, mySettingsFragment);
                break;
        }
        onTabIndex(index);
    }

    /*TODO 设置当前选中的tab
    * param index
    * */
    private void onTabIndex(int index) {
        //设置属性，更换文本颜色和图片颜色
        //相关的xml在drawable目录下
        mTabs[currentTabIndex].setSelected(false);
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }

    /*TODO Tabs：Fragment切换
    * param form
    * param to
    * */
    private void switchFragment(Fragment from, Fragment to) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mCuurentFragment == null) {
            transaction.add(R.id.fragment_container, to).commit();
            mCuurentFragment = to;
            return;
        }
        if (mCuurentFragment != to) {
            mCuurentFragment = to;
            if (!to.isAdded()){
                if (from.isAdded()) {
                    transaction.hide(from).add(R.id.fragment_container, to).commit();
                } else {
                    transaction.add(R.id.fragment_container, to).commit();
                }
            } else {
                transaction.hide(from).show(to).commit();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //每次进来该应用都检查消息会话
        checkRedPoint();
        //进入应用后，通知栏取消
        BmobNotificationManager.getInstance(this).cancelNotification();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清理导致内存泄露的资源
        BmobIM.getInstance().clear();
        UserModel.getInstance().logout();
    }
    /*TODO 消息接收：4.1 通知有在线消息接收
    * 注册消息接收事件
    * param event
    * */
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        checkRedPoint();
    }

    /*TODO 消息接收：4.2 通知有离线消息接收
    * param event
    * */
    @Subscribe
    public void onEventMainThread(OfflineMessageEvent event) {
        checkRedPoint();
    }

    /*TODO 消息接收：4.3 注册自定义消息接收事件
    * param event
    * */
    @Subscribe
    public void onEventMainThread(RefreshEvent event) {
        checkRedPoint();
    }
    /*TODO 消息接收：4.4 检查新消息接收
    * 更新会话和消息
    * 更新主页的消息（暂定不做）
    * */
    private void checkRedPoint() {
        int count = (int) BmobIM.getInstance().getAllUnReadCount();
        if (count > 0 ) {
            iv_conversation_tips.setVisibility(View.VISIBLE);
        } else {
            iv_conversation_tips.setVisibility(View.GONE);
        }
    }

}
