package com.example.jiamoufang.tutorialapp.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jiamoufang.tutorialapp.adapter.listener.OnRecyclerViewListener;
import com.example.jiamoufang.tutorialapp.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jiamoufang on 2017/12/21.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerHolder> {
    /**
     * 默认布局
     */
    private final int TYPE_DEFAULT = 0;
    /**
     * 当list没有值的时候显示的布局
     */
    private final int TYPE_HEADER = 1;
    /**
     * 多重布局
     */
    private final int TYPE_MUTIPLE = 2;
    /**
     * 带header的多重布局
     */
    private final int TYPE_MUTIPLE_HEADER = 3;

    protected final Context context;
    protected List<T> lists;
    protected IMultipleItem<T> items;
    protected OnRecyclerViewListener listener;


    /**
     * 支持一种或多种Item布局
     *
     * @param context
     * @param items
     * @param datas
     */
    public BaseRecyclerAdapter(Context context, IMultipleItem<T> items, Collection<T> datas) {
        this.context = context;
        this.items = items;
        this.lists = datas == null ? new ArrayList<T>() : new ArrayList<T>(datas);
    }

    /**
     * 绑定数据
     * @param datas
     * @return
     */
    public BaseRecyclerAdapter<T> bindDatas(Collection<T> datas) {
        this.lists = datas == null ? new ArrayList<T>() : new ArrayList<T>(datas);
        notifyDataSetChanged();
        return this;
    }

    /**
     * 删除数据
     * @param position
     */
    public void remove(int position) {
        int more = getItemCount() - lists.size();
        lists.remove(position - more);
        notifyDataSetChanged();
    }

    /**
     * 获取指定position的Item
     * @param position
     * @return
     */
    public T getItem(int position) {
        int more = getItemCount() - lists.size();
        return lists.get(position - more);
    }

    /**
     * 获取总数
     * @return
     */
    public int getCount() {
        return this.lists == null?0:this.lists.size();
    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = items.getItemLayoutId(viewType);
        LayoutInflater inflater = LayoutInflater.from(context);
        View root = inflater.inflate(layoutId, parent, false);
        return new BaseRecyclerHolder(layoutId, root);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, int position) {
        int type = getViewTypeByPosition(position);
        if(type==TYPE_HEADER){
            bindView(holder, null, position);
        }else if(type==TYPE_MUTIPLE){
            bindView(holder, lists.get(position), position);
        }else if(type==TYPE_MUTIPLE_HEADER){
            int headerCount = getItemCount() - lists.size();
            bindView(holder, lists.get(position - headerCount), position);
        }else{
            bindView(holder, null, position);
        }
        holder.itemView.setOnClickListener(getOnClickListener(position));
        holder.itemView.setOnLongClickListener(getOnLongClickListener(position));
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.getItemCount(lists);
        }
        return lists.size();
    }

    /*
        * 指定position的布局类型
        * @param position
        * */
    private int getViewTypeByPosition(int position) {
        if (items == null) {
            return TYPE_DEFAULT; //默认布局
        } else { //多布局
            if (lists != null && lists.size() > 0) { //lists 有值的时候
                if (getItemCount() > lists.size()) { //是否有自定义的header
                    int headerCount = getItemCount() - lists.size();
                    if (position >= headerCount) { //当前位置大于header个数
                        return TYPE_MUTIPLE_HEADER;
                    } else {
                        return TYPE_HEADER; //当前点击的是header
                    }
                } else {
                    return TYPE_MUTIPLE;
                }
            } else {//lists还没有值的时候
                return TYPE_HEADER;
            }
        }
    }
    /*
    * 设置点击、长按等事件监听器
    * */
    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.listener = onRecyclerViewListener;
    }

    public View.OnClickListener getOnClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null && v != null) {
                    listener.onItemClick(position);
                }
            }
        };
    }

    public View.OnLongClickListener getOnLongClickListener(final int position) {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null && v != null) {
                    listener.onItemLongClick(position);
                }
                return true;
            }
        };
    }

    /*
    * 子类必须实现此方法
    * @param holder
    * @param item
    * */
    public abstract void bindView(BaseRecyclerHolder holder, T item, int position);

    /*
    * 实例化一个Toast,方便子类调用
    * */
    private Toast toast;
    public void toast(final Object object) {
        try {
            ((BaseActivity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (toast == null) {
                        toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
                        toast.setText(object.toString());
                        toast.show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
