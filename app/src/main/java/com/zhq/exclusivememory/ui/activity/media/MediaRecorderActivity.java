package com.zhq.exclusivememory.ui.activity.media;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2019/6/16.
 */

public class MediaRecorderActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.btn_start_recorder)
    Button mBtnStartRecorder;
    @BindView(R.id.btn_stop_recorder)
    Button mBtnStopRecorder;
    private MediaRecorder mMediaRecorder;
    private File mFile;

    @Override
    protected void initData() {

    }

    private void startRecorder() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "内存卡不存在", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            mFile = new File(Environment.getExternalStorageDirectory().getCanonicalFile() + "/recorder.amr");
            mMediaRecorder = new MediaRecorder();
            //设置录音的声音来源
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //设置录制声音的输出格式（必须在设置声音编码格式之前设置）
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            //设置声音编码的格式
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setOutputFile(mFile.getAbsolutePath());
            mMediaRecorder.prepare();
            mMediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_media_recorder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.btn_start_recorder, R.id.btn_stop_recorder})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_start_recorder:
                startRecorder();
                break;
            case R.id.btn_stop_recorder:
                stopRecorder();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (mFile != null && mFile.exists()) {
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
        super.onDestroy();
    }

    private void stopRecorder() {
        if (mFile != null && mFile.exists()) {
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }
}
