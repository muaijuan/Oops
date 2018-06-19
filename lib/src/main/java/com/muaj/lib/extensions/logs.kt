package com.muaj.lib.extensions

import com.muaj.lib.extensions.log.KLog

/**
 * Created by muaj on 2018/6/6
 * Log 打印 扩展函数
 */

fun logv(msg: String, customTag: String = "") = KLog.v(msg, customTag)

fun logd(msg: String, customTag: String = "") = KLog.d(msg, customTag)

fun logi(msg: String, customTag: String = "") = KLog.i(msg, customTag)

fun logw(msg: String, customTag: String = "") = KLog.w(msg, customTag)

fun loge(msg: String, customTag: String = "") = KLog.e(msg, customTag)

fun logJson(msg: String, customTag: String = "") = KLog.json(msg, customTag)

fun logGlobalTag(global: String) {
    KLog.logGlobalTag = global
}

fun logEnabled(logEnabled: Boolean) {
    KLog.logEnabled = logEnabled
}
