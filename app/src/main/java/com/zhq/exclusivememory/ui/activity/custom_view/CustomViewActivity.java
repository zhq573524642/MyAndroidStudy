package com.zhq.exclusivememory.ui.activity.custom_view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.api.API;
import com.zhq.exclusivememory.base.BaseActivity;
import com.zhq.exclusivememory.constant.Constant;
import com.zhq.exclusivememory.ui.activity.web_view.WebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2019/5/2.
 */

public class CustomViewActivity extends BaseActivity {

    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.btn_canvas_base)
    Button mBtnCanvasBase;


    @Override
    protected void initView() {
        mTvCenterTitle.setText("自定义View学习");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void initData() {

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_view;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.btn_canvas_base})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_canvas_base:
                intent.setClass(this, WebViewActivity.class);
                intent.putExtra(Constant.WEB_PAGE_URL, API.VIEW_CANVAS_BASE);
                intent.putExtra(Constant.TITLE_NAME, "自定义View绘图基础");
                startActivity(intent);
                break;
        }
    }
}
