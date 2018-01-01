package com.example.jiamoufang.tutorialapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.adapter.QueryAdapter;
import com.example.jiamoufang.tutorialapp.adapter.holder.QueryHolder;
import com.example.jiamoufang.tutorialapp.db.localDB.bean.bmobDb;
import com.example.jiamoufang.tutorialapp.factory.ImageLoaderFactory;
import com.example.jiamoufang.tutorialapp.model.bean.Order;
import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.example.jiamoufang.tutorialapp.ui.base.ParentWithNaviActivity;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class MyTeachersActivity extends ParentWithNaviActivity {

    private RecyclerView result;

    private QueryAdapter adapter;
    private List<Order> data = new ArrayList<>();

    @Override
    protected String title() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        //初始化导航栏
        initNaviView();
        setUp();
        displayMyTeachers();
    }

    private void setUp() {
        findViews();
        setUpRecyclerView();
    }

    private void findViews() {
        result = (RecyclerView) findViewById(R.id.result);
    }

    private void setUpRecyclerView() {
        result.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QueryAdapter<Order>(this, R.layout.item_query_user, data) {
            @Override
            public void convert(QueryHolder holder, Order o) {
                User teacher = o.getTeacher();
                ImageView user_pic = holder.getView(R.id.user_pic);
                String url;
                if (teacher.getAvatar() != null)
                    url = teacher.getAvatar().getUrl();
                else
                    url = "";
                ImageLoaderFactory.getLoader(getApplicationContext()).loadAvatar(user_pic, url, R.mipmap.default_ss);
                TextView user_name = holder.getView(R.id.user_name);
                user_name.setText(teacher.getRealName());
                TextView user_sex = holder.getView(R.id.user_sex);
                user_sex.setText(sex(teacher.getSex()));
                TextView teacher_level = holder.getView(R.id.user_educated_level);
                teacher_level.setText(level(teacher.getEducatedLevel()));
                TextView user_city = holder.getView(R.id.user_city);
                user_city.setText(teacher.getCity());
                TextView user_phone = holder.getView(R.id.user_phone);
                user_phone.setText(teacher.getMobilePhoneNumber());
            }
        };
        adapter.setOnItemClickListener(new QueryAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MyTeachersActivity.this, TeacherDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("teacher", data.get(position).getTeacher());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        result.setAdapter(adapter);
    }

    private String sex(Boolean b) {
        if (b)
            return "男";
        else
            return "女";
    }

    private String level(Integer educatedLevel) {
        switch (educatedLevel) {
            case 1:
                return "小学";
            case 2:
                return "初中";
            case 3:
                return "高中";
            case 4:
                return "大专";
            case 5:
                return "本科";
            case 6:
                return "硕士";
            case 7:
                return "博士";
            default:
                return "";
        }
    }

    private void displayMyTeachers() {
        new bmobDb().findOrderForStudent().subscribe(new Action1<List<Order>>() {
            @Override
            public void call(List<Order> orders) {
                for (Order o : orders)
                    adapter.addItem(o);

                //下面是为了测试添加的一项数据
                Order o = new Order();
                User teacher = new User();
                teacher.setRealName("小初数学何老师");
                teacher.setSex(true);
                teacher.setEducatedLevel(6);
                teacher.setCity("广州");
                teacher.setMobilePhoneNumber("12345678987");
                o.setTeacher(teacher);
                adapter.addItem(o);

                if (adapter.getItemCount() == 0)
                    Toast.makeText(MyTeachersActivity.this, "您还没有授课老师", Toast.LENGTH_SHORT).show();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
            }
        });
    }

}
