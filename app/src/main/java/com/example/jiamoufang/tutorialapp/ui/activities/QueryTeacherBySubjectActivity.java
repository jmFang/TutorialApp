package com.example.jiamoufang.tutorialapp.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.adapter.QueryAdapter;
import com.example.jiamoufang.tutorialapp.adapter.holder.QueryHolder;
import com.example.jiamoufang.tutorialapp.db.localDB.bean.bmobDb;
import com.example.jiamoufang.tutorialapp.model.UserModel;
import com.example.jiamoufang.tutorialapp.model.bean.Order;
import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.example.jiamoufang.tutorialapp.ui.base.ParentWithNaviActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class QueryTeacherBySubjectActivity extends ParentWithNaviActivity {

    @Bind(R.id.rv_orders_by_subject)
    RecyclerView rv_orders_by_subject;

    private QueryAdapter<Order> adapter;
    private List<Order> data = new ArrayList<>();
    private User currentUser;
    private String currentSubject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_teacher_by_subject);
        initNaviView();
        ButterKnife.bind(this);
        currentUser = UserModel.getInstance().getCurrentUser();
        //拉取数据
        currentSubject = getIntent().getStringExtra("subject");
        fetchOrdersBySubject(currentSubject);
        setUpRecyclerView();
        rv_orders_by_subject.setAdapter(adapter);

    }

    /*
    * 实现抽象接口
    * */
    private void setUpRecyclerView() {
        //rv_orders_by_subject = (RecyclerView) findViewById(R.id.rv_orders_by_subject);
        rv_orders_by_subject.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QueryAdapter<Order>(this, R.layout.item_order_filter, data) {
            @Override
            public void convert(QueryHolder holder, Order order) {
                    TextView tv_order_grade = holder.getView(R.id.tv_order_grade);
                    TextView tv_order_subject = holder.getView(R.id.tv_order_subject);
                    TextView tv_order_address = holder.getView(R.id.tv_order_address);
                    TextView tv_order_id = holder.getView(R.id.tv_order_id);
                    TextView tv_order_price = holder.getView(R.id.tv_order_price);
                    TextView tv_order_state = holder.getView(R.id.tv_order_state);
                    TextView tv_order_visit_times = holder.getView(R.id.tv_order_visit_times);

                    if (order.getGrade() != null) {
                        tv_order_grade.setText(gradeToString(order.getGrade()));
                    } else {
                        tv_order_grade.setText("不明");
                    }
                    if (order.getSubject() != null) {
                        tv_order_subject.setText(order.getSubject());
                    }
                    if (order.getAddress() != null) {
                        tv_order_address.setText(order.getAddress());
                    }
                    if (order.getObjectId() != null) {
                        tv_order_id.setText(order.getObjectId());
                    }
                    if (order.getSalary() != null) {
                        tv_order_price.setText(order.getSalary());
                    }
                    if (!order.getStatus().booleanValue()) {
                        tv_order_state.setText("未预约");
                    } else {
                        tv_order_state.setText("已预约");
                    }
                    if (order.getVisitedTimes() != null) {
                        tv_order_visit_times.setText(order.getVisitedTimes().toString());
                    } else {
                        tv_order_visit_times.setText("0");
                    }
            }

        };
        adapter.setOnItemClickListener(new QueryAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                //点击查看订单详情
                Intent it = new Intent(QueryTeacherBySubjectActivity.this, OrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("order",data.get(position));
                it.putExtras(bundle);
                startActivity(it);
            }
        });
    }

    private String gradeToString(Integer grade) {
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
    /*
    * 获取数据
    * */
    private void fetchOrdersBySubject(String currentSubject) {
        bmobDb.getInstance().findOrderBySubject(currentSubject).subscribe(new Action1<List<Order>>() {
            @Override
            public void call(List<Order> orders) {
                for (Order o : orders) {
                    adapter.addItem(o);
                }
                toast(data.size());
                if (data.size()==0) {
                    toast("当前尚未发布该科目的信息");
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    @Override
    protected String title() {
        return null;
    }
}
