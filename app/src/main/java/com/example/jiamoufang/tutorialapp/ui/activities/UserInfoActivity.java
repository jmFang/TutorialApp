package com.example.jiamoufang.tutorialapp.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.factory.ImageLoaderFactory;
import com.example.jiamoufang.tutorialapp.model.UserModel;
import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.example.jiamoufang.tutorialapp.ui.base.ParentWithNaviActivity;

import java.security.spec.ECField;

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

public class UserInfoActivity extends ParentWithNaviActivity {
    //头像
    @Bind(R.id.iv_avator)
    ImageView iv_avatar;
    //用户名
    @Bind(R.id.tv_name)
    TextView tv_name;
    //添加好友
    @Bind(R.id.btn_add_friend)
    Button btn_add_friend;
    //与之交流
    @Bind(R.id.btn_chat)
    Button btn_chat;
    /*
    * 以上控件与ID在BaseActivity绑定，此处可不需重复绑定
    * */

    //基本的用户实体
    User user;
    //会话所需要的用户信息
    BmobIMUserInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        //初始化导航栏：数据绑定和监听注册
        initNaviView();
        //从搜索界面传过来的用户
        user = (User) getBundle().getSerializable("u");
        //如果是当前用户本身
        if (user.getObjectId().equals(getCurrentUid())) {
            btn_add_friend.setVisibility(View.GONE);
            btn_chat.setVisibility(View.GONE);
        } else {
            btn_add_friend.setVisibility(View.VISIBLE);
            btn_chat.setVisibility(View.VISIBLE);
        }
        /*
        * 构造会话聊天需要的用户信息
        * @params: userId, username, avatar
        * */
        BmobFile bmobFile = user.getAvatar();
        String fileUrl = null;
        String url = null;
        /*如果用户当前头像不为null
        * 尝试到云端数据库获取图片
        * */
        if (bmobFile != null) {
            try{
                fileUrl = bmobFile.getFileUrl();
                url = bmobFile.getUrl();
            }catch (Exception e) {
                toast("云端获取用户头像失败");
                e.printStackTrace();
            }
        }
        /*
        * 构造用户会话信息
        * */
        info = new BmobIMUserInfo(user.getObjectId(), user.getUsername(),
                bmobFile == null ? null : (fileUrl == null) ? (url == null ? null:url):fileUrl );
        //加载头像
        if (bmobFile!= null) {
            if(fileUrl != null) {
                Glide.with(this).load(fileUrl).into(iv_avatar);
            } else {
                Glide.with(this).load(R.mipmap.default_smg).into(iv_avatar);
            }
        } else {
            Glide.with(this).load(R.mipmap.default_smg).into(iv_avatar);
        }
        //ImageLoaderFactory.getLoader().loadAvatar(iv_avatar, bmobFile == null ? null : (fileUrl == null) ? (url == null ? null:url):fileUrl, R.mipmap.default_ss);
        tv_name.setText(user.getUsername());
    }

    @Override
    protected String title() {
        return "求问";
    }

    @OnClick({R.id.btn_add_friend, R.id.btn_chat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add_friend:
                toast("未开放此功能");
                break;
            case R.id.btn_chat:
                chat();
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
}
