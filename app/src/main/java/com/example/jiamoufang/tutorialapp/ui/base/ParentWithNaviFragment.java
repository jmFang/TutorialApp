package com.example.jiamoufang.tutorialapp.ui.base;

import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.ui.base.ParentWithNaviActivity;
/**
 * Created by jiamoufang on 2017/12/21.
 * 封装了导航栏的Fragment需要继承此类
 * 为操作导航栏和实现导航栏代码重用提供接口
 * 因此内容和ParentWithNaviActivity几乎是一样的
 */

public abstract class ParentWithNaviFragment extends BaseFragment {

    //作为获取控件实例的上下文对象
    protected View rootView = null;

    //监听导航栏左右两边的点击事件
    private ParentWithNaviActivity.ToolBarListener listener;

    private TextView tv_title;
    public TextView tv_right;
    public ImageView tv_left;
    public LinearLayout ll_navi;

    protected <T extends View> T getView(int id) {
        return (T) rootView.findViewById(id);
    }

    private void setListener(ParentWithNaviActivity.ToolBarListener listener) {
        this.listener = listener;
    }
    /**TODO 导航栏：设置导航栏监听
     * @return
     */
    public ParentWithNaviActivity.ToolBarListener setToolBarListener(){
        return null;
    }

    /*TODO 导航栏：监听器
    * 具体化OnClickListener
    * */
    View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_left:
                    if (listener != null){
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

    /**TODO 导航栏：获取导航栏标题
     * @return
     */
    protected abstract String title();

    /**TODO 导航栏：可导航栏右边，可以是string或图片资源id，非必填项
     * @return
     */
    public  Object right(){
        return null;
    }

    /**TODO 导航栏：获取导航栏左边
     * @return
     */
    public Object left(){
        return null;
    }
    /*
    *TODO 导航栏：刷新导航栏设置
    * */
    private void refreshTop() {
        setLeftView(left());
        setValue(R.id.tv_right, right());
        this.tv_title.setText(title());
    }

    /*TODO 导航栏：设置导航栏左边控件
    *
    * */
    private void setLeftView(Object obj){
        if(obj !=null && !obj.equals("")){
            tv_left.setVisibility(View.VISIBLE);
            if(obj instanceof Integer){
                tv_left.setImageResource(Integer.parseInt(obj.toString()));
            }else{
                tv_left.setImageResource(R.drawable.base_action_bar_back_bg_selector);
            }
        }else{
            tv_left.setVisibility(View.INVISIBLE);
        }
    }

    /*TODO 导航栏：设置导航栏右边控件
    * @param id
    * @param obj
    * */
    private void setValue(int id,Object obj){
        if (obj != null && !obj.equals("")) {
            ((TextView) getView(id)).setText("");
            getView(id).setBackgroundDrawable(new BitmapDrawable());
            if (obj instanceof String) {
                ((TextView) getView(id)).setText(obj.toString());
            } else if (obj instanceof Integer) {
                getView(id).setBackgroundResource(Integer.parseInt(obj.toString()));
            }
        } else {
            ((TextView) getView(id)).setText("");
            getView(id).setBackgroundDrawable(new BitmapDrawable());
        }
    }

    /**TODO 导航栏：设置导航条背景色
     * @param color
     */
    public void setNavBackground(int color){
        ll_navi.setBackgroundColor(color);
    }

    /**TODO 导航栏：设置右边按钮的文字大小
     * @param dimenId
     */
    public void setRightTextSize(float dimenId){
        tv_right.setTextSize(dimenId);
    }

    /*TODO 导航栏：初始化
    * 初始化导航条
    * */
    public void initNaviView(){
        tv_title = getView(R.id.tv_title);
        tv_right = getView(R.id.tv_right);
        tv_left = getView(R.id.tv_left);
        setListener(setToolBarListener());
        tv_left.setOnClickListener(clickListener);
        tv_right.setOnClickListener(clickListener);
        tv_title.setText(title());
        refreshTop();
    }

}
