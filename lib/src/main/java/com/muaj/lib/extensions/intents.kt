package com.muaj.lib.extensions

import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 * Created by muaj on 2018/6/6
 * Intent 启动 扩展函数
 */

inline fun <reified T : Context> Context.createIntent(extras: Bundle? = null): Intent {
    val intent = Intent(this, T::class.java)
    extras?.let {
        intent.putExtras(it)
    }
    return intent
}