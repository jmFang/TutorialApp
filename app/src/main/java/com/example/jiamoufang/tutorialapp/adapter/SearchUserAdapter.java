package com.example.jiamoufang.tutorialapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.jiamoufang.tutorialapp.adapter.base.BaseViewHolder;
import com.example.jiamoufang.tutorialapp.adapter.holder.SearchUserHolder;
import com.example.jiamoufang.tutorialapp.adapter.listener.OnRecyclerViewListener;
import com.example.jiamoufang.tutorialapp.model.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiamoufang on 2017/12/22.
 */

public class SearchUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<User> users = new ArrayList<>();
    private OnRecyclerViewListener onRecyclerViewListener;

    public SearchUserAdapter(){}

    /*构造用户列表
    * @param lsit
    * */
    public void setDatas(List<User> list) {
        users.clear();
        if (list != null) {
            users.addAll(list);
        }
    }
    /*获取用户
    * @param position
    * @return
    * */
    public User getItem(int position) {
        return users.get(position);
    }
    /*设置监听器
    * @param onRecyclerViewListener
    * */
    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }
    /*
    * 创建ViewHolder
    * */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchUserHolder(parent.getContext(), parent, onRecyclerViewListener);
    }
    /*
    * 绑定数据，调用父类方法
    * */

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //向下转换，bindData已经在SearchUserHolder实现
        ((BaseViewHolder)holder).bindData(users.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
