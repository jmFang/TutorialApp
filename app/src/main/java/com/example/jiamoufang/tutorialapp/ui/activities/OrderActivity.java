package com.example.jiamoufang.tutorialapp.ui.activities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.jiamoufang.tutorialapp.R;


public class OrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner_grade, spinner_class, spinner_subject, spinner_diploma;

    private EditText order_salary, order_address;

    private String[] elementary_string = {"1", "2", "3", "4", "5", "6"};
    private String[] junior_high_string = {"1", "2", "3",};
    private String[] college_string = {"1", "2", "3", "4",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initView();

    }

    private void initView() {
        //绑定spinner
        spinner_class = (Spinner) findViewById(R.id.order_class_spinner);
        spinner_grade = (Spinner) findViewById(R.id.order_grade_spinner);
        spinner_diploma = (Spinner) findViewById(R.id.order_diploma_spinner);
        spinner_subject = (Spinner) findViewById(R.id.order_subject_spinner);

        //监听spinner_class因为他选项的改变会影响spinner_grade的显示行为
        spinner_class.setOnItemSelectedListener(this);

        //绑定edittext
        order_address = (EditText) findViewById(R.id.order_address_editText);
        order_salary = (EditText) findViewById(R.id.order_salary_editText);

        //重寫按鈕的監聽
        findViewById(R.id.order_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //提交按鈕的監聽
        findViewById(R.id.order_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.order_class_spinner:

                String[] tmp;

                //处理所要加载的资讯
                if (position == 0) {
                    tmp = elementary_string;
                } else if (position == 1 || position == 2) {
                    tmp = junior_high_string;
                } else {
                    tmp = college_string;
                }

                ArrayAdapter<String> gradeAd = new ArrayAdapter<>(
                        this, android.R.layout.simple_spinner_item, tmp);

                gradeAd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner_grade.setAdapter(gradeAd);
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}


    //该方法将图片切割为圆形(逻辑开发时不要注解此方法)
    public static Drawable getRoundedShape(Bitmap scaleBitmapImage, Resources resource) {
        // TODO Auto-generated method stub
        int targetWidth = 250;
        int targetHeight = 250;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth,
                        targetHeight), null);
        return new BitmapDrawable(resource, targetBitmap);
    }

}
