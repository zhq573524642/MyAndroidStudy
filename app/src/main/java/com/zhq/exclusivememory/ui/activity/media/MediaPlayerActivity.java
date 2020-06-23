package com.zhq.exclusivememory.ui.activity.media;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;
import com.zhq.exclusivememory.utils.LogUtil;

import java.io.FileDescriptor;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2019/5/31.
 */

public class MediaPlayerActivity extends BaseSimpleActivity {
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.btn_start_res)
    Button mBtnStartRes;
    @BindView(R.id.btn_pause_res)
    Button mBtnPauseRes;
    @BindView(R.id.btn_stop_res)
    Button mBtnStopRes;
    private MediaPlayer mMediaPlayer;
    private static final String TAG = "MediaPlayerActivity";
    private MediaPlayer mMediaPlayer1;
    private MediaPlayer mMediaPlayer2;


    @Override
    protected void initData() {
        mTvCenterTitle.setText("MediaPlayer");
        //播放网络音频资源
        Uri uri = Uri.parse(url);
        mMediaPlayer1 = new MediaPlayer();
        try {
            mMediaPlayer1.setDataSource(this,uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                LogUtil.i(TAG, "=====音频准备");
            }
        });

        mMediaPlayer1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                LogUtil.i(TAG, "=====音频播放完成");
            }
        });

        mMediaPlayer1.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
                LogUtil.i(TAG, "=====设置音频进度");
            }
        });

        mMediaPlayer1.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                LogUtil.i(TAG, "=====音频播放错误");
                return false;
            }
        });

    }

    private void playResMusic() {
        //播放本地音频资源
        mMediaPlayer = MediaPlayer.create(this, R.raw.version);
        mMediaPlayer.start();
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                LogUtil.i(TAG, "=====音频准备");
            }
        });

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                LogUtil.i(TAG, "=====音频播放完成");
            }
        });

        mMediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
                LogUtil.i(TAG, "=====设置音频进度");
            }
        });

        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                LogUtil.i(TAG, "=====音频播放错误");
                return false;
            }
        });
    }

    private String url = "http://m801.music.126.net/20190531102623/dab8212f6988809d75d71fe7e94684b0/jdyyaac/520b/075a/5158/ea20c1a3e300c2b2dfb7f2c1d884551c.m4a";

    @Override
    protected void initView() {


        /**
         * 获取资源
         */
        AssetManager assets = getAssets();
        try {
            AssetFileDescriptor assetFileDescriptor = assets.openFd("version.mp3");
            FileDescriptor fileDescriptor = assetFileDescriptor.getFileDescriptor();
            long startOffset = assetFileDescriptor.getStartOffset();
            long length = assetFileDescriptor.getLength();
            mMediaPlayer2 = new MediaPlayer();
            mMediaPlayer2.setDataSource(fileDescriptor, startOffset, length);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_media_player;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        super.onDestroy();
    }

    @OnClick({R.id.ll_left_back, R.id.btn_start_res, R.id.btn_pause_res, R.id.btn_stop_res, R.id.btn_start_net, R.id.btn_pause_net, R.id.btn_stop_net, R.id.btn_start_data, R.id.btn_stop_data, R.id.btn_pause_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_start_res:
               playResMusic();
                break;
            case R.id.btn_pause_res:
                mMediaPlayer.pause();
                break;
            case R.id.btn_stop_res:
                mMediaPlayer.stop();
                break;
            case R.id.btn_start_net:
                try {
                    mMediaPlayer1.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mMediaPlayer1.start();
                break;
            case R.id.btn_pause_net:
                mMediaPlayer1.pause();
                break;
            case R.id.btn_stop_net:
                mMediaPlayer1.stop();
                break;
            case R.id.btn_start_data:
                try {
                    mMediaPlayer2.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mMediaPlayer2.start();
                break;
            case R.id.btn_stop_data:
                mMediaPlayer2.pause();
                break;
            case R.id.btn_pause_data:
                mMediaPlayer2.stop();
                break;
        }
    }
}
