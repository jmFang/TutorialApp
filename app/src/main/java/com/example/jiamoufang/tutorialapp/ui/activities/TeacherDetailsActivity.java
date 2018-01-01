package com.example.jiamoufang.tutorialapp.ui.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.example.jiamoufang.tutorialapp.ui.base.ParentWithNaviActivity;

public class TeacherDetailsActivity extends ParentWithNaviActivity {

    //老师姓名
    private TextView name;
    //老师性别
    private TextView sex;
    //老师电话
    private TextView phone;

    private User teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_details);
        initNaviView();
        /*
        //将图片做成Bitmap,并将其制作切割,再设置成订单图片
        //(此处代码为UI测试所需用到的逻辑,在逻辑开发时可以注解之>)
        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.mipmap.default_ss);
        ImageView img = (ImageView) findViewById(R.id.details_teacher_photo);
        img.setImageDrawable(OrderActivity.getRoundedShape(icon, getResources()));
        */

        setUp();
    }

    private void setUp() {
        findViews();
        teacher = getTeacher();
        initializeViews();
    }

    private void findViews() {
        name = (TextView) findViewById(R.id.details_teacher_info);
        sex = (TextView) findViewById(R.id.details_teacher_name);
        phone = (TextView) findViewById(R.id.details_teaching_age);
    }

    private User getTeacher() {
        return (User) getIntent().getExtras().getSerializable("teacher");
    }

    private void initializeViews() {
        name.setText(teacher.getRealName());
        if (teacher.getSex())
            sex.setText("男");
        else
            sex.setText("女");
        phone.setText("联系电话: " + teacher.getMobilePhoneNumber());
    }

    @Override
    protected String title() {
        return null;
    }

}
