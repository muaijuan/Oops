package com.muaj.lib.extensions

import android.os.Handler

/**
 * Created by muaj on 2018/7/4
 * handler extensions
 */
inline fun Handler.postDelayed(delayMillis: Long, crossinline action: () -> Unit): Runnable {
    val runnable = Runnable { action() }
    postDelayed(runnable, delayMillis)
    return runnable
}