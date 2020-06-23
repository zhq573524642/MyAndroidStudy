package com.zhq.exclusivememory.ui.activity.media;

import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Huiqiang Zhang
 * on 2019/6/16.
 */

public class VideoViewActivity extends BaseSimpleActivity {

    @BindView(R.id.video_view)
    VideoView mVideoView;
    private MediaController mMediaController;

    @Override
    protected void initData() {
        mMediaController = new MediaController(this);
        File file = new File("/mnt/sdcard/lala.mp4");
        if (file.exists()) {
            mVideoView.setVideoPath(file.getAbsolutePath());
            //videoView与MediaController关联
            mVideoView.setMediaController(mMediaController);
            //MediaController与VideoView关联
            mMediaController.setMediaPlayer(mVideoView);
            mVideoView.requestFocus();
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_video_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
