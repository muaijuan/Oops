package com.muaj.lib.view.spark

import android.animation.Animator

/**
 * @author muaj
 * @date 2018/5/31
 */

interface SparkAnimator {

    /** Returns an Animator that performs the desired animation.
     * @return
     */
    fun getAnimator(): Animator?
}
