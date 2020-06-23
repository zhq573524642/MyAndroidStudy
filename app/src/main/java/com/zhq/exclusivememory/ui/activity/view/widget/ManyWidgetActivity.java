package com.zhq.exclusivememory.ui.activity.view.widget;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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

public class ManyWidgetActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.toggle_button)
    ToggleButton mToggleButton;
    @BindView(R.id.check_box1)
    CheckBox mCheckBox1;
    @BindView(R.id.check_box2)
    CheckBox mCheckBox2;
    @BindView(R.id.rb_1)
    RadioButton mRb1;
    @BindView(R.id.rb_2)
    RadioButton mRb2;
    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;
    @BindView(R.id.seekBar1)
    SeekBar mSeekBar1;
    @BindView(R.id.seekBar2)
    SeekBar mSeekBar2;
    @BindView(R.id.quickContactBadge)
    QuickContactBadge mQuickContactBadge;
    @BindView(R.id.rating_bar)
    RatingBar mRatingBar;


    @Override
    protected void initData() {
        mTvCenterTitle.setText("更多控件");
    }

    @Override
    protected void initView() {
        mToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(ManyWidgetActivity.this, "1", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ManyWidgetActivity.this, "2", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mCheckBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCheckBox2.setChecked(!mCheckBox2.isChecked());
                Toast.makeText(ManyWidgetActivity.this, isChecked ? mCheckBox1.getText().toString() : "没选中", Toast.LENGTH_SHORT).show();
            }
        });
        mCheckBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCheckBox1.setChecked(!mCheckBox1.isChecked());
                Toast.makeText(ManyWidgetActivity.this, isChecked ? mCheckBox2.getText().toString() : "没选中", Toast.LENGTH_SHORT).show();
            }
        });


        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == mRb1.getId()) {
                    Toast.makeText(ManyWidgetActivity.this, mRb1.getText().toString(), Toast.LENGTH_SHORT).show();
                } else if (checkedId == mRb2.getId()) {
                    Toast.makeText(ManyWidgetActivity.this, mRb2.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        mSeekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    seekBar.setProgress(progress, true);
                } else {
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(ManyWidgetActivity.this, "滑动之前", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(ManyWidgetActivity.this, seekBar.getProgress() + "", Toast.LENGTH_SHORT).show();
            }
        });

        mSeekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    seekBar.setProgress(progress, true);
                } else {
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(ManyWidgetActivity.this, "滑动之前", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(ManyWidgetActivity.this, seekBar.getProgress() + "", Toast.LENGTH_SHORT).show();
            }
        });
        mQuickContactBadge.assignContactFromPhone("17610676602", false);
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(ManyWidgetActivity.this, rating+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_many_widget;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back,R.id.btn_watch_note, R.id.check_box1, R.id.check_box2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_watch_note:
                Intent intent = new Intent();
                intent.setClass(this, WebViewActivity.class);
                intent.putExtra(Constant.WEB_PAGE_URL, API.WIDGET_OTHERS_VIEW);
                intent.putExtra(Constant.TITLE_NAME,"其他控件");
                startActivity(intent);
                break;
            case R.id.check_box1:
                break;
            case R.id.check_box2:
                break;

        }
    }
}
