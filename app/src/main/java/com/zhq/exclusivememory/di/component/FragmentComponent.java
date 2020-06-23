package com.zhq.exclusivememory.di.component;

import android.app.Activity;
import android.content.Context;

import com.zhq.exclusivememory.di.scope.ContextLife;
import com.zhq.exclusivememory.di.module.FragmentModule;
import com.zhq.exclusivememory.di.scope.PerFragment;

import dagger.Component;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/18.
 */
@PerFragment
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    @ContextLife("Activity")
    Context getActivityContext();

    @ContextLife("Application")
    Context getApplicationContext();

    Activity getActivity();
}
