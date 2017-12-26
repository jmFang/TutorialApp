package com.example.jiamoufang.tutorialapp.ui.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.jiamoufang.tutorialapp.R;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //将图片做成Bitmap,并将其制作切割,再设置成订单图片
        //(此处代码为UI测试所需用到的逻辑,在逻辑开发时可以注解之)
        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.tutor_pic);
        ImageView img = (ImageView) findViewById(R.id.order_pic);
        img.setImageDrawable(getRoundedShape(icon));
    }

    //该方法将图片切割为圆形
    public Drawable getRoundedShape(Bitmap scaleBitmapImage) {
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
        return new BitmapDrawable(getResources(), targetBitmap);
    }
}
