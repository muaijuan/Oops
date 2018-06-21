package com.muaj.lib.util

import android.view.View

/**
 * Created by muaj on 2018/6/20
 * 避免在1秒内触发多次点击
 */
abstract class NoDoubleClickListener : View.OnClickListener {
    private var lastClickTime: Long = 0
    private var id = -1

    override fun onClick(v: View) {
        val currentTime = System.currentTimeMillis()
        val mId = v.id
        if (id != mId) {
            id = mId
            lastClickTime = currentTime
            onNoDoubleClick(v)
            return
        }
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime
            onNoDoubleClick(v)
        }
    }

    abstract fun onNoDoubleClick(v: View)

    companion object {
        const val MIN_CLICK_DELAY_TIME = 1000
    }
}