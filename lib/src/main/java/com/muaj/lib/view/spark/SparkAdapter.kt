/**
 * Copyright (C) 2016 Robinhood Markets, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.muaj.lib.view.spark

import android.database.DataSetObservable
import android.database.DataSetObserver
import android.graphics.RectF
import android.support.annotation.VisibleForTesting

/**
 * A simple adapter class - evenly distributes your points along the x axis, does not draw a base
 * line, and has support for registering/notifying [DataSetObserver]s when data is changed.
 */
abstract class SparkAdapter {
    private val observable = DataSetObservable()

    /**
     * @return the number of points to be drawn
     */
    abstract fun getCount(): Int

    /**
     * Gets the float representation of the boundaries of the entire dataset. By default, this will
     * be the min and max of the actual data points in the adapter. This can be overridden for
     * custom behavior. When overriding, make sure to set RectF's values such that:
     *
     *
     *  * left = the minimum X value
     *  * top = the minimum Y value
     *  * right = the maximum X value
     *  * bottom = the maximum Y value
     *
     *
     * @return a RectF of the bounds desired around this adapter's data.
     */
    // set values on the return object
    val dataBounds: RectF
        get() {
            val count = getCount()
            val hasBaseLine = hasBaseLine()

            var minY = if (hasBaseLine) baseLine else java.lang.Float.MAX_VALUE
            var maxY = if (hasBaseLine) minY else -java.lang.Float.MAX_VALUE
            var minX = java.lang.Float.MAX_VALUE
            var maxX = -java.lang.Float.MAX_VALUE
            for (i in 0 until count) {
                val x = getX(i)
                minX = Math.min(minX, x)
                maxX = Math.max(maxX, x)

                val y = getY(i)
                minY = Math.min(minY, y)
                maxY = Math.max(maxY, y)
            }
            return createRectF(minX, minY, maxX, maxY)
        }

    /**
     * @return the float representation of the Y value of the desired baseLine.
     */
    val baseLine: Float
        get() = 0f

    /**
     * @return the object at the given index
     */
    abstract fun getItem(index: Int): Any

    /**
     * @return the float representation of the X value of the point at the given index.
     */
    fun getX(index: Int): Float {
        return index.toFloat()
    }

    /**
     * @return the float representation of the Y value of the point at the given index.
     */
    abstract fun getY(index: Int): Float

    /**
     * Hook for unit tests
     */
    @VisibleForTesting
    internal fun createRectF(left: Float, top: Float, right: Float, bottom: Float): RectF {
        return RectF(left, top, right, bottom)
    }

    /**
     * @return true if you wish to draw a "base line" - a horizontal line across the graph used
     * to compare the rest of the graph's points against.
     */
    fun hasBaseLine(): Boolean {
        return false
    }

    /**
     * Notifies the attached observers that the underlying data has been changed and any View
     * reflecting the data set should refresh itself.
     */
    fun notifyDataSetChanged() {
        observable.notifyChanged()
    }

    /**
     * Notifies the attached observers that the underlying data is no longer valid or available.
     * Once invoked this adapter is no longer valid and should not report further data set
     * changes.
     */
    fun notifyDataSetInvalidated() {
        observable.notifyInvalidated()
    }

    /**
     * Register a [DataSetObserver] to listen for updates to this adapter's data.
     * @param observer    the observer to register
     */
    fun registerDataSetObserver(observer: DataSetObserver) {
        observable.registerObserver(observer)
    }

    /**
     * Unregister a [DataSetObserver] from updates to this adapter's data.
     * @param observer    the observer to unregister
     */
    fun unregisterDataSetObserver(observer: DataSetObserver) {
        observable.unregisterObserver(observer)
    }
}
