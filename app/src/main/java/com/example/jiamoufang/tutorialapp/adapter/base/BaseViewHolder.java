package com.example.jiamoufang.tutorialapp.adapter.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jiamoufang.tutorialapp.adapter.listener.OnRecyclerViewListener;
import com.example.jiamoufang.tutorialapp.ui.base.BaseActivity;

import butterknife.ButterKnife;

/**
 * Created by jiamoufang on 2017/12/21.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public OnRecyclerViewListener onRecyclerViewListener;
    protected Context mContext;

   public BaseViewHolder(Context context, ViewGroup root, int layoutRes, OnRecyclerViewListener listener) {
       super(LayoutInflater.from(context).inflate(layoutRes, root, false));
       this.mContext = context;
       ButterKnife.bind(this, itemView);
       this.onRecyclerViewListener = listener;
       itemView.setOnClickListener(this);
       itemView.setOnLongClickListener(this);
   }
   /*
   * 获取itemView所在的上下文
   * */
   public Context getContext() {
       return itemView.getContext();
   }
   /*
   * 绑定数据
   * 子类必须实现
   * */
   public abstract void bindData(T t);

    /*
    * 实例化一个Toast，封装了Context，方便随处调用
    * 特别是能够放在主线程处理
    * */
    private Toast toast;
    public void toast(final Object object) {
        try {
            /*
            * 神奇！将mContext向上转换为BaseActivity
            * mContext 本质上也是来自某个Activity，其继承了BaseActivity
            * 因此可以转换，得到调用runOnUiThread，在主线程处理
            * */
            ((BaseActivity)mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (toast == null) {
                        toast = Toast.makeText(mContext,"", Toast.LENGTH_SHORT);
                        toast.setText(object.toString());
                        toast.show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        if (onRecyclerViewListener != null) {
            //注意：getAdapterPosition（）与其他方法的区别？
            onRecyclerViewListener.onItemClick(getAdapterPosition());
        }
        return;
    }

    @Override
    public boolean onLongClick(View v) {
        if (onRecyclerViewListener != null) {
            onRecyclerViewListener.onItemLongClick(getAdapterPosition());
        }
        return true;
    }
    /*
    * 启动指定的Activity
    * @param target
    * @param bundle
    * */
    public void startActivity(Class<? extends Activity> target, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getContext(), target);
        if (bundle != null) {
            intent.putExtra(getContext().getPackageName(), bundle);
        }
        getContext().startActivity(intent);
    }
}
