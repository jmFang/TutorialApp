package com.example.jiamoufang.tutorialapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.ui.fragment.HomePageFragment;

/**
 * Created by a0924 on 2017/12/30.
 */

public//科目的适配器
class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    private String[] subjectMsg;

    public SubjectAdapter(String[] subjectMsg) {
        this.subjectMsg = subjectMsg;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView subjectName;

        public ViewHolder(View itemView) {
            super(itemView);

            subjectName = itemView.findViewById(R.id.textView);

            subjectName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(HomePageFragment.mContext, SubjectAdapter.ViewHolder.this.subjectName.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(HomePageFragment.mContext).inflate(R.layout.subject_item, parent, false);
        SubjectAdapter.ViewHolder viewHolder = new SubjectAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SubjectAdapter.ViewHolder holder, int position) {
        holder.subjectName.setText(subjectMsg[position]);
    }

    @Override
    public int getItemCount() {
        return subjectMsg.length;
    }
}
