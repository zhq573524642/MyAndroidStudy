package com.zhq.exclusivememory.ui.activity.login.mvp;

import com.zhq.exclusivememory.base.mvp.BaseContract;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/19.
 */

public interface LoginContract {

    interface View extends BaseContract.BaseView {

        void getSmsCodeSuccess(Object object);

        void getSmsCodeFailed(int code, String error);

        void getSmsCodeThrowable(Throwable throwable);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getSmsCode(String phone);
    }
}
