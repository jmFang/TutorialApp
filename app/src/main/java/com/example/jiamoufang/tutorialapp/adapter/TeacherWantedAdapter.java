package com.example.jiamoufang.tutorialapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.adapter.entities.TeacherWanted;

import java.util.List;

/**
 * Created by jiamoufang on 2017/12/31.
 */

public class TeacherWantedAdapter extends RecyclerView.Adapter<TeacherWantedAdapter.ViewHolder> {


    private List<TeacherWanted> mTeacherWantedList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_school, tv_subject,tv_grade, tv_address, tv_orderId, tv_payment, tv_order_status,tv_visit_times;

        public ViewHolder(View view) {
            super(view);
            tv_school = view.findViewById(R.id.tv_school);
            tv_grade = view.findViewById(R.id.tv_grade);
            tv_subject = view.findViewById(R.id.tv_subject);
            tv_address = view.findViewById(R.id.tv_address);
            tv_orderId = view.findViewById(R.id.tv_orderId);
            tv_payment = view.findViewById(R.id.tv_payment);
            tv_order_status = view.findViewById(R.id.tv_order_status);
            tv_visit_times = view.findViewById(R.id.tv_visit_times);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*start details activity to make a appointment*/

                }
            });
        }
    }

    public TeacherWantedAdapter(List<TeacherWanted> mTeacherWantedList) {
        this.mTeacherWantedList = mTeacherWantedList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teacher_wanted, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TeacherWanted teacherWanted = mTeacherWantedList.get(position);
        holder.tv_school.setText(teacherWanted.getSchool());
        holder.tv_grade.setText(teacherWanted.getGrade());
        holder.tv_subject.setText(teacherWanted.getSubject());
        holder.tv_address.setText(teacherWanted.getAddress());
        holder.tv_orderId.setText(teacherWanted.getOrderId());
        holder.tv_payment.setText(teacherWanted.getPayment());
        holder.tv_order_status.setText(teacherWanted.getOrderStatus().booleanValue()==false?"未预约":"已预约");
        holder.tv_visit_times.setText(teacherWanted.getVisitTimes().toString());
    }

    @Override
    public int getItemCount() {
        return mTeacherWantedList.size();
    }

    /*
    * 添加
    * */
    public void addItem(TeacherWanted teacherWanted) {
        mTeacherWantedList.add(teacherWanted);
        notifyDataSetChanged();
    }
    /*
    * 删除,根据订单号
    * */
    public void deleteItem(String id) {
        for (int i = 0; i < mTeacherWantedList.size(); i++) {
            if (mTeacherWantedList.get(i).getOrderId().equals(id)) {
                mTeacherWantedList.remove(i);
                break;
            }
        }
        notifyDataSetChanged();
    }
}
