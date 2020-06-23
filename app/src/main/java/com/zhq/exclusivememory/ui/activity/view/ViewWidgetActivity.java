package com.zhq.exclusivememory.ui.activity.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;
import com.zhq.exclusivememory.ui.activity.view.widget.EditTextActivity;
import com.zhq.exclusivememory.ui.activity.view.widget.ImageViewActivity;
import com.zhq.exclusivememory.ui.activity.view.widget.ManyWidgetActivity;
import com.zhq.exclusivememory.ui.activity.view.widget.StackViewActivity;
import com.zhq.exclusivememory.ui.activity.view.widget.SwitcherActivity;
import com.zhq.exclusivememory.ui.activity.view.widget.TextViewActivity;
import com.zhq.exclusivememory.ui.activity.view.widget.ViewFlipperActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2019/8/19.
 */

public class ViewWidgetActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.btn_text_view)
    Button mBtnTextView;
    @BindView(R.id.btn_image_view)
    Button mBtnImageView;

    @Override
    protected void initData() {
    mTvCenterTitle.setText("Android控件");
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_view_widget;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.btn_text_view,R.id.btn_edit_text, R.id.btn_image_view,R.id.btn_many_widget,R.id.btn_view_flipper,R.id.btn_stack_view,R.id.btn_switcher,R.id.btn_date_time})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_text_view:
                intent.setClass(this, TextViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_edit_text:
                intent.setClass(this, EditTextActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_image_view:
                intent.setClass(this, ImageViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_many_widget:
                intent.setClass(this, ManyWidgetActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_view_flipper:
                intent.setClass(this, ViewFlipperActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_stack_view:
                intent.setClass(this, StackViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_switcher:
                intent.setClass(this, SwitcherActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_date_time:
                break;
        }
    }
}
