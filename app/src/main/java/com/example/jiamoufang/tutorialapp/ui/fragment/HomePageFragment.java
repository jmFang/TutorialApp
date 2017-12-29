package com.example.jiamoufang.tutorialapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiamoufang.tutorialapp.R;
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

//科目的适配器
class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    private String[] subjectMsg;

    public SubjectAdapter(String[] subjectMsg) {
        this.subjectMsg = subjectMsg;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView subjectName;

        public ViewHolder(View itemView) {
            super(itemView);

            subjectName = itemView.findViewById(R.id.textView);

            subjectName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(HomePageFragment.mContext, ViewHolder.this.subjectName.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(HomePageFragment.mContext).inflate(R.layout.subject_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.subjectName.setText(subjectMsg[position]);
    }

    @Override
    public int getItemCount() {
        return subjectMsg.length;
    }
}

//优选老师的适配器
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

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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

//推荐老师的适配器
class TeacherRecommendAdapter extends RecyclerView.Adapter<TeacherRecommendAdapter.ViewHolder> {

    private List<TeacherInformation> mList;

    public TeacherRecommendAdapter(List<TeacherInformation> mList) {
        this.mList = mList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView teacherInfo, teacherSalary, teachingAge;

        public ViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.recommend_teacher_pic);
            teacherInfo = itemView.findViewById(R.id.recommend_teacher_info);
            teacherSalary = itemView.findViewById(R.id.recommend_teacher_salary);
            teachingAge = itemView.findViewById(R.id.recommend_teaching_age);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(HomePageFragment.mContext)
                .inflate(R.layout.teacher_info_recommand_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TeacherInformation teacher = mList.get(position);
        holder.teacherSalary.setText("$" + teacher.getPrice() + "起");
        holder.img.setImageResource(teacher.getPictureID());
        holder.teacherInfo.setText(teacher.getInfo() + teacher.getName());
        holder.teachingAge.setText("教龄" + teacher.getTeachingAge());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}

//此为老师资讯类
class TeacherInformation {

    private int pictureID;
    private String info;
    private float price;
    private int teachingAge;
    private String name;

    public TeacherInformation(int pictureID, String info, float price, int teachingAge, String name) {
        this.pictureID = pictureID;
        this.info = info;
        this.price = price;
        this.teachingAge = teachingAge;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTeachingAge() {
        return teachingAge;
    }

    public void setTeachingAge(int teachingAge) {
        this.teachingAge = teachingAge;
    }

    public int getPictureID() {
        return pictureID;
    }

    public void setPictureID(int pictureID) {
        this.pictureID = pictureID;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    //利用静态方法创造该类的对象实体
    //这里用回圈创建20个实体,只是为了表现UI可以滑动功能.
    //PS 逻辑开发时可以连这个类都注解掉, 去创建自己需要的对象个数和对象的meta-data
    public static List<TeacherInformation> initTeacherInformation() {
        List<TeacherInformation> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new TeacherInformation(R.drawable.hit_activity_1, "小初教师", 190, 14, "何老师"));
        }
        return list;
    }
}