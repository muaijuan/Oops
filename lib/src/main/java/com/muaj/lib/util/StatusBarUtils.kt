package com.muaj.lib.util

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.IntRange
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.muaj.lib.R

/**
 * Created by muaj on 2018/6/6
 * 状态栏
 * 注意：android:fitsSystemWindows 默认未设置默认为false
 * 注意2：android:fitsSystemWindows  多个fragment只有第一个设置有效
 */

object StatusBarUtils {

    private const val DEFAULT_STATUS_BAR_ALPHA = 30
    private val FAKE_STATUS_BAR_VIEW_ID = R.id.statusbarutil_fake_status_bar_view

    /**========================================== 透明状态栏 ================================ */

    /**
     * 设置状态栏全透明
     *
     * @param activity 需要设置的activity
     */
    fun setTransparent(activity: Activity) {
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        activity.window.statusBarColor = Color.TRANSPARENT
    }


    /**======================================== 半透明状态栏 ================================ */

    /**
     * 使状态栏半透明
     *
     *
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity       需要设置的activity
     * @param statusBarAlpha 状态栏透明度
     */
    fun setTranslucent(activity: Activity, @IntRange(from = 0, to = 255) statusBarAlpha: Int = DEFAULT_STATUS_BAR_ALPHA) {
        setTransparent(activity)
        activity.window.statusBarColor = Color.argb(statusBarAlpha, 0, 0, 0)
    }

    /**=============================================== 纯色状态栏 =================================== */

    /**
     * 设置状态栏颜色
     *
     * @param activity       需要设置的activity
     * @param color          状态栏颜色值
     * @param statusBarAlpha 状态栏透明度
     */
    fun setColor(activity: Activity, @ColorInt color: Int, @IntRange(from = 0, to = 255) statusBarAlpha: Int = 0) {
        //        setTransparent(activity);
        activity.window.statusBarColor = calculateStatusColor(color, statusBarAlpha)
    }

    /**===============================================  设置状态栏图标 浅色/深色======================================== */

    /**
     * 设置状态栏图标浅色/深色
     * 这个Flag只有在使用了FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS 并且没有使用 FLAG_TRANSLUCENT_STATUS的时候才有效
     * 也就是只有在状态栏全透明的时候才有效。
     *
     * @param bDark
     */
    fun setStatusBarDark(activity: Activity, bDark: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (OsUtils.isMIUI()) {
                setMiuiStatusBarDarkMode(activity, bDark)
            } else if (OsUtils.isFlyme()) {
                setMeizuStatusBarDarkIcon(activity, bDark)
            } else {
                val decorView = activity.window.decorView
                if (decorView != null) {
                    var vis = decorView.systemUiVisibility
                    if (bDark) {
                        // View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR这个字段就是把状态栏标记为浅色
                        // 然后状态栏的字体颜色自动转换为深色 只能在Android6.0及以后的版本中实现。
                        //这里暗藏一个坑：MIUI6+自己实现了浅色状态栏，6.0的这个设置在小米手机上无效
                        vis = vis or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    } else {
                        vis = vis and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                    }
                    decorView.systemUiVisibility = vis
                }
            }
        }
    }

    /**
     * 设置成白色的背景，字体颜色为黑色。MIUI
     * @param bDark
     */
    private fun setMiuiStatusBarDarkMode(activity: Activity, dark: Boolean): Boolean {
        val clazz = activity.window.javaClass
        try {
            val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            var darkModeFlag = field.getInt(layoutParams)
            val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
            extraFlagField.invoke(activity.window, if (dark) darkModeFlag else 0, darkModeFlag)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

    /**
     * 设置成白色的背景，字体颜色为黑色。 Flyme
     * @param bDark
     */
    private fun setMeizuStatusBarDarkIcon(activity: Activity, dark: Boolean): Boolean {
        try {
            val lp = activity.window.attributes
            val darkFlag = WindowManager.LayoutParams::class.java
                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
            val meizuFlags = WindowManager.LayoutParams::class.java
                    .getDeclaredField("meizuFlags")
            darkFlag.isAccessible = true
            meizuFlags.isAccessible = true
            val bit = darkFlag.getInt(null)
            var value = meizuFlags.getInt(lp)
            if (dark) {
                value = value or bit
            } else {
                value = value and bit.inv()
            }
            meizuFlags.setInt(lp, value)
            activity.window.attributes = lp
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**===============================================  其他 ======================================== */

    /**
     * 计算状态栏颜色
     *
     * @param color color值
     * @param alpha alpha值
     * @return 最终的状态栏颜色
     */
    private fun calculateStatusColor(@ColorInt color: Int, alpha: Int): Int {
        if (alpha == 0) {
            return color
        }
        val a = 1 - alpha / 255f
        var red = color shr 16 and 0xff
        var green = color shr 8 and 0xff
        var blue = color and 0xff
        red = (red * a + 0.5).toInt()
        green = (green * a + 0.5).toInt()
        blue = (blue * a + 0.5).toInt()
        return 0xff shl 24 or (red shl 16) or (green shl 8) or blue
    }


    /**
     * 设置根布局的 FitsSystemWindows
     */
    fun setRootFitsSystemWindows(activity: Activity, isFitsSystemWindows: Boolean = false) {
        val parent = activity.findViewById<ViewGroup>(android.R.id.content)
        var i = 0
        val count = parent.childCount
        while (i < count) {
            val childView = parent.getChildAt(i)
            if (childView is ViewGroup) {
                childView.setFitsSystemWindows(isFitsSystemWindows)
                //((ViewGroup) childView).setClipToPadding(isFitsSystemWindows);
            }
            i++
        }
    }
}
