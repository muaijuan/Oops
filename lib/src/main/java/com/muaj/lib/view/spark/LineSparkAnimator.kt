package com.muaj.lib.view.spark

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.graphics.Path
import android.graphics.PathMeasure
import android.support.annotation.IntRange

/**
 * @author muaj
 * @date 2018/5/31
 */

class LineSparkAnimator(private val sparkView: SparkView) : Animator(), SparkAnimator {

    private val anim: ValueAnimator= ValueAnimator.ofFloat(0f, 1f)

    init {
        this.sparkView.setAnimator(this)
    }

     override fun getAnimator(): Animator? {

        val linePath = sparkView.getLinePath()
        val lineMeasure = PathMeasure(linePath, false)
        //final PathMeasure shaderMeasure = new PathMeasure(shaderPath, true);

        val lineLength = lineMeasure.length
        //final float shaderLength = shaderMeasure.getLength();
        if (lineLength <= 0) {
            return null
        }

        anim.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float

            val lineProgressLength = progress * lineLength
            //float shaderProgressLength = progress * shaderLength;

            linePath.reset()
            lineMeasure.getSegment(0f, lineProgressLength, linePath, true)

            val pos = FloatArray(2)
            val tan = FloatArray(2)
            //                shaderPath.reset();
            //                shaderMeasure.getSegment(0, shaderProgressLength, shaderPath, true);
            lineMeasure.getPosTan(lineProgressLength, pos, tan)
            sparkView.setAnimPath(linePath, pos)
        }

        return anim
    }

    override fun getStartDelay(): Long {
        return anim.startDelay
    }

    override fun setStartDelay(@IntRange(from = 0) startDelay: Long) {
        anim.startDelay = startDelay
    }

    override fun setDuration(@IntRange(from = 0) duration: Long): Animator {
        return anim.setDuration(duration)
    }

    override fun getDuration(): Long {
        return anim.duration
    }

    override fun setInterpolator(timeInterpolator: TimeInterpolator) {
        anim.interpolator = timeInterpolator
    }

    override fun isRunning(): Boolean {
        return anim.isRunning
    }
}
