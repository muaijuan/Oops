package com.muaj.lib.util

import android.view.View

/**
 * Created by muaj on 2018/6/21
 * 优雅的处理重复点击
 */
class ClickProxy : View.OnClickListener {

    private var origin: View.OnClickListener
    private var lastClick: Long = 0
    private var delayTime: Long = 1000 //ms
    private var mIAgain: IAgain? = null

    constructor(origin: View.OnClickListener, delay: Long = 1000, again: IAgain? = null) {
        this.origin = origin
        this.mIAgain = again
        this.delayTime = delay
    }

    override fun onClick(v: View) {
        if (System.currentTimeMillis() - lastClick >= delayTime) {
            origin!!.onClick(v)
            lastClick = System.currentTimeMillis()
        } else {
            mIAgain?.onAgain()
        }
    }

    interface IAgain {
        fun onAgain() //重复点击
    }
}
