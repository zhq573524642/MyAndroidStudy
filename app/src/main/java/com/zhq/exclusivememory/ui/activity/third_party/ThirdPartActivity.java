package com.zhq.exclusivememory.ui.activity.third_party;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;
import com.zhq.exclusivememory.ui.activity.third_party.image_select.ImageSelectActivity;
import com.zhq.exclusivememory.ui.activity.third_party.map.MapActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2019/3/10.
 */

public class ThirdPartActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.btn_location)
    Button mBtnLocation;
    @BindView(R.id.btn_image_select)
    Button mBtnImageSelect;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTvCenterTitle.setText("第三方篇");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_third_party;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.btn_location,R.id.btn_image_select})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_location:
                intent.setClass(this, MapActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_image_select:
                intent.setClass(this, ImageSelectActivity.class);
                startActivity(intent);
                break;
        }
    }
}
