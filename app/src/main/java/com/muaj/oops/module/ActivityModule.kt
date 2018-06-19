package com.muaj.oops.module

import android.app.Activity
import android.support.v4.app.Fragment

import dagger.Module
import dagger.Provides

/**
 * Created by muaj on 2018/6/6
 *
 */
@Module
class ActivityModule {

    private var mContext: Activity? = null

    constructor(activity: Activity) {
        mContext = activity
    }

    constructor(fragment: Fragment) {
        mContext = fragment.activity
    }

    @Provides
    internal fun provideActivity(): Activity? {
        return mContext
    }
}
