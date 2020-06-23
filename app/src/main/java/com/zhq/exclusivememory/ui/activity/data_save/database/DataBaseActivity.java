package com.zhq.exclusivememory.ui.activity.data_save.database;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/19.
 */

public class DataBaseActivity extends BaseSimpleActivity {

    @BindView(R.id.btn_create_database)
    Button mBtnCreateDatabase;
    private MyDatabaseHelper mDatabaseHelper;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mDatabaseHelper = new MyDatabaseHelper(getApplicationContext(), "BookStore.db", null, 1);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_database;
    }


    /**
     * 创建数据库
     */
    private void createDatabase() {
        mDatabaseHelper.getWritableDatabase();
    }

    @OnClick({R.id.btn_create_database})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_create_database:
                createDatabase();
                break;
        }
    }
}
