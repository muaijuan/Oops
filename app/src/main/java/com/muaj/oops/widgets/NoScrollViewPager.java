package com.muaj.oops.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/** ViewPager
 *  user-defined whether horizontal slide
 * @author muaj
 * @date 2018/5/16
 */

public class NoScrollViewPager extends ViewPager {
    private boolean  noScroll=true;
    public NoScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return !noScroll && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return !noScroll && super.onInterceptTouchEvent(ev);
    }
}
