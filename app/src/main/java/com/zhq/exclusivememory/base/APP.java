package com.zhq.exclusivememory.base;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.qw.soul.permission.SoulPermission;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.di.component.ApplicationComponent;
import com.zhq.exclusivememory.di.component.DaggerApplicationComponent;
import com.zhq.exclusivememory.di.module.ApplicationModule;

import java.text.SimpleDateFormat;


/**
 * Created by Huiqiang Zhang
 * on 2019/1/18.
 */

public class APP extends Application {

    private ApplicationComponent mApplicationComponent;
    private static Application mApplication;

    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setDragRate(1);//显示下拉高度/手指真实下拉高度=阻尼效果
                ClassicsHeader header = new ClassicsHeader(context);
                header.setTimeFormat(new SimpleDateFormat("更新于：MM-dd HH:mm"));
                header.setEnableLastTime(true);
                header.setSpinnerStyle(SpinnerStyle.Scale);
                header.setPrimaryColors(ContextCompat.getColor(context, R.color.color_f6f6f6), ContextCompat.getColor(context, R.color.color_000000));
                return header;//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                layout.setEnableLoadmoreWhenContentNotFull(false);//是否在列表不满一页时候开启上拉加载功能
                layout.setEnableScrollContentWhenLoaded(true);//是否在加载完成时滚动列表显示新的内容
                layout.setEnableAutoLoadmore(true);//是否启用列表惯性滑动到底部时自动加载更多
                ClassicsFooter footer = new ClassicsFooter(context);
                footer.setPrimaryColors(ContextCompat.getColor(context, R.color.color_f6f6f6), ContextCompat.getColor(context, R.color.color_404040));
                footer.setSpinnerStyle(SpinnerStyle.Scale);//设置为拉伸模式
                return footer;//指定为经典Footer，默认是 BallPulseFooter
            }
        });

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        initApplicationComponent();
        //百度地图初始化
        initBaiduMap();
        SoulPermission.init(this);

    }

    private void initBaiduMap() {
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    public static Context getAppContext() {
        return mApplication.getApplicationContext();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    private void initApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
