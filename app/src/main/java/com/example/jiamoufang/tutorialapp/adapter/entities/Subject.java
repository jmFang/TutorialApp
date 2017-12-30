package com.example.jiamoufang.tutorialapp.adapter.entities;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jiamoufang on 2017/12/30.
 */

public class Subject {
    private int subjectImgId;
    private String subjectText;

    public Subject(int subjectId, String subject) {
        this.subjectImgId = subjectId;
        this.subjectText = subject;
    }

    public void setSubjectImgId(int subjectImgId) {
        this.subjectImgId = subjectImgId;
    }

    public void setSubjectText(String subjectText) {
        this.subjectText = subjectText;
    }

    public int getSubjectImgId() {
        return subjectImgId;
    }

    public String getSubjectText() {
        return subjectText;
    }
}
