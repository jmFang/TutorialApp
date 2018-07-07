package com.example.jiamoufang.tutorialapp.db.localDB.bean;

import android.util.Log;
import android.widget.Toast;

import com.example.jiamoufang.tutorialapp.model.UserModel;
import com.example.jiamoufang.tutorialapp.model.bean.Information;
import com.example.jiamoufang.tutorialapp.model.bean.Order;
import com.example.jiamoufang.tutorialapp.model.bean.TeacherInformation;
import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.nostra13.universalimageloader.utils.L;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import rx.Observable;

/**
 * Created by duang1996 on 2017/12/23.
 */

public class bmobDb  {
    private static User user;
    private int count = 0;

    // 单例模式
    private static bmobDb instance = new bmobDb();
    public static bmobDb getInstance() {
        return instance;
    }

    public bmobDb() {
        user = BmobUser.getCurrentUser(User.class);
    }

    /* 修改头像
     * @param 图片在本地的sdcard中的路径
     * 如：path = "sdcard/temp.jpg"
     */
    public void modifyAvatar(String path) {
        final BmobFile bmobFile = new BmobFile(new File(path));

        bmobFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    Log.d("Mydebug", "图片上传成功！");
                    user.setAvatar(bmobFile);
                    updateInfo();
                } else {
                    Log.d("Mydebug", "图片上传失败！");
                }
            }
            @Override
            public void onProgress(Integer value) {}
        });

    }

    /* 修改姓名
    * @param 姓名
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

    /*
    * 设置脏值
    * */
    public void modifyDirty(Boolean dirty) {
        user.setDirty(dirty);
        updateInfo();
    }

    /*
    * 修改学历
    * */
    public void modifyEducatedLevel(Integer i) {
        user.setEducatedLevel(i);
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

    /* 修改电话号吗
    * @param npw:新的电话号码
    */
    public void modifyTelnumber(String tel) {
        user.setMobilePhoneNumberVerified(false);
        user.setMobilePhoneNumber(tel);
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
    /*
    * 查询优秀教师
    * */
    public Observable<List<User>> getExcellentTeachers() {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereEqualTo("role",true);
        query.setLimit(12);
        return query.findObjectsObservable(User.class);
    }

    /*
    * 添加个人信息
    * */
    public void saveTeacherInformation(TeacherInformation teacherInformation) {
        teacherInformation.save();
       // updateInfo();
    }

    public void updateTeacherInformation(TeacherInformation teacherInformation, String sch, String exp, String saying, String pra) {
        Log.d("Obj",teacherInformation.toString());
        TeacherInformation t = new TeacherInformation(user);
        t.setExperience(exp);
        t.setSchool(sch);
        t.setTeachingSaying(saying);
        t.setPractice(pra);
        t.update(teacherInformation.getObjectId(),new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.d("MyDebug","修改老师信息成功");
                } else {
                    Log.d("MyDebug",e.getMessage().toString());
                }
            }
        });
    }

    public void modifyTeachInfoAtPractice(TeacherInformation teacherInfo, String practice) {
        TeacherInformation t = new TeacherInformation(teacherInfo);
        t.setPractice(practice);
        t.update(teacherInfo.getObjectId(),new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.d("MyDebug","修改老师信息成功");
                } else {
                    Log.d("MyDebug",e.getMessage().toString());
                }
            }
        });
    }
    public void modifyTeachInfoAtExp(TeacherInformation teacherInfo, String exp) {
        TeacherInformation t = new TeacherInformation(teacherInfo);
        t.setExperience(exp);
        t.update(teacherInfo.getObjectId(),new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.d("MyDebug","修改老师信息成功");
                } else {
                    Log.d("MyDebug",e.getMessage().toString());
                }
            }
        });
    }
    public void modifyTeachInfoAtSchool(TeacherInformation teacherInfo, String school) {
        TeacherInformation t = new TeacherInformation(teacherInfo);
        t.setSchool(school);
        t.update(teacherInfo.getObjectId(),new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.d("MyDebug","修改老师信息成功");
                } else {
                    Log.d("MyDebug",e.getMessage().toString());
                }
            }
        });
    }

    public void modifyTeachInfoAtSaying(TeacherInformation teacherInfo, String saying) {
        TeacherInformation t = new TeacherInformation(teacherInfo);
        t.setTeachingSaying(saying);
        t.update(teacherInfo.getObjectId(),new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.d("MyDebug","修改老师信息成功");
                } else {
                    Log.d("MyDebug",e.getMessage().toString());
                }
            }
        });
    }

    public Observable<List<TeacherInformation>> findSayingOfTeacherByUsername(String username) {
        BmobQuery<TeacherInformation> query = new BmobQuery<>();
        query.addWhereEqualTo("username", username);
        query.include("user");
        return query.findObjectsObservable(TeacherInformation.class);
    }


    /* 根据学生返回订单列表
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

    /*
     * 找出教师信息，根据满意率
     */

    public Observable<List<TeacherInformation>> findTeacherInformation() {
        BmobQuery<TeacherInformation> query = new BmobQuery<TeacherInformation>();
        query.addWhereGreaterThanOrEqualTo("satisfyingOfEffect", 70);
        query.order("-createdAt");
        query.include("user");
        return query.findObjectsObservable(TeacherInformation.class);
    }

    /*
    * 按用户名查找用户的TeacherInformation
    * */
    public Observable<List<TeacherInformation>> findTeacherInformationByUsername(String username) {
        BmobQuery<TeacherInformation> query = new BmobQuery<>();
        query.addWhereEqualTo("username", username);
        query.order("-createdAt");
        query.include("user");
        return query.findObjectsObservable(TeacherInformation.class);
    }
    /*
    * 根据学生返回订单
    * */
    public Observable<List<Order>> findOrderForStudent() {

        BmobQuery<Order> query = new BmobQuery<Order>();
        query.addWhereEqualTo("user", user);  // 查询当前用户的所有订单
        query.order("-createdAt");     //根据订单建立时间的倒序排列
        query.include("teacher");  // 希望在查询帖子信息的同时也把teacher的信息查询出来

        return query.findObjectsObservable(Order.class);
    }

    /*
     * 根据老师返回我的订单列表
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

    /*
    * 根据教师学历来筛选教师信息
    * @param 学历：0：小学 2 初中, 2 高中 3 大专 4 本科 5 硕士 6 博士
    * */

    public Observable<List<User>> findTeacherInfoByDiploma(int low, int high) {
        BmobQuery<User> query = new BmobQuery<>();
        query.addWhereGreaterThanOrEqualTo("educatedLevel", low);
        query.addWhereLessThanOrEqualTo("educatedLevel", high);
        query.setLimit(40);
        query.order("-createdAt");
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

    /*
    * 找出处于两个年级数之间的订单
    * */
    public Observable<List<Order>> findOrderByGradeInterval(int grade1, int grade2) {
        BmobQuery<Order> query = new BmobQuery<Order>();
        query.addWhereLessThanOrEqualTo("grade",grade2);
        query.addWhereGreaterThanOrEqualTo("grade",grade1);
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

    /* 发布订单
     * @param 参数是一个订单实例
     * 注意传入参数前需要为order的相关属性设置对应的值
     */
    public void publishOrder(Order order) {
        order.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    Log.d("Mydebug", "订单创建成功！");
                }else{
                    Log.d("Mydebug", "订单创建失败！");
                }
            }
        });
    }

    /* 老师接家教订单
     * @param 参数是一个订单实例
     * 注意传入参数前需要检查order的teacher属性是否为空
     * 然后接单后的一些后续操作需要在逻辑层完成
     */
    public void receiveOrder(Order order) {
        order.setTeacher(user);
        order.update(user.getObjectId(),new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.d("Mydebug", "接单成功");
                }else{
                    Log.d("Mydebug", "接单失败");
                }
            }
        });
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
