package com.zhq.exclusivememory.ui.activity.anim;

import android.animation.TimeInterpolator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2019/4/7.
 */

public class TweenAnimationActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.btn_scale)
    Button mBtnScale;
    @BindView(R.id.btn_alpha)
    Button mBtnAlpha;
    @BindView(R.id.btn_rotate)
    Button mBtnRotate;
    @BindView(R.id.btn_translate)
    Button mBtnTranslate;
    @BindView(R.id.btn_set)
    Button mBtnSet;
    @BindView(R.id.iv_anim_target)
    ImageView mIvAnimTarget;
    @BindView(R.id.circle1)
    ImageView mCircle1;
    @BindView(R.id.circle2)
    ImageView mCircle2;
    @BindView(R.id.circle3)
    ImageView mCircle3;
    @BindView(R.id.circle4)
    ImageView mCircle4;
    @BindView(R.id.iv_circle)
    ImageView mIvCircle;
    private Animation mAnimation1;
    private Animation mAnimation2;
    private Animation mAnimation3;
    private Animation mAnimation4;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mAnimation1 = AnimationUtils.loadAnimation(this, R.anim.set_circle_anim);
        mAnimation2 = AnimationUtils.loadAnimation(this, R.anim.set_circle_anim);
        mAnimation3 = AnimationUtils.loadAnimation(this, R.anim.set_circle_anim);
        mAnimation4 = AnimationUtils.loadAnimation(this, R.anim.set_circle_anim);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_tween_animation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.btn_scale, R.id.btn_alpha, R.id.btn_rotate, R.id.btn_translate, R.id.btn_set,R.id.iv_circle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left_back:
                break;
            case R.id.btn_scale:
                scaleAnimation();
                break;
            case R.id.btn_alpha:
                alphaAnimation();
                break;
            case R.id.btn_rotate:
                rotateAnimation();
                break;
            case R.id.btn_translate:
                translateAnimation();
                break;
            case R.id.btn_set:
                setAnimation();
                break;
            case R.id.iv_circle:
                startCircleAnimation();
                break;
        }
    }

    private void startCircleAnimation() {
        mCircle1.startAnimation(mAnimation1);
        mAnimation2.setStartOffset(600);
        mCircle2.startAnimation(mAnimation2);
        mAnimation3.setStartOffset(1200);
        mCircle3.startAnimation(mAnimation3);
        mAnimation4.setStartOffset(1800);
        mCircle4.startAnimation(mAnimation4);

    }

    private void setAnimation() {
        Animation animation = AnimationUtils.loadAnimation(TweenAnimationActivity.this, R.anim.set_anim);
        animation.setRepeatCount(Animation.INFINITE);
        mIvAnimTarget.startAnimation(animation);
    }

    private void translateAnimation() {
//        Animation animation = AnimationUtils.loadAnimation(TweenAnimationActivity.this, R.anim.translate_anim);
//        animation.setDuration(2000);
//        mIvAnimTarget.startAnimation(animation);
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 200, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 200);
        translateAnimation.setDuration(2000);
        translateAnimation.setFillAfter(true);
        translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        mIvAnimTarget.startAnimation(translateAnimation);


    }



    private void rotateAnimation() {
//        Animation animation = AnimationUtils.loadAnimation(TweenAnimationActivity.this, R.anim.rotate_anim);
//        animation.setDuration(2000);
//        mIvAnimTarget.startAnimation(animation);
        RotateAnimation rotateAnimation = new RotateAnimation(45.f, 270.f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(2000);
        rotateAnimation.setInterpolator(new BounceInterpolator());
        rotateAnimation.setRepeatCount(3);
        rotateAnimation.setRepeatMode(Animation.REVERSE);
        mIvAnimTarget.startAnimation(rotateAnimation);
    }

    private void alphaAnimation() {
//        Animation animation = AnimationUtils.loadAnimation(TweenAnimationActivity.this, R.anim.alpha_anim);
//        mIvAnimTarget.startAnimation(animation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 1.0f);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        alphaAnimation.setDuration(2000);
        alphaAnimation.setRepeatCount(2);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        mIvAnimTarget.startAnimation(alphaAnimation);
    }

    private void scaleAnimation() {
//        Animation animation = AnimationUtils.loadAnimation(TweenAnimationActivity.this, R.anim.scale_anim);
//        animation.setInterpolator(new BounceInterpolator());
//        mIvAnimTarget.startAnimation(animation);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 2.0f, 0.5f, 2.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(2000);
        scaleAnimation.setFillAfter(false);
        scaleAnimation.setInterpolator(new BounceInterpolator());
        scaleAnimation.setRepeatCount(Animation.INFINITE);
        scaleAnimation.setRepeatMode(Animation.REVERSE);
        mIvAnimTarget.startAnimation(scaleAnimation);
    }
}
