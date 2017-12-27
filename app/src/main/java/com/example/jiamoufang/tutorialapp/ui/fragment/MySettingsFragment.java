package com.example.jiamoufang.tutorialapp.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.ui.activities.OrderActivity;

public class MySettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_settings, container, false);

        //将图片做成Bitmap,并将其制作切割,再设置成订单图片
        //(此处代码为UI测试所需用到的逻辑,在逻辑开发时可以注解之>)
        Bitmap icon = BitmapFactory.decodeResource(getResources(),
        R.mipmap.default_ss);
        ImageView img = view.findViewById(R.id.my_photo);
        img.setImageDrawable(OrderActivity.getRoundedShape(icon, getResources()));

        return view;
    }
}
