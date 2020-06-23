package com.zhq.exclusivememory.ui.activity.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2019/5/9.
 */

public class SurfaceViewTestActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.lucky_view)
    LuckyPanView mLuckyView;
    @BindView(R.id.iv_start)
    ImageView mIvStart;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTvCenterTitle.setText("幸运转转转");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_surface_view_test;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.iv_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.iv_start:
                if (!mLuckyView.isStart()) {
                    Random random = new Random();
                    int i = random.nextInt(5);
                    mIvStart.setImageResource(R.mipmap.ic_stop);
                    mLuckyView.luckyStart(i);
                } else {
                    if(!mLuckyView.isShouldEnd()){
                        mIvStart.setImageResource(R.mipmap.ic_start);
                        mLuckyView.luckyEnd();
                    }
                }
                break;
        }
    }
}
