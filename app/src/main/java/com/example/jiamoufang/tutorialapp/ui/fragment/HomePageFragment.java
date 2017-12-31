package com.example.jiamoufang.tutorialapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.adapter.SubjectAdapter;
import com.example.jiamoufang.tutorialapp.adapter.TeacherAdapter;
import com.example.jiamoufang.tutorialapp.adapter.TeacherInformation;
import com.example.jiamoufang.tutorialapp.adapter.TeacherRecommendAdapter;
import com.example.jiamoufang.tutorialapp.adapter.entities.Subject;
import com.oragee.banners.BannerView;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends Fragment {

    //这是九门功课横向滚动
    List<Subject> mSubjectList = new ArrayList<>();
    String[] subjectMsg = {"数学", "语文", "英语", "物理", "化学", "生物", "政治", "历史", "地理"};
    //显示轮番图的图片ID数组(可换)
    private int[] tutorResID = {
             R.drawable.lunbo_1,
            R.drawable.lunbo_2,
            R.drawable.lunbo_3};

    //取得全局上下文
    public static Context mContext;

    private List<TeacherInformation> mTeacherList;
    //用于轮播图
    private List<View> viewList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        mContext = view.getContext();

        /*这是轮番图的设置*/
        BannerView bannerView = view.findViewById(R.id.pager);
        viewList = new ArrayList<>();
        for(int i = 0; i < tutorResID.length; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(tutorResID[i]);
            viewList.add(imageView);
        }
        bannerView.startLoop(true);
        bannerView.setViewList(viewList);

        /*这是科目的RecyclerView,采用水平滑动*/
        initSubjectList();
        RecyclerView recyclerView = view.findViewById(R.id.subject_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new SubjectAdapter(mSubjectList));

        mTeacherList = TeacherInformation.initTeacherInformation();

        /*这是优选老师的RecyclerView,采用水平滑动*/
        recyclerView = view.findViewById(R.id.good_teacher);
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new TeacherAdapter(mTeacherList));

        /*这是推荐老师的RecyclerView,采用垂直滑动*/
        recyclerView = view.findViewById(R.id.recommend_teacher);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new TeacherRecommendAdapter(mTeacherList));

        return view;
    }

    private void initSubjectList() {
        mSubjectList.add(new Subject(R.mipmap.chinese_256px,"语文"));
        mSubjectList.add(new Subject(R.mipmap.math,"数学"));
        mSubjectList.add(new Subject(R.mipmap.englishpng,"英语"));
        mSubjectList.add(new Subject(R.mipmap.politics_128px,"政治"));
        mSubjectList.add(new Subject(R.mipmap.history_128px,"历史"));
        mSubjectList.add(new Subject(R.mipmap.geography_128px,"地理"));
        mSubjectList.add(new Subject(R.mipmap.physical_128px,"物理"));
        mSubjectList.add(new Subject(R.mipmap.chemistry_128px,"化学"));
        mSubjectList.add(new Subject(R.mipmap.biology_128px,"生物"));

    }
}
