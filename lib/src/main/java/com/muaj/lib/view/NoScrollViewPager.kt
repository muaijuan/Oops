package com.muaj.lib.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by muaj on 2018/6/6
 * ViewPager
 * user-defined whether horizontal slide
 */

class NoScrollViewPager : ViewPager {
    private val noScroll = true

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return !noScroll && super.onTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return !noScroll && super.onInterceptTouchEvent(ev)
    }
}
