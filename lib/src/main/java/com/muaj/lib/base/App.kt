package com.muaj.lib.base

import android.app.Application
import android.content.Context

import com.muaj.lib.component.AppComponent
import com.muaj.lib.component.DaggerAppComponent

/**
 * Created by muaj on 2018/6/6
 * Base Application
 */

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        mContext = this
        initComponent()
    }

    private fun initComponent() {
        appComponent = DaggerAppComponent.builder()
                .build()
    }

    companion object {
        @JvmStatic
        lateinit var mContext: Context
            private set

        @JvmStatic
        lateinit var appComponent: AppComponent
            private set

    }
}
