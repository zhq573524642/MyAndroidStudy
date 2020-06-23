package com.zhq.exclusivememory.ui.activity.view.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
 * on 2019/8/20.
 */

public class ViewFlipperActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.btn_watch_note)
    Button mBtnWatchNote;
    @BindView(R.id.btn_previous)
    Button mBtnPrevious;
    @BindView(R.id.btn_next)
    Button mBtnNext;
    @BindView(R.id.btn_auto)
    Button mBtnAuto;
    @BindView(R.id.tv_position)
    TextView mTvPosition;
    @BindView(R.id.adapter_view_flipper)
    AdapterViewFlipper mAdapterViewFlipper;
    private int[] images = {R.mipmap.ic1, R.mipmap.ic2, R.mipmap.ic3, R.mipmap.ic4, R.mipmap.ic5, R.mipmap.ic6,
            R.mipmap.ic7, R.mipmap.img1, R.mipmap.img4, R.mipmap.img5, R.mipmap.img6, R.mipmap.img7};


    @Override
    protected void initData() {
        mTvCenterTitle.setText("AdapterViewFlipper");
    }

    @Override
    protected void initView() {
        //设置是否自动切换
        mAdapterViewFlipper.setAutoStart(true);
        //设置自动切换的时间间隔
        mAdapterViewFlipper.setFlipInterval(3000);
        mAdapterViewFlipper.setAnimateFirstView(false);
        mAdapterViewFlipper.startFlipping();
        //进入的动画
//        ObjectAnimator inAnimator = ObjectAnimator.ofFloat(mAdapterViewFlipper, "TranslationX", 400.0f, -200.0f);
//        inAnimator.setDuration(5000);
//        inAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//        inAnimator.start();
        //退出的动画
//        ObjectAnimator outAnimator = ObjectAnimator.ofFloat(mAdapterViewFlipper, "TranslationX", 0.0f, -400.0f);
//        outAnimator.setDuration(2000);
//        outAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
//        outAnimator.start();
        //设置动画
//        mAdapterViewFlipper.setInAnimation(inAnimator);
//        mAdapterViewFlipper.setOutAnimation(outAnimator);
        //设置adapter
        ViewFlipperAdapter viewFlipperAdapter = new ViewFlipperAdapter();
        mAdapterViewFlipper.setAdapter(viewFlipperAdapter);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_view_flipper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.btn_watch_note, R.id.btn_previous, R.id.btn_next, R.id.btn_auto})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_watch_note:
                Intent intent = new Intent();
                intent.setClass(this, WebViewActivity.class);
                intent.putExtra(Constant.WEB_PAGE_URL, API.WIDGET_VIEW_FLIPPER);
                intent.putExtra(Constant.TITLE_NAME, "ViewFlipper");
                startActivity(intent);
                break;
            case R.id.btn_previous://上一张
                mAdapterViewFlipper.stopFlipping();
                mAdapterViewFlipper.showPrevious();
                break;
            case R.id.btn_next://下一张
                mAdapterViewFlipper.stopFlipping();
                mAdapterViewFlipper.showNext();
                break;
            case R.id.btn_auto://自动播放或停止播放
                if (mAdapterViewFlipper.isFlipping()) {
                    mAdapterViewFlipper.stopFlipping();
                } else {
                    mAdapterViewFlipper.startFlipping();
                }
                break;
        }
    }

    public class ViewFlipperAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return images[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_flipper, null);
                viewHolder = new ViewHolder();
                viewHolder.mImageView = convertView.findViewById(R.id.image_view);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            mTvPosition.setText("第" + (position + 1) + "张");
            viewHolder.mImageView.setImageResource(images[position]);
            return convertView;
        }

        class ViewHolder {
            ImageView mImageView;
        }
    }
}
