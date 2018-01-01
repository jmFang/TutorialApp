package com.example.jiamoufang.tutorialapp.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.jiamoufang.tutorialapp.R;
import com.example.jiamoufang.tutorialapp.event.RefreshPostEvent;
import com.example.jiamoufang.tutorialapp.share.presenter.PublishPostPresenter;
import com.example.jiamoufang.tutorialapp.share.view.PostDetailsView;
import com.example.jiamoufang.tutorialapp.share.view.PublishPostView;
import com.example.jiamoufang.tutorialapp.ui.base.BaseActivity;
import com.example.jiamoufang.tutorialapp.ui.base.ParentWithNaviActivity;
import com.example.jiamoufang.tutorialapp.utils.BmobUtils;

public class PublishPostActivity extends ParentWithNaviActivity implements PublishPostView {

    @Bind(R.id.btn_publish)
    Button mBtnPublish;
    @Bind(R.id.edt_post_content)
    EditText mEdtPostContent;
    private PublishPostPresenter mPublishPostPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_post);
        ButterKnife.bind(this);
        mPublishPostPresenter = new PublishPostPresenter(this);
    }

    @Override
    protected String title() {
        return null;
    }

    @OnClick(R.id.btn_publish)
    public void onViewClicked() {
        String content = mEdtPostContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            BmobUtils.toast(this, "请输入帖子内容！");
            return;
        }
        mPublishPostPresenter.publishPost(content);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void hideDialog() {

    }

    @Override
    public void showError(Throwable throwable) {

        BmobUtils.toast(this, throwable.getMessage());
    }

    @Override
    public void publishSuccess() {
        EventBus.getDefault().post(new RefreshPostEvent());
        BmobUtils.toast(this, "发表帖子成功");
        finish();
    }

}
