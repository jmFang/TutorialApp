package com.example.jiamoufang.tutorialapp.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.adapter.entities.Subject;
import com.example.jiamoufang.tutorialapp.ui.activities.ChatActivity;
import com.example.jiamoufang.tutorialapp.ui.activities.QueryTeacherBySubjectActivity;
import com.example.jiamoufang.tutorialapp.ui.activities.TeacherWantedActivity;
import com.example.jiamoufang.tutorialapp.ui.base.BaseActivity;
import com.example.jiamoufang.tutorialapp.ui.fragment.HomePageFragment;

import java.util.List;

/**
 * Created by jiamoufang on 2017/12/30.
 */

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    private List<Subject> subjectList;

    public SubjectAdapter(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgSubject;
        private TextView textSubject;

        public ViewHolder(View itemView) {
            super(itemView);

            imgSubject = itemView.findViewById(R.id.item_subject_img_subject);
            textSubject = itemView.findViewById(R.id.item_subject_tv_subject);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(HomePageFragment.mContext,textSubject.getText().toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(HomePageFragment.mContext, QueryTeacherBySubjectActivity.class);
                    //传给QueryTeacherBySubjectActivity聘教信息Activity，根据学科条件过滤选择
                    intent.putExtra("subject",textSubject.getText().toString());
                    HomePageFragment.mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(HomePageFragment.mContext).inflate(R.layout.item_subject, parent, false);
        SubjectAdapter.ViewHolder viewHolder = new SubjectAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SubjectAdapter.ViewHolder holder, int position) {
        Subject subject = subjectList.get(position);
        holder.imgSubject.setImageResource(subject.getSubjectImgId());
        holder.textSubject.setText(subject.getSubjectText());
    }
    @Override
    public int getItemCount() {
        return subjectList.size();
    }
}
