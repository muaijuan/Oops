package com.muaj.lib

import android.app.Activity
import android.app.Fragment

/**
 * Created by muaj on 2018/6/6
 * get mContext
 */
val Activity.mContext
    get() = this

val Fragment.mContext
    get() = this.activity

val android.support.v4.app.Fragment.mContext
    get() = this.activity