package com.zhq.exclusivememory.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import android.widget.TextView;

import com.zhq.exclusivememory.R;

import com.zhq.exclusivememory.base.BaseActivity;

import com.zhq.exclusivememory.ui.activity.anim.AnimationActivity;
import com.zhq.exclusivememory.ui.activity.custom_view.CustomViewActivity;
import com.zhq.exclusivememory.ui.activity.data_save.DataSaveActivity;
import com.zhq.exclusivememory.ui.activity.four_module.AndroidBasicActivity;
import com.zhq.exclusivememory.ui.activity.internet.InternetActivity;
import com.zhq.exclusivememory.ui.activity.java_basic.JavaBasicActivity;
import com.zhq.exclusivememory.ui.activity.main.adapter.MainListAdapter;
import com.zhq.exclusivememory.ui.activity.main.mvp.MainContract;
import com.zhq.exclusivememory.ui.activity.main.mvp.MainImp;
import com.zhq.exclusivememory.ui.activity.media.MultiMediaActivity;
import com.zhq.exclusivememory.ui.activity.third_party.ThirdPartActivity;
import com.zhq.exclusivememory.ui.activity.thread.MultiThreadActivity;
import com.zhq.exclusivememory.ui.activity.view.ViewAndViewGroupActivity;
import com.zhq.exclusivememory.ui.activity.view.ViewWidgetActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;



public class MainActivity extends BaseActivity<MainImp> implements MainContract.View,MainListAdapter.OnMainItemClickListener {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.rv_main_view)
    RecyclerView rvMainView;
    private List<String> list = new ArrayList<>();

    @Override
    protected void initView() {
        mLlLeftBack.setVisibility(View.GONE);
        mTvCenterTitle.setText("进阶之路");
        String[] data = getResources().getStringArray(R.array.main_list);
        list.clear();
        list.addAll(Arrays.asList(data));
        rvMainView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        MainListAdapter mainListAdapter = new MainListAdapter(getContext(), list);
        rvMainView.setAdapter(mainListAdapter);
        mainListAdapter.setOnMainItemClickListener(this);
    }

    @Override
    public void onMainItemClick(int position) {
        Intent intent = new Intent();
        switch (position) {
            case 0://java基础
                intent.setClass(this, JavaBasicActivity.class);
                startActivity(intent);
                break;
            case 1://Android基础
                intent.setClass(this, AndroidBasicActivity.class);
                startActivity(intent);
                break;
            case 2://View篇
                intent.setClass(this, ViewAndViewGroupActivity.class);
                startActivity(intent);
                break;
            case 3://自定义View篇
                intent.setClass(this, CustomViewActivity.class);
                startActivity(intent);
                break;
            case 4://布局控件篇
                intent.setClass(this, ViewWidgetActivity.class);
                startActivity(intent);
                break;
            case 5://网络篇
                intent.setClass(this, InternetActivity.class);
                startActivity(intent);
                break;
            case 6://动画篇
                intent.setClass(this, AnimationActivity.class);
                startActivity(intent);
                break;
            case 7://工具功能篇
//                intent.setClass(this, AndroidBasicActivity.class);
//                startActivity(intent);
                break;
            case 8://性能篇
                break;
            case 9://第三方篇
                intent.setClass(this, ThirdPartActivity.class);
                startActivity(intent);
                break;
            case 10://多线程篇
                intent.setClass(this, MultiThreadActivity.class);
                startActivity(intent);
                break;
            case 11://多媒体篇
                intent.setClass(this, MultiMediaActivity.class);
                startActivity(intent);
                break;
            case 12://持久化技术篇
                intent.setClass(this, DataSaveActivity.class);
                startActivity(intent);
                break;


        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

}