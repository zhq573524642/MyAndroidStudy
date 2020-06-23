package com.zhq.exclusivememory.ui.activity.view.widget;

import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.api.API;
import com.zhq.exclusivememory.base.BaseSimpleActivity;
import com.zhq.exclusivememory.constant.Constant;
import com.zhq.exclusivememory.ui.activity.web_view.WebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2019/8/19.
 */

public class ImageViewActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.btn_watch_note)
    Button mBtnWatchNote;
    @BindView(R.id.rb_1)
    RadioButton mRb1;
    @BindView(R.id.rb_2)
    RadioButton mRb2;
    @BindView(R.id.rb_3)
    RadioButton mRb3;
    @BindView(R.id.rb_4)
    RadioButton mRb4;
    @BindView(R.id.rg_1)
    RadioGroup mRg1;
    @BindView(R.id.rb_5)
    RadioButton mRb5;
    @BindView(R.id.rb_6)
    RadioButton mRb6;
    @BindView(R.id.rb_7)
    RadioButton mRb7;
    @BindView(R.id.rb_8)
    RadioButton mRb8;
    @BindView(R.id.rg_2)
    RadioGroup mRg2;
    @BindView(R.id.image_view)
    ImageView mImageView;

    @Override
    protected void initData() {
        mTvCenterTitle.setText("ImageView属性");
    }

    @Override
    protected void initView() {
        mRg1.setOnCheckedChangeListener(mListener1);
        mRg2.setOnCheckedChangeListener(mListener2);

    }

    private RadioGroup.OnCheckedChangeListener mListener1 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                mRg2.setOnCheckedChangeListener(null);
                mRg2.clearCheck();
                mRg2.setOnCheckedChangeListener(mListener2);
            }
            switch (checkedId) {
                case R.id.rb_1:
                    mImageView.setScaleType(ImageView.ScaleType.CENTER);
                    break;
                case R.id.rb_2:
                    mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    break;
                case R.id.rb_3:
                    mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    break;
                case R.id.rb_4:
                    mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    break;

            }
        }
    };

    private RadioGroup.OnCheckedChangeListener mListener2 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                mRg1.setOnCheckedChangeListener(null);
                mRg1.clearCheck();
                mRg1.setOnCheckedChangeListener(mListener1);
            }
            switch (checkedId) {
                case R.id.rb_5:

                    mImageView.setScaleType(ImageView.ScaleType.MATRIX);
                    Matrix matrix = new Matrix();
                    matrix.setTranslate(100,100);
                    matrix.preRotate(30);
                    mImageView.setImageMatrix(matrix);
                    break;
                case R.id.rb_6:
                    mImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    break;
                case R.id.rb_7:
                    mImageView.setScaleType(ImageView.ScaleType.FIT_START);
                    break;
                case R.id.rb_8:
                    mImageView.setScaleType(ImageView.ScaleType.FIT_END);
                    break;
            }
        }
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_image_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.btn_watch_note})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_watch_note:
                Intent intent = new Intent();
                intent.setClass(this, WebViewActivity.class);
                intent.putExtra(Constant.WEB_PAGE_URL, API.WIDGET_IMAGE_VIEW);
                intent.putExtra(Constant.TITLE_NAME,"ImageView属性");
                startActivity(intent);
                break;
        }
    }
}
