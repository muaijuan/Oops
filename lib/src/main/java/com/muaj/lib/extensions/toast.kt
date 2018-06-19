package com.muaj.lib.extensions

import android.content.Context
import android.support.annotation.DrawableRes
import android.view.View
import android.widget.Toast
import com.muaj.lib.util.ToastUtils

/**
 * Created by muaj on 2018/6/5
 * Toast 扩展函数
 */
/*
  ---------- Context ----------
 */
fun Context.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    if (duration == 0 || duration == 1) {
        Toast.makeText(applicationContext, msg, duration).show()
    } else {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }
}

fun Context.longtoast(msg: String) {
    Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
}

fun Context.ktoast(msg: String, @DrawableRes iconId: Int = 0, duration: Int = Toast.LENGTH_SHORT, isSingle: Boolean = true) {
    if (duration == 0 || duration == 1) {
        ToastUtils.showToast(applicationContext, msg, iconId, duration, isSingle)
    } else {
        ToastUtils.showToast(applicationContext, msg, iconId, Toast.LENGTH_SHORT, isSingle)
    }
}

fun Context.klongtoast(msg: String, @DrawableRes iconId: Int = 0, isSingle: Boolean = true) {
    ToastUtils.showToast(applicationContext, msg, iconId, Toast.LENGTH_LONG, isSingle)
}

/*
  ---------- View ----------
 */
fun View.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    context.toast(msg, duration)
}


