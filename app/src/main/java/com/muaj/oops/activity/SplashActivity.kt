package com.muaj.oops.activity

import android.os.Handler
import android.os.Looper
import com.muaj.lib.base.BaseActivity
import com.muaj.lib.component.AppComponent
import com.muaj.lib.extensions.postDelay
import com.muaj.lib.extensions.startActivity
import com.muaj.lib.util.StatusBarUtils
import com.muaj.oops.R
import com.muaj.oops.component.DaggerMainComponent
import com.muaj.oops.databinding.ActivitySplashBinding


/**
 * Created by muaj on 2018/7/3
 * Splash Screen Activity
 */
class SplashActivity(override val layoutId: Int = R.layout.activity_splash,
                     override val hasStatusBar: Boolean=false,
                     override val hasToolStub: Boolean=false)
    : BaseActivity<ActivitySplashBinding>() {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                //.activityModule(ActivityModule(this))
                .build()
                .inject(this)
    }

    override fun attachView() {
    }

    override fun initDatas() {
    }

    override fun initToolBar() {

    }

    override fun initListener() {
        val mHandler:Handler= Handler(Looper.getMainLooper())
        val runnable=mHandler.postDelay(2000){
            startActivity<WalletHomeActivity> { }
            finish()
        }


    }


    override fun setStatusBar() {
        super.setStatusBar()
        StatusBarUtils.setTranslucent(this,100)
    }
}