package com.example.jiamoufang.tutorialapp.db.localDB.bean;

import com.example.jiamoufang.tutorialapp.model.bean.Information;
import com.example.jiamoufang.tutorialapp.model.bean.User;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by duang1996 on 2017/12/23.
 */

public class bmobDb  {
    private User user;

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
