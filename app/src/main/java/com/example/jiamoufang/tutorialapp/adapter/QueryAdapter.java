package com.example.jiamoufang.tutorialapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.jiamoufang.tutorialapp.adapter.holder.QueryHolder;

import java.util.List;

public abstract class QueryAdapter<T> extends RecyclerView.Adapter<QueryHolder> {

    private Context mContext;
    private int mLayoutId;
    private List<T> mDatas;
    private OnItemClickListener mOnItemClickListener = null;

    public QueryAdapter(Context context, int layoutId, List<T> datas) {
        this.mContext = context;
        this.mLayoutId = layoutId;
        this.mDatas = datas;
    }

    @Override
    public QueryHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        QueryHolder viewHolder = QueryHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final QueryHolder holder, int position) {
        convert(holder, mDatas.get(position));
        if( mOnItemClickListener!= null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(holder.getAdapterPosition());
                }
            });
        }
    }

    public abstract void convert(QueryHolder holder, T t);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public T getItem(int position) {
        if (position < mDatas.size())
            return mDatas.get(position);
        else
            return null;
    }

    public void addItem(T data) {
        mDatas.add(data);
        notifyItemInserted(mDatas.size() - 1);
        //notifyDataSetChanged();
    }

    public void removeItem(int position){
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnItemClickListener{
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener = onItemClickListener;
    }

}
