package com.zhq.exclusivememory.ui.activity.view;

import android.os.Bundle;

import com.jetradarmobile.snowfall.SnowfallView;
import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/26.
 */

public class SnowFallViewActivity extends BaseSimpleActivity {
    @BindView(R.id.snow_fall_view)
    SnowfallView mSnowFallView;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_snow_fall_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
