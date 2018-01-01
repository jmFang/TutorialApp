package com.example.jiamoufang.tutorialapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.ColorLong;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.adapter.ConversationAdapter;
import com.example.jiamoufang.tutorialapp.adapter.base.IMultipleItem;
import com.example.jiamoufang.tutorialapp.adapter.listener.OnRecyclerViewListener;
import com.example.jiamoufang.tutorialapp.event.RefreshEvent;
import com.example.jiamoufang.tutorialapp.model.bean.Conversation;
import com.example.jiamoufang.tutorialapp.model.bean.PrivateConversation;
import com.example.jiamoufang.tutorialapp.ui.activities.SearchUserActivity;
import com.example.jiamoufang.tutorialapp.ui.base.ParentWithNaviActivity;
import com.example.jiamoufang.tutorialapp.ui.base.ParentWithNaviFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;

/*
* 会话界面
* */
public class ConversationFragment extends ParentWithNaviFragment {

    //消息item的容器：recyclerView
    @Bind(R.id.rc_view)
    RecyclerView rc_view;
    //recyclerView外面的可下拉刷新的SwipeRefreshLayout
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;
    //消息item的适配器
    ConversationAdapter adapter;
    LinearLayoutManager layoutManager;

    /*
    * title是抽象方法，必须继承实现
    * 返回当前的标题
    * */
    @Override
    protected String title() {
        return null;
    }
    /*
    * 只需要添加right控件，right控件的点击事件是
    * 用于进行会话的联系人
    * */
    @Override
    public Object right() {
        return R.drawable.base_action_bar_add_bg_selector;
    }

    /*
    * 导航栏的点击事件
    * 右边控件启动搜索Activity
    * */
    @Override
    public ParentWithNaviActivity.ToolBarListener setToolBarListener() {
        return new ParentWithNaviActivity.ToolBarListener() {
            @Override
            public void clickLeft() {

            }

            @Override
            public void clickRight() {
                startActivity(SearchUserActivity.class, null);
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //rootView 来自 ParentWithNaviFragment，实例化ParentWithNaviFragment的rootView
        //使得与rootView有关的操作激活
        rootView = inflater.inflate(R.layout.fragment_conversation, container,false);     //加载conversation的布局，生成实例
        initNaviView();
        /*注意：与activity的绑定不同
        * fragment是绑定当前对象到其实例化的布局
        * */
        ButterKnife.bind(this,rootView);
        /**
        * 单一布局
        * */
        IMultipleItem<Conversation> multipleItem = new IMultipleItem<Conversation>() {
            @Override
            public int getItemLayoutId(int viewtype) {
                return R.layout.item_conversation;
            }

            @Override
            public int getItemViewType(int postion, Conversation conversation) {
                return 0;
            }

            @Override
            public int getItemCount(List<Conversation> list) {
                return list.size();
            }
        };

        adapter = new ConversationAdapter(getActivity(), multipleItem, null);
        rc_view.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        rc_view.setLayoutManager(layoutManager);
        sw_refresh.setEnabled(true);
        setListener();
        return rootView;
    }

    private void setListener() {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //removeOnGlobalLayoutListener是新版本？
                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                sw_refresh.setRefreshing(true);
                query();
            }
        });
        sw_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                query();
            }
        });
        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            /*
            * 会话单击事件,需要传入parent的context才能启动另一个activity
            * 具体动作在PrivateConversation类，点击一个PrivateConversation会话，启动聊天界面
            * @param context
            * */
            @Override
            public void onItemClick(int position) {
                adapter.getItem(position).onClick(getActivity());
            }
            /*
            * 会话长按事件,其实传入context是非必要的
            * @param context
            * */
            @Override
            public boolean onItemLongClick(int position) {
                adapter.getItem(position).onLongClick(getActivity());
                adapter.remove(position);
                return true;
            }
        });
    }
    /*
    * 查询本地会话
    * */
    private void query() {
        adapter.bindDatas(getConversations());
        adapter.notifyDataSetChanged();
        sw_refresh.setRefreshing(false);
    }

    public List<Conversation> getConversations() {
        List<Conversation> conversationList = new ArrayList<>();
        //先清空，再加载（虽然这样明显降低了效率和性能）
        conversationList.clear();
        //加载所有本地会话
        List<BmobIMConversation> list = BmobIM.getInstance().loadAllConversation();
        if (list != null && list.size() > 0) {
            for (BmobIMConversation item : list) {
                switch (item.getConversationType()) {
                    case 1://私聊
                        conversationList.add(new PrivateConversation(item));
                        break;
                    default:
                        break;

                }
            }
        }
        //本项目不涉及添加好友
        //重新排序
        Collections.sort(conversationList);
        return conversationList;
    }

    @Override
    public void onResume() {
        super.onResume();
        sw_refresh.setRefreshing(true);
        query();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    /*
    * 注册自定义消息接收事件
    * */
    @Subscribe
    public void onEventMainThread(RefreshEvent event) {
        //重新刷新列表
        adapter.bindDatas(getConversations());
        adapter.notifyDataSetChanged();
    }

    /*
    * 注册离线消息接收
    * */
    @Subscribe
    public void onEventMainThread(OfflineMessageEvent event) {
        adapter.bindDatas(getConversations());
        adapter.notifyDataSetChanged();
    }

    /*
    * 注册消息接收事件
    *
    * */
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        //重新获取本地消息并刷新列表
        adapter.bindDatas(getConversations());
        adapter.notifyDataSetChanged();
    }

}
