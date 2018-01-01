package com.example.jiamoufang.tutorialapp.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.adapter.TeacherWantedAdapter;
import com.example.jiamoufang.tutorialapp.adapter.entities.TeacherWanted;
import com.example.jiamoufang.tutorialapp.db.localDB.bean.bmobDb;
import com.example.jiamoufang.tutorialapp.model.bean.Order;
import com.example.jiamoufang.tutorialapp.ui.base.ParentWithNaviActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;

public class TeacherWantedActivity extends ParentWithNaviActivity {

    @Bind(R.id.rv_teacher_wanted)
    RecyclerView rv_teacher_wanted;


    private List<TeacherWanted> mTeacherWantedList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_wanted);
        ButterKnife.bind(this);
        initNaviView();

        int  level = getIntent().getExtras().getInt("level");
        // filter out unsatisfied data with object
        handlerForFilterWanted(level);

        initList();
        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv_teacher_wanted.setLayoutManager(lm);
        rv_teacher_wanted.setAdapter(new TeacherWantedAdapter(mTeacherWantedList));


    }

    //年级数：小学：1~6； 初中7~9； 高中：10~12； 大学：13； 其他：0
    private void handlerForFilterWanted(int level) {
        switch (level) {
            case 1:
                handleResult(bmobDb.getInstance().findOrderByGradeInterval(1,6));
                break;
            case 2:
                handleResult(bmobDb.getInstance().findOrderByGradeInterval(7,9));
                break;
            case 3:
                handleResult(bmobDb.getInstance().findOrderByGradeInterval(10,12));
                break;
            case 4:
                handleResult(bmobDb.getInstance().findOrderByGrade(13));
                break;
            default:
                handleResult(bmobDb.getInstance().findOrderByGrade(0));
                break;
        }
    }

    private void handleResult(Observable<List<Order>> result) {
        result.subscribe(new Action1<List<Order>>() {
            @Override
            public void call(List<Order> orders) {
                parseOrdersToWanteds(orders);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    private void parseOrdersToWanteds(List<Order> orders) {
        for (int i = 0; i < orders.size(); i++) {
            String[] schoolAndGrade = parseGradeFromOrder(orders.get(i).getGrade());
            mTeacherWantedList.add(new TeacherWanted(schoolAndGrade[0], schoolAndGrade[1],
                    orders.get(i).getSubject(), orders.get(i).getAddress(),orders.get(i).getSalary(),orders.get(i).getEducatedLevel()));
        }
    }
    //年级数：小学：1~6； 初中7~9； 高中：10~12； 大学：13； 其他：0
    private String[] parseGradeFromOrder(Integer grade) {
        if (1 <= grade.intValue() && grade.intValue() <= 6) {
            return new String[]{"小学",String.valueOf(grade.intValue())};
        } else if (7 <= grade.intValue() && grade.intValue() <= 9) {
            return new String[]{"初中",String.valueOf(grade.intValue()-6)};
        } else if (10 <= grade.intValue() && grade.intValue() <= 12) {
            return new String[]{"初中",String.valueOf(grade.intValue()-9)};
        } else if (grade.intValue() == 13) {
            return new String[]{"大学",""};
        } else {
            return new String[]{"",""};
        }
    }

    private void initList() {
        mTeacherWantedList.add(new TeacherWanted("小学","一年级","语文","广州番禺","100","本科"));
        mTeacherWantedList.add(new TeacherWanted("小学","一年级","语文","广州番禺","100","本科"));
    }

    @Override
    protected String title() {
        return null;
    }


}
