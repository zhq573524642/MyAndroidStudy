package com.zhq.exclusivememory.ui.activity.anim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

public class AnimationActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.rl_common_title)
    RelativeLayout mRlCommonTitle;
    @BindView(R.id.btn_tween_anim)
    Button mBtnTweenAnim;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTvCenterTitle.setText("动画");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_animation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.btn_tween_anim, R.id.btn_frame_anim, R.id.btn_value_animator, R.id.btn_object_animator, R.id.btn_path_anim})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_tween_anim:
                intent.setClass(AnimationActivity.this, TweenAnimationActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_frame_anim:
                break;
            case R.id.btn_value_animator:
                intent.setClass(AnimationActivity.this, PropertyAnimatorActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_object_animator:
                intent.setClass(AnimationActivity.this, AnimatorSetActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_path_anim:
                intent.setClass(getContext(), PathMeasureAnimActivity.class);
                startActivity(intent);
                break;


        }
    }
}
