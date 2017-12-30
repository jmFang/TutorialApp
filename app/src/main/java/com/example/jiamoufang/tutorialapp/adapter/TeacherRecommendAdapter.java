package com.example.jiamoufang.tutorialapp.adapter;

/**
 * Created by a0924 on 2017/12/30.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.ui.fragment.HomePageFragment;

import java.util.List;

public class TeacherRecommendAdapter extends RecyclerView.Adapter<TeacherRecommendAdapter.ViewHolder>{

    private List<TeacherInformation> mList;

    public TeacherRecommendAdapter(List<TeacherInformation> mList) {

        this.mList = mList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView img;
        private TextView teacherInfo,teacherSalary,teachingAge;

        public ViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.recommend_teacher_pic);
            teacherInfo = itemView.findViewById(R.id.recommend_teacher_info);
            teacherSalary = itemView.findViewById(R.id.recommend_teacher_salary);
            teachingAge = itemView.findViewById(R.id.recommend_teaching_age);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(HomePageFragment.mContext)
                .inflate(R.layout.teacher_info_recommand_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TeacherRecommendAdapter.ViewHolder holder, int position) {
        TeacherInformation teacher = mList.get(position);
        holder.teacherSalary.setText("$" + teacher.getPrice() + "起");
        holder.img.setImageResource(teacher.getPictureID());
        holder.teacherInfo.setText(teacher.getInfo() + teacher.getName());
        holder.teachingAge.setText("教龄" + teacher.getTeachingAge());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
