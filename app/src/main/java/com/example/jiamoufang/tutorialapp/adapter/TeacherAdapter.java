package com.example.jiamoufang.tutorialapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.ui.fragment.HomePageFragment;

import java.util.List;

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

        View view = LayoutInflater.from(HomePageFragment.mContext).inflate(R.layout.teacher_info_item, parent, false);

        return new TeacherAdapter.ViewHolder(view);
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
