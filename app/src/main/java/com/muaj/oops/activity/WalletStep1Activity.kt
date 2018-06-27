package com.muaj.oops.activity

import android.view.View
import com.muaj.lib.base.BaseActivity
import com.muaj.lib.component.AppComponent
import com.muaj.lib.extensions.*
import com.muaj.lib.util.ClickProxy
import com.muaj.oops.R
import com.muaj.oops.component.DaggerMainComponent
import com.muaj.oops.databinding.ActivityWalletStep1Binding

/**
 * Created by muaj on 2018/6/19
 * WalletStep1 Activity
 */
class WalletStep1Activity : BaseActivity<ActivityWalletStep1Binding>() {
    override val layoutId: Int
        get() = R.layout.activity_wallet_step1

    override val hasStatusBar: Boolean
        get() = true

    override val hasToolStub: Boolean
        get() = true

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

    fun createWallet(){
      /**  step1:生成助记词mnemonic
       *    1)
       * step2:mnemonic to seed
       * step3:seed to master key and master chain code
       */

    }

}
