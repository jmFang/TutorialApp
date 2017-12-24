package com.example.jiamoufang.tutorialapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.ui.base.ParentWithNaviActivity;
import com.example.jiamoufang.tutorialapp.ui.base.ParentWithNaviFragment;

import butterknife.Bind;

public class ConversationFragment extends ParentWithNaviFragment {

    //消息item的容器：recyclerView
    @Bind(R.id.rc_view)
    RecyclerView rc_view;
    //recyclerView外面的可下拉刷新的SwipeRefreshLayout
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;
    //消息item的适配器

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
        return super.right();
    }

    @Override
    public ParentWithNaviActivity.ToolBarListener setToolBarListener() {
        return super.setToolBarListener();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversation, container, false);
        return view;
    }
}
