package com.zhq.exclusivememory.ui.activity.anim;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2019/5/26.
 */

public class AnimatorSetActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.btn_open_menu)
    ImageView mBtnOpenMenu;
    @BindView(R.id.iv_item_1)
    ImageView mIvItem1;
    @BindView(R.id.iv_item_2)
    ImageView mIvItem2;
    @BindView(R.id.iv_item_3)
    ImageView mIvItem3;
    @BindView(R.id.iv_item_4)
    ImageView mIvItem4;
    @BindView(R.id.iv_item_5)
    ImageView mIvItem5;


    @Override
    protected void initData() {
        mTvCenterTitle.setText("动画组合");
    }

    @Override
    protected void initView() {
//        ValueAnimator valueAnimator = (ValueAnimator) AnimatorInflater.loadAnimator(this, R.animator.animator_xml);
//        valueAnimator.start();
//        ObjectAnimator objectAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.objectanimator_mxl);
//        objectAnimator.setTarget(mTvCenterTitle);
//        objectAnimator.start();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_animator_set;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.btn_open_menu, R.id.iv_item_1, R.id.iv_item_2, R.id.iv_item_3, R.id.iv_item_4, R.id.iv_item_5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left_back:
                break;
            case R.id.btn_open_menu:
                clickMenu(1);
                break;
            case R.id.iv_item_1:
                clickMenu(2);
                break;
            case R.id.iv_item_2:
                clickMenu(3);
                break;
            case R.id.iv_item_3:
                clickMenu(4);
                break;
            case R.id.iv_item_4:
                clickMenu(5);
                break;
            case R.id.iv_item_5:
                clickMenu(6);
                break;
        }
    }
    //点击执行以下方法
    private boolean mIsMenuOpen;//记录当前菜单是否打开
    private void clickMenu(int i) {
        if (!mIsMenuOpen) {
            if (i == 1) {
                Toast.makeText(this, "打开菜单", Toast.LENGTH_SHORT).show();
            }
            mIsMenuOpen = true;
            doOpenMenu();
        } else {
            switch (i) {
                case 1:
                    Toast.makeText(this, "关闭菜单", Toast.LENGTH_SHORT).show();

                    break;
                case 2:
                    Toast.makeText(this, "备忘录", Toast.LENGTH_SHORT).show();

                    break;
                case 3:
                    Toast.makeText(this, "天气预报", Toast.LENGTH_SHORT).show();

                    break;
                case 4:
                    Toast.makeText(this, "录音", Toast.LENGTH_SHORT).show();

                    break;
                case 5:
                    Toast.makeText(this, "日历", Toast.LENGTH_SHORT).show();

                    break;
                case 6:
                    Toast.makeText(this, "日记本", Toast.LENGTH_SHORT).show();

                    break;
            }
            mIsMenuOpen = false;
            doCloseMenu();
        }
    }

    private void doCloseMenu() {
        doAnimatorClose(mIvItem1, 0, 5, 300);
        doAnimatorClose(mIvItem2, 1, 5, 300);
        doAnimatorClose(mIvItem3, 2, 5, 300);
        doAnimatorClose(mIvItem4, 3, 5, 300);
        doAnimatorClose(mIvItem5, 4, 5, 300);
    }

    private void doAnimatorClose(ImageView view, int index, int total, int radius) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }

        double degree = Math.PI * index / ((total - 1) * 2);
        int translationX = -(int) (radius * Math.sin(degree));
        int translationY = -(int) (radius * Math.cos(degree));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", translationX, 0),
                ObjectAnimator.ofFloat(view, "translationY", translationY, 0),
                ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.1f),
                ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.1f),
                ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
        );
        animatorSet.setDuration(500).start();
    }

    private void doOpenMenu() {
        doAnimatorOpen(mIvItem1, 0, 5, 300);
        doAnimatorOpen(mIvItem2, 1, 5, 300);
        doAnimatorOpen(mIvItem3, 2, 5, 300);
        doAnimatorOpen(mIvItem4, 3, 5, 300);
        doAnimatorOpen(mIvItem5, 4, 5, 300);

    }

    /**
     *
     * @param view 执行动画的View
     * @param index view的索引
     * @param total view的总数
     * @param radius 半径
     */
    private void doAnimatorOpen(ImageView view, int index, int total, int radius) {

        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        //算出每个按钮之间的夹角
        double degree = Math.toRadians(90) / (total - 1) * index;
        //算数水平和竖直方向上的位移距离
        int translationX = -(int) (radius * Math.sin(degree));
        int translationY = -(int) (radius * Math.cos(degree));
        //创建动画组合AnimatorSet
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0, translationX),
                ObjectAnimator.ofFloat(view, "translationY", 0, translationY),
                ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f),
                ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f),
                ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
        );
        animatorSet.setDuration(500).start();
    }
}
