package com.example.jiamoufang.tutorialapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.adapter.SubjectAdapter;
import com.example.jiamoufang.tutorialapp.adapter.TeacherAdapter;
import com.example.jiamoufang.tutorialapp.adapter.TeacherInformation;
import com.example.jiamoufang.tutorialapp.adapter.TeacherRecommendAdapter;
import com.example.jiamoufang.tutorialapp.adapter.TeacherWantedAdapter;
import com.example.jiamoufang.tutorialapp.adapter.entities.Subject;
import com.example.jiamoufang.tutorialapp.adapter.entities.TeacherWanted;
import com.example.jiamoufang.tutorialapp.ui.activities.TeacherWantedActivity;
import com.example.jiamoufang.tutorialapp.ui.base.ParentWithNaviFragment;
import com.oragee.banners.BannerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomePageFragment extends ParentWithNaviFragment {

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
    private Toolbar toolBar;

    @Bind(R.id.ll_elementary)
    LinearLayout ll_elementary;
    @Bind(R.id.ll_junior_high_school)
    LinearLayout ll_junior_high_school;
    @Bind(R.id.ll_high_school)
    LinearLayout ll_high_school;
    @Bind(R.id.ll_college)
    LinearLayout ll_college;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        ButterKnife.bind(this,view);
        mContext = view.getContext();
        /*设置菜单使能*/
        setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        toolbar.setTitle("");
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.location);
        }
        /*设置toolbar标题为空*/
        CollapsingToolbarLayout collapsing_tl_home_page = (CollapsingToolbarLayout)view.findViewById(R.id.collapsing_tl_home_page);
        collapsing_tl_home_page.setTitle("");
        collapsing_tl_home_page.setScrimAnimationDuration(1000);
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
        SubjectAdapter subjectAdapter = new SubjectAdapter(mSubjectList);
        recyclerView.setAdapter(subjectAdapter);

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.toolbar,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /*
    * TODO : handles with click events
    * @fangjiamou
    * */

    @OnClick({R.id.ll_elementary, R.id.ll_junior_high_school, R.id.ll_high_school, R.id.ll_college})
    public void handlerForClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.ll_elementary:
                bundle.putInt("level",1);
                break;
            case R.id.ll_junior_high_school:
                bundle.putInt("level",2);
                break;
            case R.id.ll_high_school:
                bundle.putInt("level",3);
                break;
            case R.id.ll_college:
                bundle.putInt("level",4);
                break;
            default:
                break;
        }
        startActivity(TeacherWantedActivity.class, bundle);
    }
    /*
     * TODO：handles with toolbar click events
     * @fangjiamou
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tb_search:
                Toast.makeText(mContext, "搜索功能未开放", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tb_news:
                Toast.makeText(mContext, "新消息通知未开放", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                toast("定位功能未开放");
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    protected String title() {
        return null;
    }
}
