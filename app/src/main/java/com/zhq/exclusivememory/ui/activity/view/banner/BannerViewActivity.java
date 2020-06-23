package com.zhq.exclusivememory.ui.activity.view.banner;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;
import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;
import com.zhq.exclusivememory.utils.LogUtil;
import com.zhq.exclusivememory.utils.MyUtils;
import com.zhq.exclusivememory.utils.StatusBarUtils;
import com.zhq.exclusivememory.widget.CornerTransform;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/25.
 */

public class BannerViewActivity extends BaseSimpleActivity {
    //    @BindView(R.id.ll_left_back)
//    LinearLayout mLlLeftBack;
//    @BindView(R.id.tv_center_title)
//    TextView mTvCenterTitle;
    @BindView(R.id.banner_other)
    MZBannerView mBannerOther;
    private String[] images = {"http://img1.imgtn.bdimg.com/it/u=249425018,1155724919&fm=26&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=517913004,2451410586&fm=26&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=3755569129,1205422522&fm=26&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3782685451,3066622536&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=973931041,1439083005&fm=26&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3545575964,2149126446&fm=26&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=1814182549,3596720188&fm=26&gp=0.jpg"
    };
    private List<BannerBean> mBannerBeanList = new ArrayList<>();

    @Override
    protected void initData() {

        int statusBarHeight = StatusBarUtils.getStatusBarHeight(this);
        int statusBarByResource = StatusBarUtils.getStatusBarByResource(this);
        double statusBarHeight20dp = StatusBarUtils.getStatusBarHeight20dp(this);

            LogUtil.i("View","====反射 "+statusBarHeight);

            LogUtil.i("View","====资源id "+statusBarByResource);

            LogUtil.i("View","====写死的高度 "+statusBarHeight20dp);


    }

    @Override
    protected void onResume() {
        super.onResume();
        mBannerOther.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBannerOther.pause();
    }

    @Override
    protected void initView() {
//        mTvCenterTitle.setText("banner");
        for (int i = 0; i < images.length; i++) {
            BannerBean bannerBean = new BannerBean();
            bannerBean.setImages(images[i]);
            mBannerBeanList.add(bannerBean);
        }
        mBannerOther.setPages(mBannerBeanList, new MZHolderCreator<MyBannerViewHolder>() {

            @Override
            public MyBannerViewHolder createViewHolder() {
                return new MyBannerViewHolder();
            }
        });
        ViewPager viewPager = mBannerOther.getViewPager();
        viewPager.setPageMargin(20);
        mBannerOther.setDelayedTime(3000);
//        mBannerOther.setDuration(3000);
        mBannerOther.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int i) {
                Toast.makeText(BannerViewActivity.this, i, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class MyBannerViewHolder implements MZViewHolder<BannerBean> {

        private ImageView mIvBannerImage;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_banner_other, null);
            mIvBannerImage = view.findViewById(R.id.iv_banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int i, BannerBean bannerBean) {
//            Glide.with(context).load(bannerBean.getImages()).crossFade().into(mIvBannerImage);
            CornerTransform cornerTransform = new CornerTransform(context, MyUtils.dip2px(context, 8));
            cornerTransform.setExceptCorner(false, false, false, false);
            RequestOptions options = new RequestOptions();
            options.skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
            .transform(cornerTransform);
            Glide.with(context)
                    .asBitmap()
                    .load(bannerBean.getImages())
                    .apply(options)
                    .into(mIvBannerImage);
        }

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_banner_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

//    @OnClick(R.id.ll_left_back)
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.ll_left_back:
//                finish();
//                break;
//        }
//    }
}
