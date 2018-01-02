package com.example.jiamoufang.tutorialapp.ui.activities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.db.localDB.bean.bmobDb;
import com.example.jiamoufang.tutorialapp.event.RefreshEvent;
import com.example.jiamoufang.tutorialapp.model.UserModel;
import com.example.jiamoufang.tutorialapp.model.bean.Order;
import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.example.jiamoufang.tutorialapp.ui.base.BaseActivity;
import com.example.jiamoufang.tutorialapp.ui.fragment.ConversationFragment;
import com.example.jiamoufang.tutorialapp.ui.fragment.HomePageFragment;
import com.example.jiamoufang.tutorialapp.ui.fragment.MySettingsFragment;
import com.example.jiamoufang.tutorialapp.ui.fragment.ShareFragment;
import com.example.jiamoufang.tutorialapp.utils.ActivityCollector;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTouch;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
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

    /*中间按钮*/
    @Bind(R.id.btn_plus)
    ImageView btn_plus;

    /*消息tips*/
    @Bind(R.id.iv_conversation_tips)
    ImageView iv_conversation_tips;
    /*主页tips*/
    @Bind(R.id.iv_home_tips)
    ImageView iv_home_tips;
    /*底部四个tab所在的容器*/
    @Bind(R.id.main_bottom)
    LinearLayout mMainBottom;
    @Bind(R.id.fragment_container)
    RelativeLayout mFragmentContainer;

    private TextView[] mTabs;
    private ConversationFragment conversationFragment;
    private HomePageFragment homePageFragment;
    private MySettingsFragment mySettingsFragment;
    private ShareFragment shareFragment;

    private int index;
    private int currentTabIndex;
    private Fragment mCurrentFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  //设置全屏
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final User user = BmobUser.getCurrentUser(User.class);

        if (!TextUtils.isEmpty(user.getObjectId()) && BmobIM.getInstance().getCurrentStatus().getCode() != ConnectionStatus.CONNECTED.getCode()) {
            BmobIM.connect(user.getObjectId(), new ConnectListener() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        //服务器连接成功后发送一个更新事件，同步更新聊天会话和主页的小红点
                        EventBus.getDefault().post(new RefreshEvent());
                       // toast("连接服务器成功");
                        //更新用户资料，用户再会话聊天界面以及个人信息页面显示
                        BmobIM.getInstance().updateUserInfo(new BmobIMUserInfo(user.getObjectId(), user.getUsername(),null));
                    } else {
                        toast(e.getMessage());
                    }
                }
            });

            /*登录连接状态
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


        initView();
        /*
        * 启动填写家教招聘信息的活动
        * */
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OrderActivity.class));
            }
        });
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

        onTabSelect(btn_home);

    }
    /*TODO 选择fragment
    * param view
    * */
    public void onTabSelect(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        switch (view.getId()) {
            case R.id.btn_home:
                index = 0;
                showPlusButtonAnimation();
                if (homePageFragment == null) {
                    homePageFragment = new HomePageFragment();
                    transaction.add(R.id.fragment_container,homePageFragment);
                } else {
                    transaction.show(homePageFragment);
                    mCurrentFragment = homePageFragment;
                }
                break;
            case R.id.btn_conversation:
                index = 1;
                showPlusButtonAnimation();
                if (conversationFragment == null) {
                    conversationFragment = new ConversationFragment();
                    transaction.add(R.id.fragment_container, conversationFragment);
                } else {
                    transaction.show(conversationFragment);
                    mCurrentFragment = conversationFragment;
                };
                break;
            case R.id.btn_share:
                index = 2;
                showPlusButtonAnimation();
                if (shareFragment == null) {
                    shareFragment = new ShareFragment();
                    transaction.add(R.id.fragment_container, shareFragment);
                } else {
                    transaction.show(shareFragment);
                    mCurrentFragment = shareFragment;
                }
                break;
            case R.id.btn_settings:
                index = 3;
                showPlusButtonAnimation();
                if (mySettingsFragment == null) {
                    mySettingsFragment = new MySettingsFragment();
                    transaction.add(R.id.fragment_container, mySettingsFragment);
                } else {
                    transaction.show(mySettingsFragment);
                    mCurrentFragment = mySettingsFragment;
                }
                break;
            default:
                break;
        }
        onTabIndex(index);
        transaction.commit();
    }

    /*
    * tab触摸，plus缩放
    * */
    public boolean showPlusButtonAnimation() {
        final Animation plusAnimation = new ScaleAnimation(1.0F,1.3F, 1.0F, 1.3F, 1, 0.5F, 1, 0.5F);
        plusAnimation.setDuration(200L);
        btn_plus.startAnimation(plusAnimation);
        return false;
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

    private void hideFragment(FragmentTransaction transaction) {
        if(homePageFragment != null) {
            transaction.hide(homePageFragment);
        }
        if (conversationFragment != null) {
            transaction.hide(conversationFragment);
        }
        if (shareFragment != null) {
            transaction.hide(shareFragment);
        }
        if (mySettingsFragment != null) {
            transaction.hide(mySettingsFragment);
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

    /*
    * TODO 退出应用
    * 在mainActivity中按下返回键，应该是要退出应用的，因为此时mainActivity是活动栈中唯一的活动
    * 双击退出
    * */
    private long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 3000) {
                toast("再按一次退出应用");
                mExitTime = System.currentTimeMillis();
            } else {
                ActivityCollector.getInstance().exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清理导致内存泄露的资源
        BmobIM.getInstance().clear();
      //UserModel.getInstance().logout();
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
