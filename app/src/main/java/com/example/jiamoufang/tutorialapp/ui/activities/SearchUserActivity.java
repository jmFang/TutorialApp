package com.example.jiamoufang.tutorialapp.ui.activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.adapter.SearchUserAdapter;
import com.example.jiamoufang.tutorialapp.adapter.listener.OnRecyclerViewListener;
import com.example.jiamoufang.tutorialapp.model.BaseModel;
import com.example.jiamoufang.tutorialapp.model.UserModel;
import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.example.jiamoufang.tutorialapp.ui.base.ParentWithNaviActivity;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SearchUserActivity extends ParentWithNaviActivity {
    //搜索框
    @Bind(R.id.et_find_name)
    EditText et_find_name;
    //刷新layout
    @Bind(R.id.sw_refresh)
    SwipeRefreshLayout sw_refresh;
    //搜索按钮
    @Bind(R.id.btn_search)
    Button btn_search;
    //搜索结果recyclerView
    @Bind(R.id.rc_view)
    RecyclerView rc_view;
    //布局管理和适配器
    LinearLayoutManager layoutManager;
    SearchUserAdapter adapter;

    /*
    * 设置导航条标题
    * */
    @Override
    protected String title() {
        return "抱大腿";
    }
    /*
    * 初始化
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
        //调用父类方法初始化导航条：绑定和设置监听等
        //绑定统一在BaseActivity已解决
        initNaviView();
        adapter = new SearchUserAdapter();
        layoutManager = new LinearLayoutManager(this);

        /*设置布局管理器和适配器*/
        rc_view.setLayoutManager(layoutManager);
        rc_view.setAdapter(adapter);

        /*使能和设置监听*/
        sw_refresh.setEnabled(true);
        sw_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //查询更新
                query();
            }
        });

        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                User user = adapter.getItem(position);
                bundle.putSerializable("u", user);
                /*
                * param false: 跳转新的Activity,但不结束当前（可返回）
                * */
                startActivity(UserInfoActivity.class, bundle, false);
            }
            /*
            * 长按无设置效果
            * */
            @Override
            public boolean onItemLongClick(int position) {
                return true;
            }
        });
    }
    /*
    * 点击查询按钮，刷新界面
    * */
    @OnClick(R.id.btn_search)
    public void onSearchClick(View view) {
        sw_refresh.setRefreshing(true);
        query();
    }
    /*
    * 查询用户
    * */
    public void query() {
        String name = et_find_name.getText().toString();
        if (TextUtils.isEmpty(name)) {
            toast("请输入查找用户名");
            sw_refresh.setRefreshing(false);
            return;
        }
        UserModel.getInstance().queryUsers(name, BaseModel.DEFAULT_LIMIT, new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    /*查找完毕，结束刷新*/
                    sw_refresh.setRefreshing(false);
                    adapter.setDatas(list);
                    adapter.notifyDataSetChanged();
                } else {
                    sw_refresh.setRefreshing(false);
                    adapter.setDatas(null);
                    adapter.notifyDataSetChanged();
                    toast("查询出错");
                    Logger.e(e);
                }
            }
        });
    }
}
