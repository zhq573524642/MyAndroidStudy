package com.zhq.exclusivememory.ui.activity.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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
 * on 2019/1/31.
 */

public class ViewTouchActivity extends BaseSimpleActivity {
    @BindView(R.id.iv_image)
    ImageView mIvImage;
    @BindView(R.id.tv_text)
    TextView mTvText;
    @BindView(R.id.ll_parent_view)
    LinearLayout mLlParentView;
    @BindView(R.id.btn_get_param)
    Button mBtnGetParam;
    private int mLastX;
    private int mLastY;
    private static final String TAG = "ViewTouchActivity";
    private int mWidth;
    private int mHeight;
    private int mTXLastX;
    private int mTXLastY;

    @Override
    protected void initData() {

    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initView() {

        mIvImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int vAction = event.getAction();
                switch (vAction) {
                    case MotionEvent.ACTION_DOWN:
                        mLastX = (int) event.getRawX();
                        mLastY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int deltaX = (int) event.getRawX() - mLastX;
                        int deltaY = (int) event.getRawY() - mLastY;
                        int left = mIvImage.getLeft() + deltaX;
                        int top = mIvImage.getTop() + deltaY;
                        int right = mIvImage.getRight() + deltaX;
                        int bottom = mIvImage.getBottom() + deltaY;
//                        if (left < 0) {
//                            left = 0;
//                            right = left + mIvImage.getWidth();
//                        }
//                        if (right > mWidth) {
//                            right = mWidth;
//                            left = right - mIvImage.getWidth();
//                        }
//                        if (top < 0) {
//                            top = 0;
//                            bottom = top + mIvImage.getHeight();
//                        }
//                        if (bottom > mHeight) {
//                            bottom = mHeight;
//                            top = bottom - mIvImage.getHeight();
//                        }
                        mIvImage.layout(left, top, right, bottom);
                        mLastX = (int) event.getRawX();
                        mLastY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return true;
            }
        });

        mTvText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){

                }
                return false;
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_view_touch;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_get_param)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.btn_get_param:
                int[] location = new int[2];
                mIvImage.getLocationInWindow(location);
                int x = location[0];
                int y = location[1];
                Log.i(TAG, "====图片在Window中的位置 X: " + x + " Y:" + y);
                mIvImage.getLocationOnScreen(location);
                int x1 = location[0];
                int y1 = location[1];
                Log.i(TAG, "====图片在Screen上的位置 X: " + x1 + " Y: " + y1);
                mWidth = mLlParentView.getWidth();
                mHeight = mLlParentView.getHeight();
                Log.i(TAG, "====parentWidth " + mWidth);
                Log.i(TAG, "====parentHeight " + mHeight);
                break;
        }
    }
}
