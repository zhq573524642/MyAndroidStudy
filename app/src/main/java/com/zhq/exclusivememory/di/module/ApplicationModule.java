package com.zhq.exclusivememory.di.module;

import android.content.Context;

import com.zhq.exclusivememory.base.APP;
import com.zhq.exclusivememory.di.scope.ContextLife;
import com.zhq.exclusivememory.di.scope.PerApp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/18.
 */
@Module
public class ApplicationModule {
    private APP mApplication;

    public ApplicationModule(APP application) {
        this.mApplication = application;
    }

    @Provides
    @PerApp
    @ContextLife("Application")
    public Context providerApplicationContext() {
        return mApplication.getApplicationContext();
    }
}
