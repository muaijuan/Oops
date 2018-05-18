package com.muaj.oops.module;

import android.app.Activity;
import android.app.Fragment;

import dagger.Module;
import dagger.Provides;

/**
 * @author muaj
 * @date 2018/5/15
 */
@Module
public class ActivityModule {

    private Activity mContext;

    public ActivityModule(Activity activity) {
        mContext = activity;
    }

    public ActivityModule(Fragment fragment) {
        mContext = fragment.getActivity();
    }

    @Provides
    Activity provideActivity() {
        return mContext;
    }
}
