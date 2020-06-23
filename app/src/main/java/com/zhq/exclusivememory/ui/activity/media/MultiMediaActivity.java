package com.zhq.exclusivememory.ui.activity.media;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2019/5/31.
 */

public class MultiMediaActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.btn_media_player)
    Button mBtnMediaPlayer;
    @BindView(R.id.btn_sound_pool)
    Button mBtnSoundPool;
    @BindView(R.id.btn_video_view)
    Button mBtnVideoView;

    @Override
    protected void initData() {
        mTvCenterTitle.setText("多媒体");
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_multi_media;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.btn_media_player, R.id.btn_sound_pool, R.id.btn_video_view, R.id.btn_media_recorder,R.id.btn_take_photo})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_media_player:
                break;
            case R.id.btn_sound_pool:
                intent.setClass(this, SoundPoolActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_video_view:
                intent.setClass(this, VideoViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_media_recorder:
                intent.setClass(this, MediaRecorderActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_take_photo:
                intent.setClass(this,TakePhotoActivity.class);
                startActivity(intent);
                break;
        }
    }
}
