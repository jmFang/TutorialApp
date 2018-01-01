package com.example.jiamoufang.tutorialapp.model;

import android.icu.lang.UScript;
import android.nfc.NfcEvent;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.jiamoufang.tutorialapp.model.bean.User;
import com.example.jiamoufang.tutorialapp.model.intface.QueryUserListener;
import com.example.jiamoufang.tutorialapp.model.intface.UpdateCacheListener;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import org.w3c.dom.Text;

import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DeleteBatchListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by jiamoufang on 2017/12/18.
 */

public class UserModel extends BaseModel {
    /*
    * 单例模式
    * 提供静态实例
    * */

    private static UserModel ourInstance = new UserModel();
    public static UserModel getInstance() {
        return ourInstance;
    }
    private UserModel(){}

    /* TODO 用户管理：1.1 登录
    * @param username
    * @param password
    * @param listener
    * */
    public void login(String username, String password, final LogInListener listener) {
        if (TextUtils.isEmpty(username)) {
            listener.done(null, new BmobException(CODE_NULL, "请填写用户名"));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            listener.done(null, new BmobException(CODE_NULL, "请填写密码"));
            return;
        }
        final User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    Toast.makeText(getContext(), "userId:"+ user.getObjectId(), Toast.LENGTH_SHORT).show();
                    listener.done(getCurrentUser(), null);
                } else {
                    listener.done(user,e);
                }
            }
        });
    }

    /*TODO 用户管理：1.2 获取当前用户
    * @return
    * */
    public User getCurrentUser() {
        return BmobUser.getCurrentUser(User.class);
    }

    /*TODO 用户管理： 1.3 注册
    * @param username
    * @param password
    * @param pwdagain
    * @param listener
    * */

    public void register(String username, String password, String pwdagain, final LogInListener listener) {
        if (TextUtils.isEmpty(username)) {
            listener.done(null, new BmobException(CODE_NULL, "请填写用户名"));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            listener.done(null, new BmobException(CODE_NULL, "请填写密码"));
            return;
        }
        if (TextUtils.isEmpty(pwdagain)) {
            listener.done(null, new BmobException(CODE_NULL, "请填写确认密码"));
            return;
        }
        if (!password.equals(pwdagain)) {
            listener.done(null, new BmobException(CODE_NULL, "两次密码输入不一致，请重新输入"));
            return;
        }
        final User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    listener.done(null, null);
                    Toast.makeText(getContext(), "userId:"+ user.getObjectId(), Toast.LENGTH_SHORT).show();
                } else {
                    listener.done(null, e);
                }
            }
        });
    }

    /*TODO 用户管理：1.4 登出
    * @return
    * */
    public void logout() {
        BmobUser.logOut();
    }

    /*TODO 用户管理：1.5 查询用户(所有用户)
    * @param username
    * @param limit
    * @param listener
    * */

    public void queryUsers(String username, final int limit, final FindListener<User> listener) {
        BmobQuery<User> query = new BmobQuery<>();
        //需要去掉当前用户
        try {
            BmobUser user = BmobUser.getCurrentUser();
            query.addWhereNotEqualTo("username",user.getUsername());
        }catch (Exception e) {
            e.printStackTrace();
        }

        query.addWhereEqualTo("username", username);
        query.setLimit(limit);
        /*按注册时间先后排序*/
        query.order("-createdAt");

        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    if (list != null && list.size() > 0) {
                        listener.done(list, e);
                    } else {
                        listener.done(list, new BmobException(CODE_NULL, "查无此人"));
                    }
                } else {
                    listener.done(null, e);
                }
            }
        });
    }

    /*TODO 用户管理：2.6 查询指定用户信息
    * @param objectId
    * @param listener
    * */
    public void queryUserInfo(String objectId, final QueryUserListener listener) {
        BmobQuery<User> query = new BmobQuery<>();

        query.addWhereEqualTo("objectId",objectId);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    if (list != null && list.size() > 0) {
                        listener.done(list.get(0), e);
                    } else {
                        listener.done(null, new BmobException(000,"查无此人"));
                    }
                } else {
                    listener.done(null,e);
                }
            }
        });
    }
    /*TODO 用户管理：2.6 查询指定用户信息
    * @param username
    * @param listener
    * */
    public void queryUser(String username, final QueryUserListener listener) {
        BmobQuery<User> query = new BmobQuery<>();

        query.addWhereEqualTo("username",username);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    if (list != null && list.size() > 0) {
                        listener.done(list.get(0), e);
                    } else {
                        listener.done(null, new BmobException(000,"查无此人"));
                    }
                } else {
                    listener.done(null,e);
                }
            }
        });
    }

    /*TODO 用户管理:2.7 更新用户资料和会话资料
    * @param event
    * @param listener
    * */

    public void updateUserInfo(MessageEvent event, final UpdateCacheListener listener) {
        final BmobIMConversation conversation = event.getConversation();
        final BmobIMUserInfo info = event.getFromUserInfo();
        final BmobIMMessage msg = event.getMessage();

        final String username = info.getName();
        final String avatar = info.getAvatar();//头像图片在Bmob云端的url路径
        String title = conversation.getConversationTitle();
        String icon = conversation.getConversationIcon();
        //SDK内部将新会话的会话标题用objectId表示，
        //因此需要比对用户名和私聊会话标题，后续会根据会话类型进行判断
        if (!username.equals(title) || (avatar != null && !avatar.equals(icon))) {
            UserModel.getInstance().queryUserInfo(info.getUserId(), new QueryUserListener() {
                @Override
                public void done(User user, BmobException e) {
                    if (e == null) {
                        String name = user.getUsername();
                        String avatar = user.getAvatar() == null ? null : user.getAvatar().getFileUrl();
                        conversation.setConversationIcon(avatar);
                        conversation.setConversationTitle(name);
                        info.setName(name);
                        info.setAvatar(avatar);
                        //更新用户资料，用于在聊天界面以及个人信息显示
                        BmobIM.getInstance().updateUserInfo(info);
                        //更新会话资料，如果是暂态消息则不用更新会话资料
                        if (msg.isTransient()) {
                            BmobIM.getInstance().updateConversation(conversation);
                        }
                    } else {
                        Logger.e(e);
                    }
                    listener.done(null);
                }
            });
        } else {
            listener.done(null);
        }
    }

}
