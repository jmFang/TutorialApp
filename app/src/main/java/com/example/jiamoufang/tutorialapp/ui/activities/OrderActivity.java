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
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.ui.base.ParentWithNaviActivity;


public class OrderActivity extends ParentWithNaviActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner_grade, spinner_class, spinner_subject, spinner_diploma;

    private EditText order_salary, order_address;

    private String[] elementary_string = {"1", "2", "3", "4", "5", "6"};
    private String[] junior_high_string = {"1", "2", "3",};
    private String[] college_string = {"1", "2", "3", "4",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        initViews();
        initNaviView();

    }

    @Override
    protected String title() {
        return "找老师";
    }

    private void initViews() {
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
                order_salary.setText("");
                order_address.setText("");
                //默认学员为小学学生
                spinner_class.setSelection(0);
                //小学一年级
                spinner_grade.setSelection(0);
                //科目默认语文
                spinner_subject.setSelection(0);
                //老师学历默认为本科
                spinner_diploma.setSelection(4);
            }
        });

        //提交按鈕的監聽
        findViewById(R.id.order_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ok = checkValid();
                if (ok) {

                }
            }
        });
    }
    /*
    * 检车所有输入或填写是否合法
    * */
    private boolean checkValid() {
        if (TextUtils.isEmpty(order_address.getText().toString())|| TextUtils.isEmpty( order_salary.getText().toString())) {
            toast("输入不能空");
            return false;
        }
        if (spinner_grade.toString() == null || spinner_diploma.toString() == null
                || spinner_subject.toString() == null || spinner_class.toString() == null) {
            toast("请完成所有选择");
            return false;
        }
        return true;
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
