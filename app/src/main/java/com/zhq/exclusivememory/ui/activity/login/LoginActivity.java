package com.zhq.exclusivememory.ui.activity.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseActivity;
import com.zhq.exclusivememory.ui.activity.login.bean.GetSmsCodeBean;
import com.zhq.exclusivememory.ui.activity.login.mvp.LoginContract;
import com.zhq.exclusivememory.ui.activity.login.mvp.LoginImp;
import com.zhq.exclusivememory.utils.GsonTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/19.
 */

public class LoginActivity extends BaseActivity<LoginImp> implements LoginContract.View {


    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.btn_get_code)
    Button mBtnGetCode;
    @BindView(R.id.tv_receive_response)
    TextView mTvReceiveResponse;

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_get_code)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_get_code:
                String phone = mEtPhone.getText().toString().trim();
                mPresenter.getSmsCode(phone);
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initInjector() {
        mActivityComponent.inject(this);
    }

    @Override
    public void getSmsCodeSuccess(Object object) {
        GetSmsCodeBean getSmsCodeBean = GsonTools.changeGsonToBean(GsonTools.createGsonString(object), GetSmsCodeBean.class);
        mTvReceiveResponse.setText(getSmsCodeBean.toString());
    }

    @Override
    public void getSmsCodeFailed(int code, String error) {
        Toast.makeText(mActivity, code + "===== " + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getSmsCodeThrowable(Throwable throwable) {
        Toast.makeText(mActivity, "==="+throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}
