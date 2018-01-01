package com.example.jiamoufang.tutorialapp.adapter;

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
import com.example.jiamoufang.tutorialapp.share.model.BmobModel;
import com.example.jiamoufang.tutorialapp.ui.activities.MyTeachersActivity;
import com.example.jiamoufang.tutorialapp.ui.activities.TeacherDetailsActivity;
import com.example.jiamoufang.tutorialapp.ui.fragment.HomePageFragment;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by a0924 on 2017/12/30.
 */

public//优选老师的适配器
class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.ViewHolder> {

    private List<TeacherInformation> mList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView info;
        private TextView salary;

        public ViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.good_teacher_pic);
            info = itemView.findViewById(R.id.good_teacher_info);
            salary = itemView.findViewById(R.id.good_teacher_salary);

        }
    }

    public TeacherAdapter(List<TeacherInformation> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(HomePageFragment.mContext).inflate(R.layout.teacher_info_item, parent, false);
        final TeacherAdapter.ViewHolder viewHolder =  new TeacherAdapter.ViewHolder(view);
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

    private void startActivity(User user) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(HomePageFragment.mContext, TeacherDetailsActivity.class);
        bundle.putSerializable("teacher",user );
        intent.putExtras(bundle);
        HomePageFragment.mContext.startActivity(intent);
    }


    @Override
    public void onBindViewHolder(TeacherAdapter.ViewHolder holder, int position) {
        TeacherInformation teacher = mList.get(position);
        holder.img.setImageResource(teacher.getPictureID());
        holder.info.setText(teacher.getInfo());
        holder.salary.setText("$" + teacher.getPrice() + "起");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
