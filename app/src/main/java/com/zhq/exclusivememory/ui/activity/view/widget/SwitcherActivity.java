package com.zhq.exclusivememory.ui.activity.view.widget;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

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
 * on 2019/8/20.
 */

public class SwitcherActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.btn_watch_note)
    Button mBtnWatchNote;
    @BindView(R.id.text_switcher)
    TextSwitcher mTextSwitcher;
    @BindView(R.id.image_switcher)
    ImageSwitcher mImageSwitcher;
    @BindView(R.id.btn_previous)
    Button mBtnPrevious;
    @BindView(R.id.btn_next)
    Button mBtnNext;
    @BindView(R.id.tv_position)
    TextView mTvPosition;
    private String[] text = {"哈哈哈", "啦啦啦", "嘿嘿嘿", "嘎嘎嘎", "呵呵呵", "嘻嘻嘻"};
    private TextView mTextView;
    private int times = 0;
    private int[] images = {R.mipmap.ic1, R.mipmap.ic2, R.mipmap.ic3, R.mipmap.ic4, R.mipmap.ic5, R.mipmap.ic6,
            R.mipmap.ic7, R.mipmap.img1, R.mipmap.img4, R.mipmap.img5, R.mipmap.img6, R.mipmap.img7};
    private int index = 0;

    @Override
    protected void initData() {
        mTvCenterTitle.setText("Switcher系列");
        mTextSwitcher.setText(text[0]);

        mTextSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextSwitcher.setText(text[times++ % text.length]);
            }
        });

        mImageSwitcher.setImageResource(images[0]);
        mImageSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageSwitcher.setImageResource(images[index++ % images.length]);
                mTvPosition.setText("第"+(index++ % images.length)+"张");
            }
        });
    }

    @Override
    protected void initView() {
        mTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(SwitcherActivity.this);
                textView.setTextColor(Color.RED);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                return textView;
            }
        });
        mImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(SwitcherActivity.this);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                return imageView;
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_switcher;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.btn_watch_note,R.id.btn_previous,R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_watch_note:
                Intent intent = new Intent();
                intent.setClass(this, WebViewActivity.class);
                intent.putExtra(Constant.WEB_PAGE_URL, API.WIDGET_SWITCHER);
                intent.putExtra(Constant.TITLE_NAME,"Switcher系列");
                startActivity(intent);
                break;
            case R.id.btn_previous:
                mTvPosition.setText("第"+(index++ % images.length)+"张");
                mImageSwitcher.showPrevious();                break;
            case R.id.btn_next:
                mTvPosition.setText("第"+(index++ % images.length)+"张");
                mImageSwitcher.showNext();
                break;
        }
    }
}
