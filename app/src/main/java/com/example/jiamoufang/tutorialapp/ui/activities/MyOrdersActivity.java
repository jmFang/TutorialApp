package com.example.jiamoufang.tutorialapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.adapter.QueryAdapter;
import com.example.jiamoufang.tutorialapp.adapter.holder.QueryHolder;
import com.example.jiamoufang.tutorialapp.db.localDB.bean.bmobDb;
import com.example.jiamoufang.tutorialapp.model.bean.Order;
import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.example.jiamoufang.tutorialapp.ui.base.ParentWithNaviActivity;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class MyOrdersActivity extends ParentWithNaviActivity {

    private RecyclerView result;

    private QueryAdapter adapter;
    private List<Order> data = new ArrayList<>();

    private User currentUser;

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
        currentUser = (User)getBundle().getSerializable("user");
        setUp();
        displayMyOrders();
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
        adapter = new QueryAdapter<Order>(this, R.layout.item_query_order, data) {
            @Override
            public void convert(QueryHolder holder, Order o) {
                TextView grade = holder.getView(R.id.grade);
                grade.setText(grade(o.getGrade()));
                TextView subject = holder.getView(R.id.subject);
                subject.setText(o.getSubject());
                TextView time = holder.getView(R.id.time);
                time.setText(o.getTime());
                TextView tv_user_name = holder.getView(R.id.tv_user_name);
                TextView user_name = holder.getView(R.id.user_name);
                if (currentUser.getRole()) {
                    tv_user_name.setText("授课学生");
                    user_name.setText(o.getUser().getRealName());
                } else {
                    tv_user_name.setText("授课老师");
                    user_name.setText(o.getTeacher().getRealName());
                }
            }
        };
        adapter.setOnItemClickListener(new QueryAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MyOrdersActivity.this, OrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("order", data.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        result.setAdapter(adapter);
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

    private void displayMyOrders() {
        if (currentUser.getRole())
            findOrdersForTeacher();
        else
            findOrdersForStudent();
    }

    private void findOrdersForTeacher() {
        new bmobDb().findOrderForTeacher().subscribe(new Action1<List<Order>>() {
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
                teacher.setAddress("大学城中山大学东校区");
                teacher.setMobilePhoneNumber("12345678987");
                o.setTeacher(teacher);
                User student = new User();
                student.setRealName("小明");
                student.setSex(true);
                student.setEducatedLevel(3);
                student.setCity("广州");
                student.setMobilePhoneNumber("22252225222");
                o.setUser(student);
                o.setSubject("英语");
                o.setGrade(12);
                o.setSalary("一小时200");
                o.setTime("周一晚7-8节");
                o.setDes("英语不好");
                adapter.addItem(o);

                if (adapter.getItemCount() == 0)
                    Toast.makeText(MyOrdersActivity.this, "您还没有订单", Toast.LENGTH_SHORT).show();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
            }
        });
    }

    private void findOrdersForStudent() {
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
                teacher.setAddress("大学城中山大学东校区");
                teacher.setMobilePhoneNumber("12345678987");
                o.setTeacher(teacher);
                User student = new User();
                student.setRealName("小明");
                student.setSex(true);
                student.setEducatedLevel(3);
                student.setCity("广州");
                student.setMobilePhoneNumber("22252225222");
                o.setUser(student);
                o.setSubject("英语");
                o.setGrade(12);
                o.setSalary("一小时200");
                o.setTime("周一晚7-8节");
                o.setDes("英语不好");
                adapter.addItem(o);

                if (adapter.getItemCount() == 0)
                    Toast.makeText(MyOrdersActivity.this, "您还没有订单", Toast.LENGTH_SHORT).show();
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
            }
        });
    }

}
