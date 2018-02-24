package com.example.jiamoufang.tutorialapp.ui.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.db.localDB.bean.bmobDb;
import com.example.jiamoufang.tutorialapp.model.UserModel;
import com.example.jiamoufang.tutorialapp.model.bean.TeacherInformation;
import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.example.jiamoufang.tutorialapp.ui.base.ParentWithNaviActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import rx.functions.Action1;

public class TeacherDetailsActivity extends ParentWithNaviActivity {


    private User teacher;
    @Bind(R.id.effect_percent)
    TextView effect_percent;
    @Bind(R.id.serving_percent)
    TextView serving_percent;
    @Bind(R.id.teaching_XP)
    TextView teachingXP;
    @Bind(R.id.teaching_story)
    TextView teaching_story;
    @Bind(R.id.college)
    TextView college;
    @Bind(R.id.details_teacher_info) //学历
    TextView teacher_info;
    @Bind(R.id.details_teacher_name)
    TextView teacher_name;
    @Bind(R.id.details_teaching_age)
    TextView teacher_age;
    @Bind(R.id.details_teacher_photo)
    ImageView teacher_avatar;//头像
    @Bind(R.id.img_contact)
    ImageView img_contact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_details);
        ButterKnife.bind(this);
        initNaviView();
        setUp();
        fillMoreInfoAboutTeacher(teacher.getUsername());
    }
    /*
    * 根据用户名查询更多关于老师的信息
    * @param 用户名
    * */
    private void fillMoreInfoAboutTeacher(String username) {
        bmobDb.getInstance().findTeacherInformationByUsername(username).subscribe(new Action1<List<TeacherInformation>>() {
            @Override
            public void call(List<TeacherInformation> teacherInformations) {
                Log.d("TeacherDetailsActivity","line 65 fillMoreInfoAboutTeacher()");
                if (teacherInformations.size() != 1) {
                    Log.d("TeacherDetailsActivity","查询老师信息出错");
                } else {
                    effect_percent.setText(teacherInformations.get(0).getSatisfyingOfEffect().toString());
                    serving_percent.setText(teacherInformations.get(0).getSatisfyingOfService().toString());
                    if (teacherInformations.get(0).getExperience() != null) teachingXP.setText(teacherInformations.get(0).getExperience());
                    else if (TextUtils.isEmpty(teacherInformations.get(0).getExperience())) teachingXP.setText("未说明");
                    if (teacherInformations.get(0).getPractice() != null) teaching_story.setText(teacherInformations.get(0).getPractice());
                    else if (TextUtils.isEmpty(teacherInformations.get(0).getPractice()))teaching_story.setText("未说明");
                    if (teacherInformations.get(0).getSchool() != null) college.setText(teacherInformations.get(0).getSchool());
                    else if (TextUtils.isEmpty(teacherInformations.get(0).getSchool()))college.setText("未说明");
                    Log.d("TeacherDetailsActivity","初始化数据");
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.d("TeacherDetailsActivity","初始化数据出错");
            }
        });
    }

    private void setUp() {
        teacher = getTeacher();
        initializeViews();
    }

    private User getTeacher() {
        return (User) getIntent().getExtras().getSerializable("teacher");
    }

    private void initializeViews() {
        if (teacher.getRealName() != null)
            teacher_name.setText(teacher.getRealName());
        else teacher_name.setText(teacher.getUsername());
        //加载头像
        if (teacher.getAvatar() != null) {
                //Url需要保证是正确的
                Glide.with(this).load(teacher.getAvatar().getUrl()).into(teacher_avatar);
        } else {
                Glide.with(this).load(R.mipmap.default_smg).into(teacher_avatar);
        }
        if (teacher.getEducatedLevel() != null) {
            teacher_info.setText(educatedLevelToString(teacher.getEducatedLevel()));
        }

    }

    private String educatedLevelToString(Integer educatedLevel) {
        switch (educatedLevel) {
            case 0:return "小学";
            case 1:return "初中";
            case 2:return "高中";
            case 3:return "大专";
            case 4:return "本科";
            case 5:return "硕士";
            case 6:return "博士";
            default:return "未设置";
        }
    }

    @OnClick(R.id.img_contact)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_contact:
                String objectId = teacher.getObjectId();
                //启动通讯
                chat();
                break;
            default:
                break;

        }
    }

    /*
* 进入聊天界面，与之交流
* */
    private void chat() {
        /*检查IM服务器连接状况*/
        if (BmobIM.getInstance().getCurrentStatus().getCode() != ConnectionStatus.CONNECTED.getCode()) {
            /*尝试重新连接*/
            toast("IM服务器断开，重连中...");
            BmobIM.connect(UserModel.getInstance().getCurrentUser().getObjectId(), new ConnectListener() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        toast("重新连接成功");
                    } else {
                        toast("重连失败，请尝试重启应用");
                        return;
                    }
                }
            });
        }
        //会话所需要的用户信息
        BmobFile bmobFile = teacher.getAvatar();
        String fileUrl = null;
        String url = null;
        if (bmobFile != null) {
            try{
                fileUrl = bmobFile.getFileUrl();
                url = bmobFile.getUrl();
            }catch (Exception e) {
                toast("云端获取用户头像失败");
                e.printStackTrace();
            }
        }
        BmobIMUserInfo info = new BmobIMUserInfo(teacher.getObjectId(), teacher.getRealName()==null?teacher.getUsername():teacher.getRealName(),
                bmobFile == null ? null : url);
        /*
        * 连接状态良好，创建会话窗口，进入陌生人聊天
        * */
        BmobIMConversation conversationEntrance = BmobIM.getInstance().startPrivateConversation(info, null);
        Bundle bundle = new Bundle();
        /*
        * 串行化conversationEntrance对象，传给ChatActivity
        * */
        bundle.putSerializable("c",conversationEntrance);
        /*
        * 启动聊天界面，且不结束当前活动（可返回）
        * */
        startActivity(ChatActivity.class, bundle,false);
    }


    @Override
    protected String title() {
        return null;
    }

}
