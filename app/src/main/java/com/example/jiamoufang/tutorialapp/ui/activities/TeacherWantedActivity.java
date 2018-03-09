package com.example.jiamoufang.tutorialapp.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.adapter.QueryAdapter;
import com.example.jiamoufang.tutorialapp.adapter.TeacherWantedAdapter;
import com.example.jiamoufang.tutorialapp.adapter.entities.TeacherWanted;
import com.example.jiamoufang.tutorialapp.adapter.holder.QueryHolder;
import com.example.jiamoufang.tutorialapp.db.localDB.bean.bmobDb;
import com.example.jiamoufang.tutorialapp.model.bean.Order;
import com.example.jiamoufang.tutorialapp.model.bean.TeacherInformation;
import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.example.jiamoufang.tutorialapp.ui.base.ParentWithNaviActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;

public class TeacherWantedActivity extends ParentWithNaviActivity {

    @Bind(R.id.rv_teacher_wanted)
    RecyclerView rv_teacher_wanted;


    private List<User> mTeacherInfoList = new ArrayList<>();
    private QueryAdapter<User> adapter;
    private  final Context mContext = this;
    private String filter = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_wanted);
        ButterKnife.bind(this);
        initNaviView();
        int  level =  getBundle().getInt("level");
        // filter out unsatisfied data with object
        toast("selected level is " + level);
        handlerForFilterWanted(level);
        setUpRecyclerView();

      //  initList();
    }

    private void setUpRecyclerView() {
        rv_teacher_wanted.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QueryAdapter<User>(this, R.layout.item_teacher_filte_by_diploma, mTeacherInfoList) {
            @Override
            public void convert(final QueryHolder holder, User user) {
                //绑定viewHolder
                toast(user.toString());
                ImageView userAvatar = holder.getView(R.id.img_teacher_info);
                //获取用户头像
                if (user.getAvatar() != null) {
                    //Url需要保证是正确的
                    Glide.with(mContext).load(user.getAvatar().getUrl()).into(userAvatar);
                } else {
                    Glide.with(mContext).load(R.mipmap.default_smg).into(userAvatar);
                }

                //获取学历
                TextView tv_diploma = holder.getView(R.id.tv_teacher_diploma);
                tv_diploma.setText(EducatedLevelToString(user.getEducatedLevel()));
                //获取姓名
                final TextView tv_teacher_name = holder.getView(R.id.tv__teacher_name);
                if(user.getRole() != null) {
                    tv_teacher_name.setText(user.getRealName());
                } else {
                    tv_teacher_name.setText(user.getUsername());
                }
                //根据名字查询该用户的箴言
                bmobDb.getInstance().findTeacherInformationByUsername(user.getUsername()).subscribe(new Action1<List<TeacherInformation>>() {
                    @Override
                    public void call(List<TeacherInformation> teacherInformations) {
                        if (teacherInformations.size() != 1) {
                            Log.d("TeacherWantedActivity","查询用户的教师信息出错");
                        } else {
                            Log.d("Mydebug",teacherInformations.get(0).getTeachingSaying());
                            TextView tv_teacher_saying = holder.getView(R.id.tv_teacher_saying);
                            tv_teacher_saying.setText(teacherInformations.get(0).getTeachingSaying());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("TeacherWantedActivity","查询回调出错");
                    }
                });
            }
        };

        adapter.setOnItemClickListener(new QueryAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                //处理点击事件
                Intent it = new Intent(TeacherWantedActivity.this, TeacherDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("teacher",mTeacherInfoList.get(position));
                it.putExtras(bundle);
                startActivity(it);
            }
        });

        rv_teacher_wanted.setAdapter(adapter);
    }

    private String EducatedLevelToString(Integer educatedLevel) {
        if (educatedLevel != null) {
            int e = educatedLevel.intValue();
            switch (e) {
                case 0:return "小学";
                case 1:return "初中";
                case 2:return "高中";
                case 3:return "专科";
                case 4:return "本科";
                case 5:return "硕士";
                case 6:return "博士";
                default:return "";
            }
        } else return "";
    }

    //年级数：小学：1~6； 初中7~9； 高中：10~12； 大学：13； 其他：0
    private void handlerForFilterWanted(int level) {
        switch (level) {
            case 1:
                filter = "小初学历";
                handleResult(bmobDb.getInstance().findTeacherInfoByDiploma(0,1));
                break;
            case 2:
                filter = "高中学历";
                handleResult(bmobDb.getInstance().findTeacherInfoByDiploma(2,2));
                break;
            case 3:
                filter = "专本学历";
                handleResult(bmobDb.getInstance().findTeacherInfoByDiploma(3,4));
                break;
            case 4:
                filter = "硕博学历";
                handleResult(bmobDb.getInstance().findTeacherInfoByDiploma(5,6));
                break;
            default:
                break;
        }
    }

    private void handleResult(Observable<List<User>> teacherInfoByDiploma) {
        teacherInfoByDiploma.subscribe(new Action1<List<User>>() {
            @Override
            public void call(List<User> users) {
                //添加查询得到的数据
                Log.d("callback debug",String.valueOf(users.size()));
                for (User user : users)
                    adapter.addItem(user);

                if (mTeacherInfoList.size() == 0) {
                    Log.d("TeacherWantedActivity","List‘ size == 0");
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
