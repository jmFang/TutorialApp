package com.example.jiamoufang.tutorialapp.db.localDB.bean;

import com.example.jiamoufang.tutorialapp.model.bean.Information;
import com.example.jiamoufang.tutorialapp.model.bean.Order;
import com.example.jiamoufang.tutorialapp.model.bean.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import rx.Observable;

/**
 * Created by duang1996 on 2017/12/23.
 */

public class bmobDb  {
    private User user;
    private int count = 0;

    public bmobDb() {
        user = BmobUser.getCurrentUser(User.class);
    }

    /* 修改头像
     * @param 图片在本地的sdcard中的路径
     * 如：path = "sdcard/temp.jpg"
     */
    public void modifyAvatar(String path) {
        BmobFile bmobFile = new BmobFile(new File(path));

        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){

                } else {

                }
            }
            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）

            }
        });
    }

    /* 修改昵称
    * @param 昵称
    */
    public void modifyNickname(String name) {
        user.setRealName(name);
        updateInfo();
    }

    /* 修改角色
    * @param 老师 : true  学生 : false
    */
    public void modifyRole(Boolean role) {
        user.setRole(role);
        updateInfo();
    }

    /* 修改性别
    * @param 男：true； 女： false
    */
    public void modifySex(Boolean sex) {
        user.setSex(sex);
        updateInfo();
    }

    /* 修改城市
    * @param 城市名
    */
    public void modifyCity(String city) {
        user.setCity(city);
        updateInfo();
    }

    /* 修改住址
    * @param 详细地址
    */
    public void modifyAddress(String address) {
        user.setAddress(address);
        updateInfo();
    }

    /* 修改密码
    *修改密码前先进行密码的验证，包括先输入旧密码验证和两次新密码的输入是否相同
    * 验证完成后传入参数
    * @param npw:新密码
    */
    public void modifyPassword(String npw) {
        user.setPassword(npw);
        updateInfo();
    }

    /* 根据学生返回订单列表
     * 适配“我的”页面“我的老师”
     * 注意由于BmobQuery采用的是异步的方法，因此调用该函数的时候能需要采用以下方法
     */
    /*
      new bmobDb().findMyTeachers().subscribe(new Action1<List<Order>>() {
            @Override
            public void call(List<Order> orders) {

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
     */
    public Observable<List<Order>> findOrderForStudent() {

        BmobQuery<Order> query = new BmobQuery<Order>();
        query.addWhereEqualTo("user", user);  // 查询当前用户的所有订单
        query.order("-createdAt");     //根据订单建立时间的倒序排列
        query.include("teacher");  // 希望在查询帖子信息的同时也把teacher的信息查询出来

        return query.findObjectsObservable(Order.class);
    }

    /* 根据老师返回我的订单列表
     * 适配“我的”页面“我的学员”
     */
    public Observable<List<Order>> findOrderForTeacher() {

        BmobQuery<Order> query = new BmobQuery<Order>();
        query.addWhereEqualTo("teacher", user);  // 查询老师为当前用户的所有订单
        query.order("-createdAt");    //根据订单建立时间的倒序排列
        query.include("user");  // 希望在查询帖子信息的同时也把teacher的信息查询出来

        return query.findObjectsObservable(Order.class);
    }

    /* 根据userId返回特定的User列表
     * @param userId
     */
    public Observable<List<User>> findUserById(String userId) {
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("objectId", userId );

        return query.findObjectsObservable(User.class);
    }

    /* 根据年级来筛选家教订单
     * @param 年级数：小学：1~6； 初中7~9； 高中：10~12； 大学：13； 其他：0
     */
    public Observable<List<Order>> findOrderByGrade(int grade) {
        BmobQuery<Order> query = new BmobQuery<Order>();
        query.addWhereEqualTo("grade", grade);
        query.order("-createdAt");    //根据订单建立时间的倒序排列

        return query.findObjectsObservable(Order.class);
    }

    /* 根据家教科目来筛选订单
     * @param 家教科目
     */

    public Observable<List<Order>> findOrderBySubject(String sub) {
        BmobQuery<Order> query = new BmobQuery<Order>();
        query.addWhereEqualTo("subject", sub);
        query.order("-createdAt");    //根据订单建立时间的倒序排列

        return query.findObjectsObservable(Order.class);
    }

    protected void updateInfo() {
        user.update(user.getObjectId(),new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){

                }else{

                }
            }
        });
    }
}
