package com.muaj.oops.activity.activity

/**
 * Created by muaj on 2018/6/19
 * WalletStep1 Activity
 */
class WalletStep1Activity : BaseActivity<ActivityWalletStep1Binding>() {
    override val layoutId: Int
        get() = R.layout.activity_WalletStep1

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
