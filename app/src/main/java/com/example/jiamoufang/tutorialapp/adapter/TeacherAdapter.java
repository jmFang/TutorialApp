package com.example.jiamoufang.tutorialapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.ViewHolder> {

    private List<User> mList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView good_teacher_avatar;
        private TextView good_teacher_name;
        private TextView good_teacher_diploma;

        public ViewHolder(View itemView) {
            super(itemView);

            good_teacher_avatar = itemView.findViewById(R.id.good_teacher_avatar);
            good_teacher_name = itemView.findViewById(R.id.good_teacher_name);
            good_teacher_diploma = itemView.findViewById(R.id.good_teacher_diploma);

        }
    }

    public TeacherAdapter(Context context, List<User> mList) {
        this.mList = mList;
        mContext = context;
        Log.d("TeacherAdapter",String.valueOf(mList.size()));
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
                    User teacherInformation = mList.get(index);
                    findTeacherInfo(teacherInformation);
                }
            }
        });
        return viewHolder;
    }

    private void findTeacherInfo(User user) {
        startActivity(user);
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
        User teacher = mList.get(position);
        if (teacher.getAvatar()!= null) {
            Glide.with(mContext).load(teacher.getAvatar().getUrl()).into(holder.good_teacher_avatar);
        } else {
            Glide.with(mContext).load(R.mipmap.default_smg).into(holder.good_teacher_avatar);
        }
        holder.good_teacher_name.setText((teacher.getRealName()==null)?teacher.getUsername():teacher.getRealName());
        holder.good_teacher_diploma.setText(EducatedLevelToString(teacher.getEducatedLevel()));
    }

    private String EducatedLevelToString(Integer educatedLevel) {
        if (educatedLevel == null) return "未知";
        int i = educatedLevel.intValue();
        switch (i) {
            case 0:return "小学";
            case 1:return "初中";
            case 2:return "高中";
            case 3:return "大专";
            case 4:return "本科";
            case 5:return "硕士";
            case 6:return "博士";
            default:return "未知";
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
