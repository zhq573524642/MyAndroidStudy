package com.zhq.exclusivememory.ui.activity.anim;


import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;
import com.zhq.exclusivememory.ui.activity.anim.view.CircleProgressView;

import butterknife.BindView;

public class PathMeasureAnimActivity extends BaseSimpleActivity implements View.OnClickListener {

    @BindView(R.id.ll_left_back)
    LinearLayout llLeftBack;
    @BindView(R.id.tv_center_title)
    TextView tvCenterTitle;
    @BindView(R.id.btn_progress_add)
    Button btnProgressAdd;
    @BindView(R.id.btn_progress_dec)
    Button btnProgressDec;
    @BindView(R.id.circle_progress_view)
    CircleProgressView circleProgressView;

    @Override
    protected void initData() {
        tvCenterTitle.setText("动画进阶—PathMeasure实现路径动画");
    }

    @Override
    protected void initView() {
        btnProgressAdd.setOnClickListener(this);
        btnProgressDec.setOnClickListener(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_path_measure_anim;
    }

    private int progress;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_progress_add:
                if (progress >= 314) {
                    progress = 0;
                } else {

                    progress = progress + 10;
                }
                circleProgressView.setProgress(progress);
                circleProgressView.invalidate();
                break;
            case R.id.btn_progress_dec:
                if (progress == 0) {
                    progress = 0;
                } else {
                    progress = progress - 10;
                }
                circleProgressView.setProgress(progress);
                circleProgressView.invalidate();
                break;

        }
    }
}
