package com.zhq.exclusivememory.ui.activity.data_save.share_preference;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.api.API;
import com.zhq.exclusivememory.base.BaseSimpleActivity;
import com.zhq.exclusivememory.constant.Constant;
import com.zhq.exclusivememory.ui.activity.web_view.WebViewActivity;
import com.zhq.exclusivememory.utils.SpUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/21.
 */

public class SPActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.et_save_content)
    EditText mEtSaveContent;
    @BindView(R.id.tv_read_content)
    TextView mTvReadContent;
    @BindView(R.id.btn_save_content)
    Button mBtnSaveContent;
    @BindView(R.id.btn_read_content)
    Button mBtnReadContent;
    @BindView(R.id.btn_see_note)
    Button mBtnSeeNote;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTvCenterTitle.setText("SharePreference");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_share_preference;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.btn_save_content, R.id.btn_read_content, R.id.btn_see_note})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_save_content:
                String content = mEtSaveContent.getText().toString().trim();
                if (!TextUtils.isEmpty(content)) {

                    SpUtil.SetStringSF(SPActivity.this, "content", content);
                } else {
                    Toast.makeText(this, "请输入要保存的内容", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_read_content:
                String content1 = SpUtil.getStringSF(SPActivity.this, "content");
                if (!TextUtils.isEmpty(content1)) {
                    mTvReadContent.setText(content1);
                }
                break;
            case R.id.btn_see_note:
                Intent intent = new Intent(SPActivity.this, WebViewActivity.class);
                intent.putExtra(Constant.TITLE_NAME,"文件存储");
                intent.putExtra(Constant.WEB_PAGE_URL, API.FILE_SAVE);
                startActivity(intent);
                break;
        }
    }
}
