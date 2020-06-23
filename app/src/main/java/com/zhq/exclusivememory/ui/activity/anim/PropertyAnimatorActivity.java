package com.zhq.exclusivememory.ui.activity.anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;
import com.zhq.exclusivememory.widget.FallingBallImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Huiqiang Zhang
 * on 2019/5/25.
 */

public class PropertyAnimatorActivity extends BaseSimpleActivity {
    @BindView(R.id.tv_color_change)
    TextView mTvColorChange;
    @BindView(R.id.btn_start_anim)
    Button mBtnStartAnim;
    @BindView(R.id.iv_circle)
    ImageView mIvCircle;
    @BindView(R.id.tv_object_animator)
    TextView mTvObjectAnimator;
    @BindView(R.id.btn_start_object_anim)
    Button mBtnStartObjectAnim;
    @BindView(R.id.iv_object)
    FallingBallImageView mIvObject;
    @BindView(R.id.btn_anim_set)
    Button mBtnAnimSet;
    @BindView(R.id.tv_boy)
    TextView mTvBoy;
    @BindView(R.id.tv_girl)
    TextView mTvGirl;
    private ValueAnimator mAnimator;

    @Override
    protected void initData() {
//        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100, 400);//ofInt传int类型值，ofFloat传float类型的值
//        valueAnimator.setInterpolator(new MyInterpolator());//设置插值器
//        valueAnimator.setDuration(3000);//设置动画时间
//        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);//设置动画重复次数
//        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);//设置动画重复模式
//        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int animatedValue = (int) animation.getAnimatedValue();
//                //动画的过程中的值，强转类型由前面ofInt或者ofFloat来定。
//            }
//        };
//        valueAnimator.setEvaluator(new IntEvaluator());//动画使用ofInt设置数值区间
//        valueAnimator.addUpdateListener(animatorUpdateListener);
//        //动画状态的监听
//        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        };
//        valueAnimator.addListener(animatorListener);
//        valueAnimator.start();//动画开始
//        valueAnimator.cancel();//动画取消
//        valueAnimator.removeUpdateListener(animatorUpdateListener);//移除某个AnimatorUpdateListener
//        valueAnimator.removeAllUpdateListeners();//移除全部AnimatorUpdateListener
//        valueAnimator.removeListener(animatorListener);//移除某个AnimatorListener
//        valueAnimator.removeAllListeners();//移除全部AnimatorListener
//
//        valueAnimator.setStartDelay(5000);//延时多长时间开始 单位毫秒
//        //完全克隆一个ValueAnimator实例，包括它所有设置以及所有对监听器代码的处理
//        valueAnimator.clone();

        mAnimator = ValueAnimator.ofInt(0xffffff00, 0xff0000ff);
        mAnimator.setEvaluator(new ArgbEvaluator());
        mAnimator.setDuration(3000);
        mAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mTvColorChange.setTextColor(value);
            }
        });
        mAnimator.start();

        final ValueAnimator animator = ValueAnimator.ofObject(new CharEvaluator(), 'A', 'Z');
        animator.setDuration(10000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Character value = (Character) animation.getAnimatedValue();
                mTvColorChange.setText(String.valueOf(value));
            }
        });
        animator.start();

        mBtnStartAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimator();
            }
        });

        /**
         * ObjectAnimator
         */

        mTvObjectAnimator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startObjectAnimator();
            }
        });
        final int[] ints = new int[2];
        mIvObject.getLocationOnScreen(ints);
        /**
         * ObjectAnimator 的抛物动画
         */
        mBtnStartObjectAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator1 = ObjectAnimator.ofObject(mIvObject, "FallingPoint", new FallingBallEvaluator(), new Point(0, 0), new Point(200, 200));
                animator1.setDuration(2000);
                animator1.start();
            }
        });

        /**
         * 属性动画集合
         */

        mBtnAnimSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimatorSet();
            }
        });

    }

    private void startAnimatorSet() {

        ObjectAnimator animator = ObjectAnimator.ofFloat(mTvBoy, "alpha", 0.2f, 0.5f, 1, 0.5f, 1);
        ObjectAnimator animator1 = ObjectAnimator.ofInt(mTvGirl, "BackgroundColor", Color.RED,Color.YELLOW,Color.BLUE);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mTvBoy, "translationY", 0,100,0,100,0);
        final AnimatorSet animatorSet = new AnimatorSet();
        //动画集合：一起播放和依次播放
        //animatorSet.playTogether(animator,animator1,animator2);
        //animatorSet.playTogether(animator,animator1,animator2);
        //动画集合：组合动画播放顺序
        //animator2-animator-animator1
        AnimatorSet.Builder builder = animatorSet.play(animator);
        builder.before(animator1);//animator1之前播放animator
        builder.after(animator2);//animator2之后播放animator
        //或者使用串行调用
        //AnimatorSet.Builder builder1 = animatorSet.play(animator).before(animator1).after(animator2);
        //设置时间和插值器都会覆盖各个动画的设置
        //动画集合中每个动画的执行时间
        animatorSet.setDuration(2000);
        //插值器
        animatorSet.setInterpolator(new AccelerateInterpolator());
        //设置目标控件
        animatorSet.setTarget(mTvGirl);

        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    private void startObjectAnimator() {
        /**
         * 参数1：操作的控件
         * 参数2：操作控件的属性
         * 参数3：可变长参数
         */
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTvObjectAnimator, "alpha", 1, 0.5f, 0, 0.5f, 1);
        animator.setDuration(2000);
        animator.setRepeatCount(5);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    /**
     * 抛物动画
     */
    private void startAnimator() {
        ValueAnimator animator = ValueAnimator.ofObject(new FallingBallEvaluator(), new Point(0, 0), new Point(200, 200));
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point value = (Point) animation.getAnimatedValue();
                mIvCircle.layout(value.x, value.y, value.x + mIvCircle.getWidth(), value.y + mIvCircle.getHeight());
            }
        });
        animator.start();
    }

    public class FallingBallEvaluator implements TypeEvaluator<Point> {

        private Point point = new Point();

        /**
         * 由于如果使用自由落体公式比较麻烦
         * 由于fraction是小数，所以这种乘以2
         * 在0~1时fraction*2的y的进度比x要快，
         * 所以y的进度先变为1，x还在继续
         *
         * @param fraction
         * @param startValue
         * @param endValue
         * @return
         */
        @Override
        public Point evaluate(float fraction, Point startValue, Point endValue) {
            point.x = (int) (startValue.x + fraction * (endValue.x - startValue.x));
            if (fraction * 2 <= 1) {
                point.y = (int) (startValue.y + fraction * 2 * (endValue.y - startValue.y));
            } else {
                point.y = endValue.y;
            }
            return point;
        }
    }

    public class CharEvaluator implements TypeEvaluator<Character> {

        @Override
        public Character evaluate(float fraction, Character startValue, Character endValue) {
            int startInt = (int) startValue;
            int endInt = (int) endValue;
            int curInt = (int) (startInt + (endInt - startInt) * fraction);
            char result = (char) curInt;
            return result;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAnimator.cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public class MyInterpolator implements TimeInterpolator {

        @Override
        public float getInterpolation(float input) {
            return 1 - input;
        }
    }

    //实现倒序输出
    public class MyEvaluator implements TypeEvaluator<Integer> {

        @Override
        public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
            int startInt = startValue;
            int endInt = endValue;
            return (int) (endInt - (endInt - startInt) * fraction);
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_property_animator;
    }
}
