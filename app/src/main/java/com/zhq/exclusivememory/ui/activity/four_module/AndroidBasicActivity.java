package com.zhq.exclusivememory.ui.activity.four_module;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;
import com.zhq.exclusivememory.ui.activity.four_module.content_provide.ContentProviderActivity;
import com.zhq.exclusivememory.ui.activity.four_module.service.ServiceTestActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/21.
 */

public class AndroidBasicActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.btn_content_provider)
    Button mBtnContentProvider;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTvCenterTitle.setText("四大组件");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_four_module;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back,R.id.btn_service, R.id.btn_content_provider})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_service:
                intent.setClass(this,ServiceTestActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_content_provider:
                intent.setClass(this,ContentProviderActivity.class);
                startActivity(intent);
                break;
        }
    }
}
