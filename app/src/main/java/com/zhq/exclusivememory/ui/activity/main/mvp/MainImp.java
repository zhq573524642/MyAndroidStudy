package com.zhq.exclusivememory.ui.activity.main.mvp;

import com.zhq.exclusivememory.base.mvp.BasePresenter;

import javax.inject.Inject;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/19.
 */

public class MainImp extends BasePresenter<MainContract.View> implements MainContract.Presenter {
    @Inject
    public MainImp() {
    }
}
