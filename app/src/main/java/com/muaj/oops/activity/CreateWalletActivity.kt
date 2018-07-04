package com.muaj.oops.activity

import android.view.View
import com.muaj.lib.base.BaseActivity
import com.muaj.lib.component.AppComponent
import com.muaj.lib.extensions.*
import com.muaj.lib.util.ClickProxy
import com.muaj.oops.R
import com.muaj.oops.component.DaggerMainComponent
import com.muaj.oops.databinding.ActivityCreateWalletBinding

/**
 * Created by muaj on 2018/6/19
 * CreateWallet Activity
 */
class CreateWalletActivity(override val layoutId: Int = R.layout.activity_create_wallet,
                           override val hasStatusBar: Boolean = true,
                           override val hasToolStub: Boolean = true)
    : BaseActivity<ActivityCreateWalletBinding>() {
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
        mToolbarStubBinding!!.ivToolbarBack.setImageDrawable(loadDrawable(R.drawable.svg_arrow_left))
        mToolbarStubBinding!!.tvToolbarTitle.text = "创建钱包"
    }

    override fun initListener() {
        mToolbarStubBinding!!.ivToolbarBack.setOnClickListener(ClickProxy(View.OnClickListener { v ->
            onBackPressed()
        }))
        mBinding.btnCreate.setOnClickListener(ClickProxy(View.OnClickListener {

        }))

    }


}
