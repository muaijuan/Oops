package com.muaj.lib.util

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.annotation.DrawableRes
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.muaj.lib.R
import com.muaj.lib.databinding.ToastLayoutBinding

/**
 * Created by muaj on 2018/6/6
 * custom toast
 */

object ToastUtils {

    private var mToast: Toast? = null

    private var mToastBinding: ToastLayoutBinding? = null

    private fun init(context: Context) {
        mToast = Toast(context)
        mToastBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.toast_layout, null, false)
        mToast!!.view = mToastBinding!!.root
    }

    fun showToast(context: Context,text: String, @DrawableRes iconId: Int = 0, duration: Int = Toast.LENGTH_SHORT, isSingle: Boolean = true) {
        if (!isSingle) {
            //连续弹出的Toast
            init(context)
        } else {
            //非连续弹出的Toast
            if (mToast == null || mToastBinding == null) {
                init(context)
            }
        }

        mToast!!.duration = duration
        if (iconId != 0) {
            mToastBinding!!.toastIcon.setImageResource(iconId)
            mToastBinding!!.toastIcon.visibility = View.VISIBLE
        } else {
            mToastBinding!!.toastIcon.visibility = View.GONE
        }
        if (!TextUtils.isEmpty(text)) {
            mToastBinding!!.toastText.text = text
            mToast!!.show()
        }
    }
}

