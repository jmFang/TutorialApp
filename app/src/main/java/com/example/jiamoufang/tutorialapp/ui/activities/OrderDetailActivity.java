package com.example.jiamoufang.tutorialapp.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.model.bean.Order;
import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.example.jiamoufang.tutorialapp.ui.base.ParentWithNaviActivity;

public class OrderDetailActivity extends ParentWithNaviActivity {

    private TextView teacher_name_text, teacher_gender_text, teacher_address_text,
            order_home_or_out_text, order_number_text, order_subject_text,
            order_teaching_age_text, order_salary_text, order_teaching_period_text,
            order_student_state_text;

    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        initNaviView();
        setUp();
    }

    @Override
    protected String title() {
        return null;
    }

    private void setUp() {
        findViews();
        order = (Order) getIntent().getExtras().getSerializable("order");
        initializeViews();
    }

    private void findViews() {
        teacher_name_text = (TextView) findViewById(R.id.teacher_name_text);
        teacher_gender_text = (TextView) findViewById(R.id.teacher_gender_text);
        teacher_address_text = (TextView) findViewById(R.id.teacher_address_text);
        order_home_or_out_text = (TextView) findViewById(R.id.order_home_or_out_text);
        order_number_text = (TextView) findViewById(R.id.order_number_text);
        order_subject_text = (TextView) findViewById(R.id.order_subject_text);
        order_teaching_age_text = (TextView) findViewById(R.id.order_teaching_age_text);
        order_salary_text = (TextView) findViewById(R.id.order_salary_text);
        order_teaching_period_text = (TextView) findViewById(R.id.order_teaching_period_text);
        order_student_state_text = (TextView) findViewById(R.id.order_student_state_text);
    }

    private void initializeViews() {
        User teacher = order.getTeacher();
        if (teacher != null) {
            teacher_name_text.setText(teacher.getRealName());
            if (teacher.getSex())
                teacher_gender_text.setText("男");
            else
                teacher_gender_text.setText("女");
            teacher_address_text.setText(teacher.getAddress());
            order_home_or_out_text.setText("已接单");
        } else {
            order_home_or_out_text.setText("无人接单");
        }
        User student = order.getUser();
        order_number_text.setText(student.getRealName());
        order_subject_text.setText(order.getSubject());
        order_teaching_age_text.setText(grade(order.getGrade()));
        order_salary_text.setText(order.getSalary());
        order_teaching_period_text.setText(order.getTime());
        order_student_state_text.setText(order.getDes());
    }

    private String grade(Integer grade) {
        switch (grade) {
            case 1:
                return "小学一年级";
            case 2:
                return "小学二年级";
            case 3:
                return "小学三年级";
            case 4:
                return "小学四年级";
            case 5:
                return "小学五年级";
            case 6:
                return "小学六年级";
            case 7:
                return "初一";
            case 8:
                return "初二";
            case 9:
                return "初三";
            case 10:
                return "高一";
            case 11:
                return "高二";
            case 12:
                return "高三";
            case 13:
                return "大学";
            default:
                return "其他";
        }
    }

}
