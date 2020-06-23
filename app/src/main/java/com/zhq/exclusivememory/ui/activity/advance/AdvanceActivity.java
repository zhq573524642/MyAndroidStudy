package com.zhq.exclusivememory.ui.activity.advance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;
import com.zhq.exclusivememory.ui.activity.advance.rxjava.RxJavaStudyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2019/3/25.
 */

public class AdvanceActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.btn_rx_java)
    Button mBtnRxJava;

    @Override
    protected void initData() {
   mTvCenterTitle.setText("进阶篇");
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_advance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.btn_rx_java})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_rx_java:
                intent.setClass(AdvanceActivity.this, RxJavaStudyActivity.class);
                startActivity(intent);
                break;
        }
    }
}
