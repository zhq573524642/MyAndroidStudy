package com.zhq.exclusivememory.di.module;

import android.app.Activity;
import android.content.Context;

import com.zhq.exclusivememory.di.scope.ContextLife;
import com.zhq.exclusivememory.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/18.
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }
    @Provides
    @PerActivity
    @ContextLife("Activity")
    public Context providerActivityContext(){
        return mActivity;
    }
    @Provides
    @PerActivity
    public Activity providerActivity(){
        return mActivity;
    }
}
