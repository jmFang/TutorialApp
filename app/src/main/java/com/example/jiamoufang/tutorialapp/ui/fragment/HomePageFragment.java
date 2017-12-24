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

import java.util.List;

public class HomePageFragment extends Fragment {

    String[] subjectMsg = {"数学", "语文", "英语", "物理", "化学", "生物", "政治", "历史", "地理"};

    private int[] tutorResID = {R.drawable.tutor_pic
            , R.drawable.tutor_pic2
            , R.drawable.tutor_pic3
            , R.drawable.turor_pic4};

    public static Context mContext;

    private List<TeacherInformation> mTeacherList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        mContext = view.getContext();

        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter();

        ViewPager viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(customPagerAdapter);

        RecyclerView recyclerView = view.findViewById(R.id.subject_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new SubjectAdapter(subjectMsg));

        return view;
    }

    private class CustomPagerAdapter extends PagerAdapter {

        private LayoutInflater mLayoutInflater;

        @Override
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
    }
}

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

class TeacherInformation {
    private int picutureID;
    private String info;
    private String price;
}