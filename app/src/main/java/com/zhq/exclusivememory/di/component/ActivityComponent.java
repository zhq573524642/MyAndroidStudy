package com.zhq.exclusivememory.di.component;

import android.app.Activity;
import android.content.Context;

import com.zhq.exclusivememory.di.module.ActivityModule;
import com.zhq.exclusivememory.di.scope.ContextLife;
import com.zhq.exclusivememory.di.scope.PerActivity;
import com.zhq.exclusivememory.ui.activity.login.LoginActivity;
import com.zhq.exclusivememory.ui.activity.main.MainActivity;

import dagger.Component;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/18.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();
    void inject(MainActivity mainActivity);
    void inject(LoginActivity loginActivity);

}
