package com.zhq.exclusivememory.ui.activity.java_basic;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;
import com.zhq.exclusivememory.utils.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2020/3/4.
 */

public class JavaBasicActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private static final String TAG = "JavaBasicActivity";

    @Override
    protected void initData() {
        mTvCenterTitle.setText("Java基础");
    }

    private int[] nums = {0, 1, 0, 13, 12};
    private int[] nums1 = new int[]{};

    private List<Integer> list = new ArrayList<>();
    private int num = 0;

    @Override
    protected void initView() {
        LogUtil.i(TAG, "====给出的nums " + Arrays.toString(nums));
//        list.clear();
//        for (int i = nums.length - 1; i >= 0; i--) {
//            if (nums[i] == 0) {
//                list.add(0);
//            } else {
//                list.add(0, nums[i]);
//            }
//        }
//        Object[] array = list.toArray();
//        String s = Arrays.toString(array);
//        LogUtil.i(TAG, "====输出结果 " + s);

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[num] = nums[i];
                if (num != i) {
                    nums[i] = 0;
                }
                num++;
            }
        }
        LogUtil.i(TAG, "====输出结果11 " + Arrays.toString(nums));

//        int i = -1;
//        i >>>= 10;
//        LogUtil.i(TAG, "=====i= " + i);
//
//        long l = -1;
//        l >>>= 10;
//        LogUtil.i(TAG, "=====l= " + l);
//        short s = -1;
//        s >>>= 10;
//        LogUtil.i(TAG, "=====s= " + s);
//        byte b = -1;
//        b >>>= 10;
//        LogUtil.i(TAG, "=====b= " + b);

    }


    private int getMoreNum(int[] nums) {
        int[] num1 = new int[]{};
        int[] num2 = new int[]{};
        int count = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    count = count + 1;
                } else {
                    count = count - 1;
                }
            }
        }


        return 0;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_java_basic;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ll_left_back)
    public void onViewClicked() {
        finish();
    }
}
