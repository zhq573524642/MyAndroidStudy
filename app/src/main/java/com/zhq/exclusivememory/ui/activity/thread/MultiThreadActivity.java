package com.zhq.exclusivememory.ui.activity.thread;

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
 * on 2019/1/22.
 */

public class MultiThreadActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.btn_asyncTask)
    Button mBtnAsyncTask;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTvCenterTitle.setText("多线程");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_multi_thread;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.btn_asyncTask, R.id.btn_start_alarm, R.id.btn_stop_alarm})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_asyncTask:
                new MyAsyncTask(MultiThreadActivity.this).execute();
                break;
            case R.id.btn_start_alarm:
                intent.setClass(MultiThreadActivity.this, AlarmTestService.class);
                startService(intent);
                break;
            case R.id.btn_stop_service:
                intent.setClass(MultiThreadActivity.this, AlarmTestService.class);
                intent.putExtra("stopService", true);
                stopService(intent);
                break;
        }
    }
}
