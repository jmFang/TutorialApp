package com.example.jiamoufang.tutorialapp.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.ui.base.ParentWithNaviActivity;

public class QueryTeacherBySubjectActivity extends ParentWithNaviActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_teacher_by_subject);
        initNaviView();

    }

    @Override
    protected String title() {
        return null;
    }
}
