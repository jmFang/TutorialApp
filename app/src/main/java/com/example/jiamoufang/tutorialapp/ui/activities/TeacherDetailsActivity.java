package com.example.jiamoufang.tutorialapp.ui.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.jiamoufang.tutorialapp.R;

public class TeacherDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_details);

        //将图片做成Bitmap,并将其制作切割,再设置成订单图片
        //(此处代码为UI测试所需用到的逻辑,在逻辑开发时可以注解之>)
        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.mipmap.default_ss);
        ImageView img = (ImageView) findViewById(R.id.details_teacher_photo);
        img.setImageDrawable(OrderActivity.getRoundedShape(icon, getResources()));
    }
}
