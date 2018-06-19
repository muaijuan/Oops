package com.muaj.lib.util

import android.os.Build
import android.os.Environment
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*

/**
 * Created by muaj on 2018/6/11
 *
 */
object OsUtils {
    private var properties: Properties? = null

    private val KEY_EMUI_VERSION_CODE = "ro.build.version.emui"
    private val KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code"
    private val KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name"
    private val KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage"

    fun isEMUI(): Boolean {
        return isPropertiesExist(KEY_EMUI_VERSION_CODE)
    }


    fun isMIUI(): Boolean {
        return isPropertiesExist(KEY_MIUI_VERSION_CODE, KEY_MIUI_VERSION_NAME, KEY_MIUI_INTERNAL_STORAGE)
    }

    fun isFlyme(): Boolean {
        return try {
            val method = Build::class.java.getMethod("hasSmartBar")
            method != null
        } catch (e: Exception) {
            false
        }
    }

    @Throws(IOException::class)
    private fun getProp() {
        properties = Properties()
        properties!!.load(FileInputStream(File(Environment.getRootDirectory(), "build.prop")))
    }

    private fun isPropertiesExist(vararg keys: String): Boolean {
        try {
            if (properties == null) {
                getProp()
            }
            for (key in keys) {
                val str = properties!!.getProperty(key, "")
                if (str == "") {
                    return false
                }
            }
            return true
        } catch (ex: IOException) {
            return false
        }

    }
}
