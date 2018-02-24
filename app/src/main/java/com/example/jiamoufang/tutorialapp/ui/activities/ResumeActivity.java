package com.example.jiamoufang.tutorialapp.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.db.localDB.bean.bmobDb;
import com.example.jiamoufang.tutorialapp.model.UserModel;
import com.example.jiamoufang.tutorialapp.model.bean.TeacherInformation;
import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.example.jiamoufang.tutorialapp.ui.base.ParentWithNaviActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

public class ResumeActivity extends ParentWithNaviActivity {

    @Bind(R.id.et_resume_teachingExp)
    EditText et_resume_teachingExp;
    @Bind(R.id.et_resume_sophisticate)
    EditText et_resume_sophisticate;
    @Bind(R.id.et_resume_school)
    EditText et_resume_school;
    @Bind(R.id.et_resume_saying)
    EditText et_resume_saying;

    @Bind(R.id.bt_resume_clear)
    Button bt_resume_clear;
    @Bind(R.id.bt_resume_commit)
    Button bt_resume_commit;

    private User user = UserModel.getInstance().getCurrentUser();
    private bmobDb db = bmobDb.getInstance();

    private TeacherInformation t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        //绑定数据
        ButterKnife.bind(this);
        //初始化nav bar
        initNaviView();
        //初始化数据
        initData();
    }

    private void initData() {
        bmobDb.getInstance().findTeacherInformationByUsername(user.getUsername()).subscribe(new Action1<List<TeacherInformation>>() {
            @Override
            public void call(List<TeacherInformation> teacherInformations) {
                Log.d("MyDebug",String.valueOf(teacherInformations.size()));
                if (teacherInformations.size() != 1) {
                    toast("System Error");
                } else {
                    t = teacherInformations.get(0);
                    et_resume_teachingExp.setText(t.getExperience());
                    et_resume_sophisticate.setText(t.getPractice());
                    et_resume_school.setText(t.getSchool());
                    et_resume_saying.setText(t.getTeachingSaying());
                    Log.d("MyDebug","数据初始化");
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.d("MyDebug","查询老师信息出错");
            }
        });
    }

    @Override
    protected String title() {
        return null;
    }

    @OnClick({R.id.bt_resume_clear, R.id.bt_resume_commit})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_resume_clear:
                handleClear();
                break;
            case R.id.bt_resume_commit:
                handleCommit();
                break;
        }
    }

    private void handleCommit() {
        db.updateTeacherInformation(t,
                et_resume_school.getText().toString(),
                et_resume_teachingExp.getText().toString(),
                et_resume_saying.getText().toString(),
                et_resume_sophisticate.getText().toString()
        );
        toast("已保存");
        finish();
    }

    private void handleClear() {
        et_resume_school.setText("");
        et_resume_sophisticate.setText("");
        et_resume_teachingExp.setText("");
        toast("请重新填写");
    }

    @Override
    public void onBackPressed() {
        handleCommit();
        super.onBackPressed();
    }
}
