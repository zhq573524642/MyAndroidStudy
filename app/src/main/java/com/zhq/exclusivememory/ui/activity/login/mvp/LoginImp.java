package com.zhq.exclusivememory.ui.activity.login.mvp;

import com.zhq.exclusivememory.api.RxSchedulers;
import com.zhq.exclusivememory.base.mvp.BaseContract;
import com.zhq.exclusivememory.base.mvp.BasePresenter;
import com.zhq.exclusivememory.utils.RetrofitNetManager;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import retrofit2.Response;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/19.
 */

public class LoginImp extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    @Inject
    public LoginImp() {
    }


    @Override
    public void getSmsCode(String phone) {
        mView.showLoading();
        RetrofitNetManager.getApiService().sendSmsCodeForRegister(phone)
                .compose(mView.<Response<Object>>bindToLife())
                .compose(RxSchedulers.<Response<Object>>applySchedulers())
                .subscribe(new Consumer<Response<Object>>() {
                    @Override
                    public void accept(Response<Object> response) throws Exception {
                        if (response.isSuccessful()) {
                            mView.hideLoading();
                            mView.getSmsCodeSuccess(response.body());
                        } else {
                            mView.hideLoading();
                            mView.getSmsCodeFailed(response.code(), response.errorBody().string());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.hideLoading();
                        mView.getSmsCodeThrowable(throwable);
                    }
                });
    }
}
