package com.example.jiamoufang.tutorialapp.adapter.listener;

/**
 * Created by jiamoufang on 2017/12/21.
 * 为RecyclerView添加点击事件
 * 定义接口，在需要的地方再实例化、具体化
 */

public interface OnRecyclerViewListener {
    void onItemClick(int position);
    boolean onItemLongClick(int position);
}
