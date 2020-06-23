package com.zhq.exclusivememory.ui.activity.view.widget;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class EditTextActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.btn_watch_note)
    Button mBtnWatchNote;
    @BindView(R.id.edit_text)
    EditText mEditText;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
     mTvCenterTitle.setText("EditText");
     mEditText.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after) {
             //EditText改变前
         }

         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count) {
             if(!TextUtils.isEmpty(s)){
                 //有输入
             }else {
                 //没有输入
             }
         }
         @Override
         public void afterTextChanged(Editable s) {
             //EditText改变后
         }
     });


    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_edit_text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.btn_watch_note})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_watch_note:
                Intent intent = new Intent();
                intent.setClass(this, WebViewActivity.class);
                intent.putExtra(Constant.WEB_PAGE_URL, API.WIDGET_EDIT_TEXT);
                intent.putExtra(Constant.TITLE_NAME,"EditText属性");
                startActivity(intent);
                break;
        }
    }
}
