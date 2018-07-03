package com.muaj.oops.activity

import android.view.View
import com.muaj.lib.base.BaseActivity
import com.muaj.lib.component.AppComponent
import com.muaj.lib.extensions.startActivity
import com.muaj.lib.util.ClickProxy
import com.muaj.oops.R
import com.muaj.oops.component.DaggerMainComponent
import com.muaj.oops.databinding.ActivityWalletHomeBinding

/**
 * Created by muaj on 2018/6/6
 * WalletHome Activity
 */
class WalletHomeActivity : BaseActivity<ActivityWalletHomeBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_wallet_home

    override val hasStatusBar: Boolean
        get() = true

    override val hasToolStub: Boolean
        get() = false


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
        mBinding.btnCreate.setOnClickListener(ClickProxy(View.OnClickListener { v ->
            startActivity<CreateWalletActivity> { }
        }))
    }

}