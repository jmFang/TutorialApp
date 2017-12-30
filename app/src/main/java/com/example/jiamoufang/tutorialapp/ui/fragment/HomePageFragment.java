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
import android.widget.ImageView;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.adapter.SubjectAdapter;
import com.example.jiamoufang.tutorialapp.adapter.TeacherAdapter;
import com.example.jiamoufang.tutorialapp.adapter.TeacherInformation;
import com.example.jiamoufang.tutorialapp.adapter.TeacherRecommendAdapter;
import com.oragee.banners.BannerView;

import java.util.ArrayList;
import java.util.List;

public class HomePageFragment extends Fragment {

    //这是科目RecyclerView里的view所要呈现的资料
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
        RecyclerView recyclerView = view.findViewById(R.id.subject_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new SubjectAdapter(subjectMsg));

        //调用TeacherInformation类的静态方法实例化20个已写入数据的对象
        // (方便UI呈现以及测试,在实际逻辑开发时需要更改)
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

    //轮番图的适配器(逻辑开发无需管理此内部类,此为纯粹UI需要表达之逻辑呈现)
/*    private class CustomPagerAdapter extends PagerAdapter {

        private LayoutInflater mLayoutInflater;

        @Override //轮番图有几页
        public int getCount() {
            return tutorResID.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            mLayoutInflater = LayoutInflater.from(mContext);
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

            Log.d("MainActivity", "in pagerAdapter");

            ImageView img = itemView.findViewById(R.id.ImageView);
            img.setImageResource(tutorResID[position]);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }*/
}
