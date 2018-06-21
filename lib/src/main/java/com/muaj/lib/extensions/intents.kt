package com.muaj.lib.extensions

import android.app.Activity
import android.app.Fragment
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle

/**
 * Created by muaj on 2018/6/6
 * Intent 启动 扩展函数
 */
inline fun <reified T : Activity> Context.buildIntent(initializer: Intent.() -> Unit): Intent {
    return Intent(this, T::class.java).apply(initializer)
}

/**
 * Activity startActivity extensions
 */
inline fun <reified T : Activity> Activity.startActivity(initializer: Intent.() -> Unit) {
    startActivity(Intent(this, T::class.java).apply(initializer))
}

inline fun <reified T : Activity> Activity.startActivityForResult(initializer: Intent.() -> Unit, requestCode: Int) {
    startActivityForResult(Intent(this, T::class.java).apply(initializer), requestCode)
}


/**
 * Fragment startActivity extensions
 */
inline fun <reified T : Activity> Fragment.startActivity(initializer: Intent.() -> Unit) {
    startActivity(Intent(activity, T::class.java).apply(initializer))
}

inline fun <reified T : Activity> Fragment.startActivityForResult(initializer: Intent.() -> Unit, requestCode: Int) {
    startActivityForResult(Intent(activity, T::class.java).apply(initializer), requestCode)
}

/**
 * android.support.v4.app.Fragment startActivity extensions
 */
inline fun <reified T : Activity> android.support.v4.app.Fragment.startActivity(initializer: Intent.() -> Unit) {
    startActivity(Intent(activity, T::class.java).apply(initializer))
}

inline fun <reified T : Activity> android.support.v4.app.Fragment.startActivityForResult(initializer: Intent.() -> Unit, requestCode: Int) {
    startActivityForResult(Intent(activity, T::class.java).apply(initializer), requestCode)
}

/**
 * Context startService extensions
 */
inline fun <reified T : Service> Context.startService(initializer: Intent.() -> Unit) {
    startService(Intent(this, T::class.java).apply(initializer))
}