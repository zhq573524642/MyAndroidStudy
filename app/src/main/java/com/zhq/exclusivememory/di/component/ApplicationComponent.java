package com.zhq.exclusivememory.di.component;

import android.content.Context;

import com.zhq.exclusivememory.di.module.ApplicationModule;
import com.zhq.exclusivememory.di.scope.ContextLife;
import com.zhq.exclusivememory.di.scope.PerApp;

import dagger.Component;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/18.
 */
@PerApp
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    @ContextLife("Application")
    Context getApplication();
}
