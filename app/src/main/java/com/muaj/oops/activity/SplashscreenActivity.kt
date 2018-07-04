package com.muaj.oops.activity

import com.muaj.lib.base.BaseActivity
import com.muaj.lib.component.AppComponent
import com.muaj.lib.util.StatusBarUtils
import com.muaj.oops.R
import com.muaj.oops.component.DaggerMainComponent
import com.muaj.oops.databinding.ActivitySplashscreenBinding

/**
 * Created by muaj on 2018/7/3
 * Splash Screen Activity
 */
class SplashscreenActivity(override val layoutId: Int = R.layout.activity_splashscreen,
                           override val hasStatusBar: Boolean=false,
                           override val hasToolStub: Boolean=false)
    : BaseActivity<ActivitySplashscreenBinding>() {

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
        StatusBarUtils.setTranslucent(this,200)
    }

    override fun initListener() {
    }


}