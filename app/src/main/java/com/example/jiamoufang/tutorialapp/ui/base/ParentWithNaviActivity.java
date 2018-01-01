package com.example.jiamoufang.tutorialapp.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.textservice.TextInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.example.jiamoufang.tutorialapp.utils.ActivityCollector;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.socketio.callback.StringCallback;

/**
 * Created by jiamoufang on 2017/12/20.
 * 对于需要显示导航栏的Activity要继承ParentWithNaviActivity
 * 实现导航栏的代码重用
 * 避免一个导航栏可被多个Activity重用
 */

public abstract class ParentWithNaviActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /*
            * 接口封装
            * */
    public interface ToolBarListener {
        void clickLeft();
        void clickRight();
    }
    /*
    * 获取资源
    * @param id
    * @return (T) View
    * */
    @SuppressWarnings("unchecked")
    protected <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }
    /*
    * 获取Drawable资源
    * @param id
    * @return
    * */
    public Drawable getDrawableResource(int id) {
        return getResources().getDrawable(id,null);
    }
    /*
    * 启动指定的Activity
    * @param target
    * @param bundle
    * */
    public void startActivity(Class<? extends Activity> target, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this,target);
        if (bundle != null) {
            intent.putExtra(this.getPackageName(), bundle);
        }
        startActivity(intent);
    }
    /*
    * 获取当前的UserID
    * @return bmob user id
    * */
    public String getCurrentUid() {
        return BmobUser.getCurrentUser(User.class).getObjectId();
    }
    /*
    * 按下返回键的处理
    * @return
    * */
    public boolean handleBackPressed() {
        return false;
    }

    public ToolBarListener listener;
    public TextView tv_title;
    public ImageView tv_left;
    public TextView tv_right;

    /*TODO 导航栏标题：必填项
    * @return
    * */
    protected abstract String title();

    /*TODO 导航栏左边：可以是String或者图片资源ID，非必须
    * 由子类具体化
    * @return
    * */
    public Object left() {
        return null;
    }

    /*TODO 导航栏右边：可以是String或者图片资源ID，非必须
    * @return
    * */
    public Object right() {
        return null;
    }

    /*TODO 导航栏：设置导航栏监听，非必须
    * @return
    * */
    public ToolBarListener setToolBarListener() {
        return null;
    }

    /*TODO 导航栏;设置监听，非必须
    * @return
    * */
    protected void setNaviListener(ToolBarListener listener) {
        this.listener = listener;
    }

    /*
    * 获取一个Listener对象
    * 处理导航栏的点击事件
    * 主要是导航栏左边和右边的可按控件（图片、文字等）
    * */
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_left:
                    //如果没有设置监听器，那么listener为null，默认为返回
                    if (listener == null) {
                        finish();
                    } else {
                        listener.clickLeft();
                    }
                    break;
                case R.id.tv_right:
                    if (listener != null)
                        listener.clickRight();
                    break;
                default:
                    break;
            }
        }
    };

    /*TODO 导航栏：初始化导航条
    *
    * */
    public void initNaviView() {
        tv_title = getView(R.id.tv_title);
        tv_right = getView(R.id.tv_right);
        tv_left = getView(R.id.tv_left);
        /*在继承的子类中需要自定义传入需要的监听*/
        setNaviListener(setToolBarListener());
        /*设置导航栏左右控件的监听*/
        tv_left.setOnClickListener(clickListener);
        tv_right.setOnClickListener(clickListener);
        /*设置导航栏的标题*/
        tv_title.setText(title());
        /*刷新导航栏，见下面方法具体*/
        refreshTop();

    }

    /*TODO 导航栏：刷新导航栏
    *
    * */
    protected void refreshTop() {
        /*设置导航栏左边控件
        * 如果子类中没有改变此方法，那么默认为返回
        * */
        setLeftView(left() == null? R.drawable.base_action_bar_back_bg_selector: left());
        /*设置导航栏右边控件*/
        setValue(R.id.tv_right, right());
        /*设置标题*/
        this.tv_title.setText(title());
    }

    private void setValue(int id, Object object) {
        /*当右边控件不是文字的时候*/
        if (object != null && !object.equals("")) {
            ((TextView)getView(id)).setText("");
            getView(id).setBackgroundDrawable(new BitmapDrawable());
            if (object instanceof String) {
                ((TextView) getView(id)).setText(object.toString());
            } else {
                getView(id).setBackgroundResource(Integer.parseInt(object.toString()));
            }
        } else {
            ((TextView)getView(id)).setText("");
            getView(id).setBackgroundDrawable(new BitmapDrawable());
        }
    }

    private void setLeftView(Object object) {
        //object 为非null的String或者为int的情况
        if (object != null && !object.equals("")) {
            tv_left.setVisibility(View.VISIBLE);
            if (object instanceof Integer) { //为 int
                tv_left.setImageResource(Integer.parseInt(object.toString()));
            } else {
                tv_left.setImageResource(R.drawable.base_action_bar_back_bg_selector);
            }
        } else {  //非String
            tv_left.setVisibility(View.INVISIBLE);
        }
    }


}
