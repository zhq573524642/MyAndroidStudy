package com.zhq.exclusivememory.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.zhq.exclusivememory.R;

/**
 * Created by Huiqiang Zhang
 * on 2019/5/25.
 */

public class LoadingImageView extends android.support.v7.widget.AppCompatImageView {
    private int mTop;

    public LoadingImageView(Context context) {
        this(context, null);
    }

    public LoadingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.mTop = top;
    }

    private int mImgIndex = 0;
    private static int mImgCount = 8;

    private void init() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 100, 0);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(1000);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                setTop(mTop - value);
            }
        });
        animator.start();

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                setImageDrawable(getResources().getDrawable(R.mipmap.ic_1));
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                mImgIndex++;
                switch (mImgIndex%mImgCount){
                    case 0:
                        setImageDrawable(getResources().getDrawable(R.mipmap.ic_1));
                        break;
                    case 1:
                        setImageDrawable(getResources().getDrawable(R.mipmap.ic_2));
                        break;
                    case 2:
                        setImageDrawable(getResources().getDrawable(R.mipmap.ic_3));
                        break;
                    case 3:
                        setImageDrawable(getResources().getDrawable(R.mipmap.ic_4));
                        break;
                    case 4:
                        setImageDrawable(getResources().getDrawable(R.mipmap.ic_5));
                        break;
                    case 5:
                        setImageDrawable(getResources().getDrawable(R.mipmap.ic_6));
                        break;
                    case 6:
                        setImageDrawable(getResources().getDrawable(R.mipmap.ic_7));
                        break;
                    case 7:
                        setImageDrawable(getResources().getDrawable(R.mipmap.ic_8));
                        break;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });
    }
}
