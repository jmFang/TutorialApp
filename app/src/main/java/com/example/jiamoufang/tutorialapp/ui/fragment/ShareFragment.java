package com.example.jiamoufang.tutorialapp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.adapter.PostAdapter;
import com.example.jiamoufang.tutorialapp.event.RefreshPostEvent;
import com.example.jiamoufang.tutorialapp.share.bean.Post;
import com.example.jiamoufang.tutorialapp.share.presenter.ShowPostPresenter;
import com.example.jiamoufang.tutorialapp.share.view.ShowPostView;
import com.example.jiamoufang.tutorialapp.ui.activities.PublishPostActivity;
import com.example.jiamoufang.tutorialapp.ui.base.ParentWithNaviActivity;
import com.example.jiamoufang.tutorialapp.ui.base.ParentWithNaviFragment;
import com.example.jiamoufang.tutorialapp.widget.SwipeRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* 学圈
* */
public class ShareFragment extends ParentWithNaviFragment implements ShowPostView {



    @Bind(R.id.v_top)
    View mVTop;
    @Bind(R.id.tv_left)
    ImageView mTvLeft;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tv_right)
    TextView mTvRight;
    //自定义控件，从widget包获取
    @Bind(R.id.swipe_recycle_post)
    SwipeRecyclerView mSwipeRecyclePost;

    @Bind(R.id.tv_error)
    TextView mTvError;
    @Bind(R.id.fb_add_post)
    FloatingActionButton mFbAddPost;


    private List<Post> mPosts;
    private PostAdapter mPostAdapter;
    private ShowPostPresenter mShowPostPresenter;
    private int page = 1;
    private final int COUNT = 12;
    private final int PAGE = 1;

    /*
    * 获取一个ShareFragment实例
    * */
    public static ShareFragment newInstance() {
        ShareFragment fragment = new ShareFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /*
    * 默认构造方法
    * */
    public ShareFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_share, container, false);
        //绑定导航条
        initNaviView();
        ButterKnife.bind(this, rootView);
        mPosts = new ArrayList<>();
        mShowPostPresenter = new ShowPostPresenter(this);
        mShowPostPresenter.showPosts(PAGE, COUNT);
        //自定义控件
        mSwipeRecyclePost.getRecyclerView().setHasFixedSize(true);
        mSwipeRecyclePost.getRecyclerView().setLayoutManager(new LinearLayoutManager(getContext()));
        mSwipeRecyclePost.setOnLoadListener(new SwipeRecyclerView.OnLoadListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
        return rootView;
    }

    /**
     * 加载更多
     */
    private void loadMore() {
        page = page + 1;
        mSwipeRecyclePost.setRefreshEnable(false);
        mShowPostPresenter.showPosts(page, COUNT);
    }

    /**
     * 刷新界面
     */
    private void refresh() {
        page = PAGE;
        mSwipeRecyclePost.setLoadMoreEnable(false);
        mShowPostPresenter.showPosts(page, COUNT);
    }

    @Override
    protected String title() {
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_error, R.id.fb_add_post})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_error:
                break;
            case R.id.fb_add_post:
                startActivity(new Intent(getActivity(), PublishPostActivity.class));  //启动PublishPostActivity,填写post
                break;
        }
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void hideDialog() {
    }

    @Override
    public void showError(Throwable throwable) {
        //TODO 弹出toast的时候 fragment已经死掉
        Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPosts(List<Post> posts) {
        //TODO 显示数据
        mSwipeRecyclePost.setLoadMoreEnable(true);
        mSwipeRecyclePost.setRefreshEnable(true);
        if (page == PAGE) {
            mPosts.clear();
            mPosts.addAll(posts);
            mSwipeRecyclePost.setRefreshing(false);
            if (mPosts.size() < 1) {
                mSwipeRecyclePost.setEmptyView(mTvError);
            } else {
                if (mPosts.size() < COUNT) {
                    mSwipeRecyclePost.complete();
                    mSwipeRecyclePost.setLoadMoreEnable(false);
                }
                if (mPostAdapter == null) {
                    mPostAdapter = new PostAdapter(getContext(), mPosts);
                    mSwipeRecyclePost.setAdapter(mPostAdapter);
                } else {
                    mPostAdapter.notifyDataSetChanged();
                }
            }
        } else {
            if (posts.size() < COUNT) {
                mSwipeRecyclePost.complete();
                mSwipeRecyclePost.setLoadMoreEnable(false);
            }
            mSwipeRecyclePost.stopLoadingMore();
            mPosts.addAll(posts);
            mPostAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshPostEvent(RefreshPostEvent event) {
        refresh();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
