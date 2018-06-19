package com.muaj.oops.item


import android.databinding.BindingAdapter
import android.graphics.Color
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.view.animation.LinearInterpolator
import android.widget.ImageView

import com.muaj.lib.rv.RVAdapter
import com.muaj.lib.view.spark.LineSparkAnimator
import com.muaj.lib.view.spark.RandomizedAdapter
import com.muaj.lib.view.spark.SparkView
import com.muaj.oops.BR
import com.muaj.oops.R

import java.util.Random

/**
 * Created by muaj on 2018/6/6
 *
 */

class CoinItem : RVAdapter.RVItem {

    override val layout: Int
        get() = R.layout.item_coin

    override val variableId: Int
        get() = BR.item

    var coin: String
    @DrawableRes
    var iconId: Int = 0
    var yData: FloatArray
    @ColorInt
    var lineColor: Int = 0
    @ColorInt
    var shaderColor: Int = 0

    init {
        val index = Random().nextInt(100) % 5
        coin = COINS[index]
        iconId = R.mipmap.ic_launcher_round
        yData = FloatArray(10)
        for (i in yData.indices) {
            yData[i] = Random().nextFloat() * 200
        }
        val i = Random().nextInt(2)
        lineColor = COLORS[i]
        shaderColor = COLORS1[i]
    }

    companion object {

        private val COINS = arrayOf("BTC", "ETC", "XUC", "EOS", "ETH")

        private val COLORS = intArrayOf(Color.parseColor("#ff4081"), Color.parseColor("#00ff33"))

        private val COLORS1 = intArrayOf(Color.parseColor("#80ff4081"), Color.parseColor("#8000ff33"))

        @BindingAdapter("android:src")
        @JvmStatic
        fun setSrc(view: ImageView, resId: Int) {
            view.setImageResource(resId)
        }

        @BindingAdapter(value = ["yData", "lineColor", "shaderColor"], requireAll = true)
        @JvmStatic
        fun setSparkAdapter(sparkView: SparkView, yData: FloatArray, @ColorInt lineColor: Int, @ColorInt shaderColor: Int) {
            val adapter = RandomizedAdapter(yData)
            sparkView.setShaderColor(shaderColor)
            sparkView.setLineColor(lineColor)
            val lineSparkAnimator = LineSparkAnimator(sparkView)
            lineSparkAnimator.interpolator = LinearInterpolator()
            lineSparkAnimator.duration = 2000
            //sparkView.setPlayAnimate(true);
            sparkView.adapter = adapter
        }
    }


}
