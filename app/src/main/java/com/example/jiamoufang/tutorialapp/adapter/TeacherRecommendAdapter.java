package com.example.jiamoufang.tutorialapp.adapter;

/**
 * Created by a0924 on 2017/12/30.
 */

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
import com.example.jiamoufang.tutorialapp.db.localDB.bean.bmobDb;
import com.example.jiamoufang.tutorialapp.model.UserModel;
import com.example.jiamoufang.tutorialapp.model.bean.*;
import com.example.jiamoufang.tutorialapp.model.bean.TeacherInformation;
import com.example.jiamoufang.tutorialapp.model.intface.QueryUserListener;
import com.example.jiamoufang.tutorialapp.ui.activities.TeacherDetailsActivity;
import com.example.jiamoufang.tutorialapp.ui.fragment.HomePageFragment;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import rx.functions.Action1;

public class TeacherRecommendAdapter extends RecyclerView.Adapter<TeacherRecommendAdapter.ViewHolder>{

    private List<User> mList;
    private Context mContext;

    public TeacherRecommendAdapter(Context context, List<User> mList) {
        mContext = context;
        this.mList = mList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        private ImageView img_recommend_avatar;
        private TextView tv_recommend_diploma,tv_teacher_name,tv_teaching_saying;

        public ViewHolder(View itemView) {
            super(itemView);

            img_recommend_avatar = itemView.findViewById(R.id.recommend_teacher_pic);
            tv_recommend_diploma = itemView.findViewById(R.id.tv_recommend_diploma);
            tv_teacher_name = itemView.findViewById(R.id.tv_recommend_teacherName);
            tv_teaching_saying = itemView.findViewById(R.id.tv_recommend_teaching_saying);

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
                    //Toast.makeText(HomePageFragment.mContext, "hehe", Toast.LENGTH_SHORT).show();
                    User teacherInformation = mList.get(index);
                    findTeacherInfo(teacherInformation);
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

    private void findTeacherInfo(User teacherInformation) {
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

    private String educatedLevelToString(Integer educatedLevel) {
        switch (educatedLevel) {
            case 0:return "小学";
            case 1:return "初中";
            case 2:return "高中";
            case 3:return "大专";
            case 4:return "本科";
            case 5:return "硕士";
            case 6:return "博士";
            default:return "未设置";
        }
    }
    @Override
    public void onBindViewHolder(TeacherRecommendAdapter.ViewHolder holder, int position) {
        User teacher = mList.get(position);
        holder.tv_recommend_diploma.setText(educatedLevelToString(teacher.getEducatedLevel()));
        if (teacher.getAvatar() != null) {
            Glide.with(mContext).load(teacher.getAvatar().getUrl()).into(holder.img_recommend_avatar);
        } else {
            Glide.with(mContext).load(R.mipmap.default_smg).into(holder.img_recommend_avatar);
        }
        holder.tv_teacher_name.setText(teacher.getRealName()==null? teacher.getUsername():teacher.getRealName());
        holder.tv_teaching_saying.setText(findSayingOfTeacherByUsername(teacher.getUsername()));

    }

    private String output="";

    private String findSayingOfTeacherByUsername(String username) {
        bmobDb.getInstance().findSayingOfTeacherByUsername(username).subscribe(new Action1<List<com.example.jiamoufang.tutorialapp.model.bean.TeacherInformation>>() {
            @Override
            public void call(List<TeacherInformation> teacherInformations) {
                if (teacherInformations.size() != 1) {
                    Log.d("TeacherRecommendAdapter", "查询结果不唯一或为空");
                } else {
                    output = teacherInformations.get(0).getTeachingSaying();
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.d("TeacherRecommendAdapter","查询回调出错");
            }
        });
        return output;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
