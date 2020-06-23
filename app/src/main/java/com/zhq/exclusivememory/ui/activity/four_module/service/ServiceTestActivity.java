package com.zhq.exclusivememory.ui.activity.four_module.service;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.api.API;
import com.zhq.exclusivememory.base.BaseSimpleActivity;
import com.zhq.exclusivememory.ui.activity.four_module.service.download.DownloadService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/22.
 */

public class ServiceTestActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.btn_start_service)
    Button mBtnStartService;
    @BindView(R.id.btn_stop_service)
    Button mBtnStopService;
    @BindView(R.id.btn_bind_service)
    Button mBtnBindService;
    @BindView(R.id.btn_unbind_service)
    Button mBtnUnbindService;
    @BindView(R.id.tv_number)
    TextView mTvNumber;
    private int LAUNCHER_WHICH_SERVICE = 0;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            switch (LAUNCHER_WHICH_SERVICE) {
//                case 1:
//                    break;
//                case 2:
//                    break;
//            }
//                    mService = ((MyService.MyBinder) service).getService();
                    mDownloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private int mProgress;
    private MyService mService;
    private DownloadService.DownloadBinder mDownloadBinder;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTvCenterTitle.setText("服务");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_service_test;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.btn_start_service, R.id.btn_stop_service, R.id.btn_start, R.id.btn_bind_service, R.id.btn_unbind_service, R.id.btn_start_download_service, R.id.btn_close_download_service, R.id.btn_start_download, R.id.btn_pause_download, R.id.btn_cancel_download})
    public void onViewClicked(View view) {

        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_start_service:
                intent.setClass(this, MyService.class);
                startService(intent);
                break;
            case R.id.btn_stop_service:
                intent.setClass(this, MyService.class);
                stopService(intent);
                break;
            case R.id.btn_bind_service:
                intent.setClass(this, MyService.class);
                bindService(intent, mConnection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind_service:
                unbindService(mConnection);
                break;
            case R.id.btn_start:
                String start = mService.start();
                Toast.makeText(ServiceTestActivity.this, start, Toast.LENGTH_SHORT).show();
                mService.startProgress();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (mProgress < MyService.MAX_PROGRESS) {
                            mProgress = mService.getProgress();
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Log.i("ServiceTestActivity", "====progress " + mProgress);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mTvNumber.setText(mProgress + "");
                                }
                            });
                        }
                    }
                }).start();
                break;
            case R.id.btn_start_download_service:
                intent.setClass(this, DownloadService.class);
                startService(intent);
                bindService(intent, mConnection, BIND_AUTO_CREATE);
                Toast.makeText(this, "下载服务开启成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_close_download_service:
                unbindService(mConnection);
                intent.setClass(this, DownloadService.class);
                stopService(intent);
                Toast.makeText(this, "下载服务关闭成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_start_download:
                requestDownload();
                break;
            case R.id.btn_pause_download:
                mDownloadBinder.pauseDownload();
                break;
            case R.id.btn_cancel_download:
                mDownloadBinder.cancelDownload();
                break;
        }
    }

    private void requestDownload() {
        if (ContextCompat.checkSelfPermission(ServiceTestActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ServiceTestActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        } else {
            mDownloadBinder.startDownload(API.DOWNLOAD_URL);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mDownloadBinder.startDownload(API.DOWNLOAD_URL);
                } else {
                    Toast.makeText(mService, "未获取权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
