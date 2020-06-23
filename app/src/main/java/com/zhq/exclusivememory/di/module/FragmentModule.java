package com.zhq.exclusivememory.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.zhq.exclusivememory.di.scope.ContextLife;
import com.zhq.exclusivememory.di.scope.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/18.
 */
@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        this.mFragment = fragment;
    }

    @Provides
    @PerFragment
    @ContextLife("Activity")
    public Context providerActivityContext() {
        return mFragment.getActivity();
    }

    @Provides
    @PerFragment
    public Activity providerActivity() {
        return mFragment.getActivity();
    }

    @Provides
    @PerFragment
    public Fragment providerFragment() {
        return mFragment;
    }
}
