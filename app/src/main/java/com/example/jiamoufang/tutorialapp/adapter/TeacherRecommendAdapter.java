package com.example.jiamoufang.tutorialapp.adapter;

/**
 * Created by a0924 on 2017/12/30.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.model.UserModel;
import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.example.jiamoufang.tutorialapp.model.intface.QueryUserListener;
import com.example.jiamoufang.tutorialapp.ui.activities.TeacherDetailsActivity;
import com.example.jiamoufang.tutorialapp.ui.fragment.HomePageFragment;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class TeacherRecommendAdapter extends RecyclerView.Adapter<TeacherRecommendAdapter.ViewHolder>{

    private List<TeacherInformation> mList;

    public TeacherRecommendAdapter(List<TeacherInformation> mList) {

        this.mList = mList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView img;
        private TextView tv_subjects,teacherInfo,teacherSalary,teachingAge;

        public ViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.recommend_teacher_pic);
            tv_subjects = itemView.findViewById(R.id.tv_subjects);
            teacherInfo = itemView.findViewById(R.id.tv_recommend_teacherName);
            teacherSalary = itemView.findViewById(R.id.tv_recommend_teacher_price);
            teachingAge = itemView.findViewById(R.id.tv_recommend_teaching_age);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(HomePageFragment.mContext)
                .inflate(R.layout.teacher_info_recommand_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = viewHolder.getAdapterPosition();
                if (index >= 0) {
                    Toast.makeText(HomePageFragment.mContext, "hehe", Toast.LENGTH_SHORT).show();
                    TeacherInformation teacherInformation = mList.get(index);
                    fundTeacherInfo(teacherInformation);
                }
            }
        });
        return viewHolder;
    }

    private void startActivity(User user) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(HomePageFragment.mContext, TeacherDetailsActivity.class);
        bundle.putSerializable("teacher",user );
        intent.putExtras(bundle);
        HomePageFragment.mContext.startActivity(intent);
    }

    private void fundTeacherInfo(TeacherInformation teacherInformation) {
        String username = teacherInformation.getUsername();
        UserModel.getInstance().queryUser(username, new QueryUserListener() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    startActivity(user);
                } else {
                    e.printStackTrace();
                    Toast.makeText(HomePageFragment.mContext, "wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(TeacherRecommendAdapter.ViewHolder holder, int position) {
        TeacherInformation teacher = mList.get(position);
        holder.tv_subjects.setText(teacher.getSubject());
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
