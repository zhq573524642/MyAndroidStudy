package com.zhq.exclusivememory.ui.activity.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;
import com.zhq.exclusivememory.ui.activity.view.banner.BannerViewActivity;
import com.zhq.exclusivememory.ui.activity.view.notification.NotificationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/19.
 */

public class ViewAndViewGroupActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.btn_notification)
    Button mBtnNotification;
    @BindView(R.id.btn_banner)
    Button mBtnBanner;
    private static final String TAG = "ViewAndViewGroupActivity";

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTvCenterTitle.setText("View篇");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_view_part;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.btn_notification, R.id.btn_banner, R.id.btn_snow_fall_view, R.id.btn_spring_menu, R.id.btn_view_touch, R.id.btn_key_listener, R.id.btn_calendar, R.id.btn_view_viewGroup, R.id.btn_surface_view,R.id.btn_dan_mu_view,R.id.btn_scrolling})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_view_viewGroup:
                intent.setClass(this,ViewWidgetActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_notification:
                intent.setClass(ViewAndViewGroupActivity.this, NotificationActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_banner:
                intent.setClass(ViewAndViewGroupActivity.this, BannerViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_snow_fall_view:
                intent.setClass(ViewAndViewGroupActivity.this, SnowFallViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_spring_menu:
//                intent.setClass(ViewAndViewGroupActivity.this,SpringMenuActivity.class);
//                startActivity(intent);
                break;
            case R.id.btn_view_touch:
                intent.setClass(ViewAndViewGroupActivity.this, ViewTouchActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_key_listener:
                intent.setClass(ViewAndViewGroupActivity.this, KeyListenerActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_calendar:
                intent.setClass(ViewAndViewGroupActivity.this, Calendar2Activity.class);
                startActivity(intent);
                break;
            case R.id.btn_surface_view:
                intent.setClass(ViewAndViewGroupActivity.this, SurfaceViewTestActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_dan_mu_view:
                intent.setClass(ViewAndViewGroupActivity.this, DanMuViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_scrolling:
                intent.setClass(ViewAndViewGroupActivity.this,ScrollingActivity.class);
                startActivity(intent);
                break;


        }
    }
}
