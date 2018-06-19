package com.muaj.lib.view.spark

/**
 * @author muaj
 * @date 2018/5/30
 */

class RandomizedAdapter(private val yData: FloatArray) : SparkAdapter() {

    override fun getCount(): Int {
        return yData.size
    }

    override fun getItem(index: Int): Any {
        return yData[index]
    }

    override fun getY(index: Int): Float {
        return yData[index]
    }
}