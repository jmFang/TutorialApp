package com.example.jiamoufang.tutorialapp.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.db.localDB.bean.bmobDb;
import com.example.jiamoufang.tutorialapp.factory.ImageLoaderFactory;
import com.example.jiamoufang.tutorialapp.model.UserModel;
import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.example.jiamoufang.tutorialapp.ui.base.ParentWithNaviActivity;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class CurrentUserInfoSettingActivity extends ParentWithNaviActivity{

    /*
    * user @Bind to get components here
    * and bind them in method onCreate()
    * */
   /* @Bind()*/
    @Bind(R.id.ll_my_avatar)
    ConstraintLayout ll_my_avatar;
    @Bind(R.id.ll_my_nick)
    ConstraintLayout ll_my_nick;
    @Bind(R.id.ll_my_role)
    ConstraintLayout ll_my_role;
    @Bind(R.id.ll_my_sex)
    ConstraintLayout ll_my_sex;
    @Bind(R.id.ll_my_city)
    ConstraintLayout ll_my_city;
    @Bind(R.id.ll_my_address)
    ConstraintLayout ll_my_address;
    @Bind(R.id.ll_my_phone)
    ConstraintLayout ll_my_phone;
    @Bind(R.id.ll_my_weChat)
    ConstraintLayout ll_my_weChat;
    @Bind(R.id.ll_my_password)
    ConstraintLayout ll_my_password;
    //退出当前用户
    @Bind(R.id.bt_logout)
    Button bt_logout;
    //用户头像
    @Bind(R.id.img_my_avatar)
    ImageView my_avatar;
    //用户昵称显示
    @Bind(R.id.tv_my_nickname)
    TextView my_nickname;
    //用户角色显示
    @Bind(R.id.tv_my_role)
    TextView my_role;
    //用户性别显示
    @Bind(R.id.tv_my_sex)
    TextView my_sex;
    //用户所在城市显示
    @Bind(R.id.tv_my_city)
    TextView my_city;
    //用户地址显示
    @Bind(R.id.tv_my_address)
    TextView my_address;
    //用户电话显示
    @Bind(R.id.tv_my_phone)
    TextView my_phone;
    //用户上传头像本地路径
    private String changed_avatar_path;
    //用户更改后的密码
    private String changed_password;
    /*用户角色
    * 更改身份时用于判断
    * 老师 : 0
    * 学生 : 1
    * */
    private int currentRole;
    /*用户性别
    * 更改性别时用于判断
    * 男 : 0
    * 女 : 1
    * */
    private int currentSex;
    //用户拍摄的图片
    private File tempFile;
    //各种更改功能的AlertDialog
    private AlertDialog.Builder uploadDialog, inputDialog, roleDialog, sexDialog;
    //嵌入AlertDialog的输入框
    private EditText changedInfo;

    //当前用户
    private User currentUser;

    //数据库Instance
    private bmobDb db = new bmobDb();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_user_info_setting);
        //初始化导航栏
        initNaviView();

        /*
        * get the latest inforation of the currentUser
        * */
        currentUser = BmobUser.getCurrentUser(User.class);
        if (currentUser.getAvatar() != null) {
            //测试过URL是正确的
            Glide.with(this).load(currentUser.getAvatar().getUrl()).into(my_avatar);

            // ImageLoaderFactory.getLoader().loadAvatar(, currentUser.getAvatar().getUrl(), R.mipmap.default_ss);
        } else {
            Glide.with(this).load(R.mipmap.default_ss).into(my_avatar);
        }
        /*
        * initialize user info
        * */
        initUserInfo();
        setUp();
    }

    /*
     * 重加载页面及时更新UI
     */
    @Override
    protected void onResume() {
        super.onResume();
        initUserInfo();
    }

    /*
    * initialize user info
    * @by fangjiamou
    * */
    private void initUserInfo() {
        if (currentUser.getRealName() != null)
            my_nickname.setText(currentUser.getRealName());
        if (currentUser.getRole() != null) {
            if (currentUser.getRole())
                my_role.setText("老师");
            else
                my_role.setText("学生");
        }
        if (currentUser.getSex() != null) {
            if (currentUser.getSex())
                my_sex.setText("男");
            else
                my_sex.setText("女");
        }
        if (currentUser.getCity() != null)
            my_city.setText(currentUser.getCity());
        if (currentUser.getAddress() != null)
            my_address.setText(currentUser.getAddress());
        if (currentUser.getMobilePhoneNumber() != null)
            my_phone.setText(currentUser.getMobilePhoneNumber());
    }

    private void setUp() {
        setUpUploadDialog();
        setUpInputDialog();
    }

    private void setUpUploadDialog() {
        uploadDialog = new AlertDialog.Builder(this);
        uploadDialog.setTitle("上传头像");
        final String[] choices = {"拍摄", "从相册选择"};
        uploadDialog.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    //先动态申请权限，申请成功在拍照
                    dynamicPermissionRequest();
                }

                else
                    pickPhoto();
            }
        });
        uploadDialog.setPositiveButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {}
                }).create();
    }

    private void setUpInputDialog() {
        changedInfo = new EditText(this);
        inputDialog = new AlertDialog.Builder(this);
        inputDialog.setTitle("请输入")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });
    }

    /*
    * 拍摄获取图片
    * */
    private void takeCamera() {
        Intent intent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            tempFile = new File(
                    Environment.getExternalStorageDirectory(),
                    "temp_photo" + System.currentTimeMillis() + ".jpg");
            Uri uri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, 1);
        } else {
            Toast.makeText(CurrentUserInfoSettingActivity.this,
                    "SDCard is unavailable",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void dynamicPermissionRequest() {
        if(Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePerssion = ContextCompat.checkSelfPermission(CurrentUserInfoSettingActivity.this, Manifest.permission.CAMERA);
            int checkCallPhonePerssion1 = ContextCompat.checkSelfPermission(CurrentUserInfoSettingActivity.this, Manifest.permission.READ_PHONE_STATE);
            if (checkCallPhonePerssion != PackageManager.PERMISSION_GRANTED || checkCallPhonePerssion1 != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CurrentUserInfoSettingActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE}, 222);
            } else {
                takeCamera();
            }
        } else {
            takeCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 222:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    takeCamera();
                } else {
                    toast("拍照权限被禁用，无法拍照");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode,permissions,grantResults);
                break;
        }
    }

    /*
        * 从相册中获取图片
        * */
    private void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (data != null) {
                Uri uri = data.getData();
                String[] filePathColumns = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(uri, filePathColumns, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePathColumns[0]);
                changed_avatar_path = c.getString(columnIndex);
                Bitmap pic = BitmapFactory.decodeFile(changed_avatar_path);
                my_avatar.setImageBitmap(pic);
            }
        } else if (requestCode == 1) {
            Uri uri = Uri.fromFile(tempFile);
            Bitmap pic = BitmapFactory.decodeFile(uri.getPath());
            my_avatar.setImageBitmap(pic);
            changed_avatar_path = tempFile.getPath();
        }
        db.modifyAvatar(changed_avatar_path);
        //BmobFile bmobFile = new BmobFile(new File(changed_avatar_path));
        //currentUser.setAvatar(bmobFile);
    }
    /*
    * handlers for click events
    * @by fangjiamou
    * */
    @OnClick({R.id.ll_my_avatar, R.id.ll_my_nick,R.id.ll_my_role,R.id.ll_my_sex,R.id.ll_my_city,R.id.ll_my_address,
            R.id.ll_my_phone,R.id.ll_my_weChat,R.id.ll_my_password,R.id.bt_logout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_my_avatar:
                handlerForChangeAvatar();
                break;
            case R.id.ll_my_nick:
                handlerForChangeNickname();
                break;
            case R.id.ll_my_role:
                handlerForChangeRole();
                break;
            case R.id.ll_my_sex:
                handlerForChangeSex();
                break;
            case R.id.ll_my_city:
                handlerForChangeCity();
                break;
            case R.id.ll_my_address:
                handlerForChangeAddress();
                break;
            case R.id.ll_my_phone:
                handlerForChangeMyPhone();
                break;
            case R.id.ll_my_weChat:
                handlerForChangeMyWeChat();
                break;
            case R.id.ll_my_password:
                handlerForChangeMyPassword();
                break;
            case R.id.bt_logout:
                handlerForLogout();
                break;
            default:
                break;
        }
    }

    /*
    * TODO Click 1.10 :click R.id.bt_logout
    * attention!!!
    * you should finish all the running activities before you logout
    * when you logout, just jump to the LogActivity(登录界面)
    * @fangjiamou
    * */
    private void handlerForLogout() {
        UserModel.getInstance().logout();
        BmobIM.getInstance().disConnect();
        Intent intent = new Intent(CurrentUserInfoSettingActivity.this, LogActivity.class);
        startActivity(intent);
        finish();
    }

    /*
    * TODO Click 1.9 :click R.id.ll_my_password
    * tips: refers to TODO Click 1.1:click R.id.ll_my_nick
    * */
    private void handlerForChangeMyPassword() {
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (changedInfo.getText().toString().trim().equals(""))
                            Toast.makeText(CurrentUserInfoSettingActivity.this,
                                    "密码不能为空",
                                    Toast.LENGTH_SHORT).show();
                        else {
                            changed_password = changedInfo.getText().toString().trim();
                            db.modifyPassword(changed_password.trim());
                            //currentUser.setPassword(changed_password);
                        }
                    }
                });
        ViewGroup p = (ViewGroup) changedInfo.getParent();
        if (p != null)
            p.removeAllViewsInLayout();
        changedInfo.setText("");
        inputDialog.setView(changedInfo).create().show();
    }
    /*
    * TODO Click 1.8 :click R.id.ll_my_weChat
    * tips: refers to TODO Click 1.1:click R.id.ll_my_nick
    * */
    private void handlerForChangeMyWeChat() {
    }
    /*
    * TODO Click 1.7 :click R.id.ll_my_phone
    * tips: refers to TODO Click 1.1:click R.id.ll_my_nick
    * */
    private void handlerForChangeMyPhone() {
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        my_phone.setText(changedInfo.getText().toString().trim());
                        db.modifyTelnumber(my_phone.getText().toString().trim());
                        //currentUser.setMobilePhoneNumber(changedInfo.getText().toString());
                    }
                });
        ViewGroup p = (ViewGroup) changedInfo.getParent();
        if (p != null)
            p.removeAllViewsInLayout();
        changedInfo.setText(my_phone.getText().toString());
        inputDialog.setView(changedInfo).create().show();
    }
    /*
    * TODO Click 1.6 :click R.id.ll_my_address
    * tips: refers to TODO Click 1.1:click R.id.ll_my_nick
    * */
    private void handlerForChangeAddress() {
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        my_address.setText(changedInfo.getText().toString().trim());
                        db.modifyAddress(my_address.getText().toString().trim());
                        //currentUser.setAddress(changedInfo.getText().toString());
                    }
                });
        ViewGroup p = (ViewGroup) changedInfo.getParent();
        if (p != null)
            p.removeAllViewsInLayout();
        changedInfo.setText(my_address.getText().toString());
        inputDialog.setView(changedInfo).create().show();
    }
    /*
    * TODO Click 1.5 :click R.id.ll_my_city
    * tips: refers to TODO Click 1.1:click R.id.ll_my_nick
    * */
    private void handlerForChangeCity() {
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        my_city.setText(changedInfo.getText().toString().trim());
                        db.modifyCity(my_city.getText().toString().trim());
                        //currentUser.setCity(changedInfo.getText().toString());
                    }
                });
        ViewGroup p = (ViewGroup) changedInfo.getParent();
        if (p != null)
            p.removeAllViewsInLayout();
        changedInfo.setText(my_city.getText().toString());
        inputDialog.setView(changedInfo).create().show();
    }

    /*
    * TODO Click 1.4 :click R.id.ll_my_sex
    * tips: refers to TODO Click 1.1:click R.id.ll_my_nick
    * */
    private void handlerForChangeSex() {
        sexDialog = new AlertDialog.Builder(this);
        String str = my_sex.getText().toString();
        if (str.equals("女"))
            currentSex = 1;
        else
            currentSex = 0;
        sexDialog.setTitle("您的性别")
                .setSingleChoiceItems(
                        new String[] { "男", "女" }, currentSex,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                currentSex = which;
                            }
                        })
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if (currentSex == 0) {
                                    my_sex.setText("男");
                                    db.modifySex(true);
                                    //currentUser.setSex(true);
                                } else {
                                    my_sex.setText("女");
                                    db.modifySex(false);
                                    //currentUser.setSex(false);
                                }
                            }
                        })
                .setNegativeButton("取消", null).create().show();
    }

    /*
    * TODO Click 1.3: click R.id.ll_my_role
    * tips: refers to TODO Click 1.1:click R.id.ll_my_nick
    * */
    private void handlerForChangeRole() {
        roleDialog = new AlertDialog.Builder(this);
        String str = my_role.getText().toString();
        if (str.equals("学生"))
            currentRole = 1;
        else
            currentRole = 0;
        roleDialog.setTitle("您的身份")
                .setSingleChoiceItems(
                        new String[] { "老师", "学生" }, currentRole,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                currentRole = which;
                            }
                        })
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if (currentRole == 0) {
                                    my_role.setText("老师");
                                    db.modifyRole(true);
                                    //currentUser.setRole(true);
                                } else {
                                    my_role.setText("学生");
                                    db.modifyRole(false);
                                    //currentUser.setRole(false);
                                }
                            }
                        })
                .setNegativeButton("取消", null).create().show();
    }

    /*
    * TODO Click 1.2:click R.id.ll_my_avatar:
    * tips: fetch picture from external storage or have a picture taken
    *       then replace the current picture
    *       @fangjiamou
    * */
    private void handlerForChangeAvatar() {
        uploadDialog.show();
    }
    /*
    * TODO Click 1.1:click R.id.ll_my_nick
    * tips: pop out a dialog to update the nickname
    *       or, make the TextView to EditText,but you should mind that before the user clicks the EditText
    *       it should be like a "TextView"
    * */
    private void handlerForChangeNickname() {
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        my_nickname.setText(changedInfo.getText().toString());
                        db.modifyNickname(my_nickname.getText().toString().trim());
                        //currentUser.setRealName(changedInfo.getText().toString());
                    }
                });
        ViewGroup p = (ViewGroup) changedInfo.getParent();
        if (p != null)
            p.removeAllViewsInLayout();
        changedInfo.setText(my_nickname.getText().toString());
        inputDialog.setView(changedInfo).create().show();
    }
    /*
    * set toolBar's title，here it should return null
    * @return null
    * */
    @Override
    protected String title() {
        return null;
    }
    /*
    * set click listener for left or right clickable components
    * @by fangjiamou
    * */
    @Override
    protected void setNaviListener(ToolBarListener listener) {
        super.setNaviListener(new ToolBarListener() {
            @Override
            public void clickLeft() {
                finish();
            }

            @Override
            public void clickRight() {
                //do nothing
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /*
    * tips: save all changes, and update those changes in cloud-server
    *       when the user click return, this method should be called.
    *       @fangjiamou
    * */
}
