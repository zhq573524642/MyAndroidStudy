package com.zhq.exclusivememory.ui.activity.four_module.content_provide;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;
import com.zhq.exclusivememory.ui.activity.four_module.bean.ContactBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/21.
 */

public class ContentProviderActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.btn_get_contacts)
    Button mBtnGetContacts;
    @BindView(R.id.rv_contacts)
    RecyclerView mRvContacts;
    private List<ContactBean> mContactList=new ArrayList<>();
    private String image = "http://img5.imgtn.bdimg.com/it/u=639238630,2179659181&fm=26&gp=0.jpg";
    private ContactsAdapter mContactsAdapter;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTvCenterTitle.setText("内容提供者");
        mContactsAdapter = new ContactsAdapter(ContentProviderActivity.this,mContactList);
        mRvContacts.setLayoutManager(new LinearLayoutManager(this));
        mRvContacts.setAdapter(mContactsAdapter);
        mContactsAdapter.setOnContactCallListener(new ContactsAdapter.OnContactCallListener() {
            @Override
            public void onCall(int position) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mContactList.get(position).getPhone()));
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_content_provider;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.btn_get_contacts})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_get_contacts:
                getContacts();
                break;
        }
    }

    private void getContacts() {
        if (ContextCompat.checkSelfPermission(ContentProviderActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ContentProviderActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            readContacts();
        }
    }

    private void readContacts() {
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    ContactBean contactBean = new ContactBean();
                    contactBean.setPortrait(image);
                    contactBean.setName(displayName);
                    contactBean.setPhone(phone);
                    mContactList.add(contactBean);
                }

                mContactsAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                }else {
                    Toast.makeText(this, "未获取权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
