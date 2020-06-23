package com.zhq.exclusivememory.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zhq.exclusivememory.base.mvp.BaseContract;
import com.zhq.exclusivememory.di.component.ActivityComponent;
import com.zhq.exclusivememory.di.component.DaggerActivityComponent;
import com.zhq.exclusivememory.di.module.ActivityModule;

import com.zhq.exclusivememory.utils.ActivityCollector;
import com.zhq.exclusivememory.utils.ProgressDialogUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/18.
 */

public abstract class BaseActivity<T extends BaseContract.BasePresenter> extends RxAppCompatActivity implements BaseContract.BaseView {
    @Nullable
    @Inject
    protected T mPresenter;
    private Unbinder mUnbinder;
    protected BaseActivity mActivity;
    protected ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initActivityComponent();
        mActivity = this;
        mUnbinder = ButterKnife.bind(this);
        ActivityCollector.getInstance().addActivity(this);
        initInjector();
        attachView();
        initData();
        initView();
    }

    public Context getContext() {
        return mActivity;
    }


    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    private void initActivityComponent() {
        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(((APP) getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        ActivityCollector.getInstance().removeActivity(this);
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return this.bindToLifecycle();
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract int getLayoutId();

    protected abstract void initInjector();

    @Override
    public void showLoading() {
        ProgressDialogUtils.showDialog(mActivity);
    }

    @Override
    public void hideLoading() {
        ProgressDialogUtils.cancelDialog();
    }
}
