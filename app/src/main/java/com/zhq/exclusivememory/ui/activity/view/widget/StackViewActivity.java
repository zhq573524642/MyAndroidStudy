package com.zhq.exclusivememory.ui.activity.view.widget;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.StackView;
import android.widget.TextView;
import android.widget.Toast;

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
 * on 2019/8/20.
 */

public class StackViewActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.btn_watch_note)
    Button mBtnWatchNote;
    @BindView(R.id.btn_previous)
    Button mBtnPrevious;
    @BindView(R.id.btn_next)
    Button mBtnNext;
    @BindView(R.id.tv_position)
    TextView mTvPosition;
    @BindView(R.id.stack_view)
    StackView mStackView;
    private int[] images = {R.mipmap.ic1, R.mipmap.ic2, R.mipmap.ic3, R.mipmap.ic4, R.mipmap.ic5, R.mipmap.ic6,
            R.mipmap.ic7, R.mipmap.img1, R.mipmap.img4, R.mipmap.img5, R.mipmap.img6, R.mipmap.img7};

    @Override
    protected void initData() {
        mTvCenterTitle.setText("StackView");
    }

    @Override
    protected void initView() {
        StackViewAdapter stackViewAdapter = new StackViewAdapter();
        mStackView.setAdapter(stackViewAdapter);
        mStackView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(StackViewActivity.this, position+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_stack_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.btn_watch_note, R.id.btn_previous,R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn_watch_note:
                Intent intent = new Intent();
                intent.setClass(this, WebViewActivity.class);
                intent.putExtra(Constant.WEB_PAGE_URL, API.WIDGET_STACK_VIEW);
                intent.putExtra(Constant.TITLE_NAME,"StackView");
                startActivity(intent);
                break;
            case R.id.btn_previous:
                mStackView.showPrevious();
                break;
            case R.id.btn_next:
                mStackView.showNext();
                break;
        }
    }

    public class StackViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return images[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_flipper, null);
                viewHolder = new ViewHolder();
                viewHolder.mImageView = convertView.findViewById(R.id.image_view);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            mTvPosition.setText("第" + (position + 1) + "张");
            viewHolder.mImageView.setImageResource(images[position]);
            return convertView;
        }

        class ViewHolder {
            ImageView mImageView;
        }
    }
}
