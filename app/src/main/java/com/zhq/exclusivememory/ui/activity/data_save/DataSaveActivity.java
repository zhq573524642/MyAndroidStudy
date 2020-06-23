package com.zhq.exclusivememory.ui.activity.data_save;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;
import com.zhq.exclusivememory.ui.activity.data_save.database.DataBaseActivity;
import com.zhq.exclusivememory.ui.activity.data_save.file.FileSaveActivity;
import com.zhq.exclusivememory.ui.activity.data_save.share_preference.SPActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/19.
 */

public class DataSaveActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.btn_database)
    Button mBtnDatabase;
    @BindView(R.id.btn_file_save)
    Button mBtnFileSave;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
      mTvCenterTitle.setText("持久化技术");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_data_save;
    }


    @OnClick({R.id.ll_left_back,R.id.btn_database, R.id.btn_file_save,R.id.btn_share_preference})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_database:
                intent.setClass(DataSaveActivity.this, DataBaseActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_file_save:
                intent.setClass(DataSaveActivity.this, FileSaveActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_share_preference:
                intent.setClass(DataSaveActivity.this, SPActivity.class);
                startActivity(intent);
                break;
        }
    }
}
