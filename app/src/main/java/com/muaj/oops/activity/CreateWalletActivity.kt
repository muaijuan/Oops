package com.muaj.oops.activity

import com.muaj.lib.base.BaseActivity
import com.muaj.lib.component.AppComponent
import com.muaj.oops.R
import com.muaj.oops.component.DaggerMainComponent
import com.muaj.oops.databinding.ActivityCreateWalletBinding

/**
 * Created by muaj on 2018/6/6
 * Create Wallet Activity
 */
class CreateWalletActivity : BaseActivity<ActivityCreateWalletBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_create_wallet

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
    }

    override fun hasStatusBar(): Boolean {
        return true
    }

    override fun hasToolStub(): Boolean {
        return false
    }
}
