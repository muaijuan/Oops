package com.muaj.oops.activity

import android.view.View
import com.muaj.lib.base.BaseActivity
import com.muaj.lib.component.AppComponent
import com.muaj.lib.extensions.loge
import com.muaj.lib.util.ClickProxy
import com.muaj.oops.R
import com.muaj.oops.component.DaggerMainComponent
import com.muaj.oops.databinding.ActivityWalletHomeBinding

/**
 * Created by muaj on 2018/6/6
 * WalletHome Activity
 */
class WalletHomeActivity(override val layoutId: Int = R.layout.activity_wallet_home,
                         override val hasStatusBar: Boolean = true,
                         override val hasToolStub: Boolean = false) : BaseActivity<ActivityWalletHomeBinding>() {

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
//        Thread(Runnable {
//            for (x in 20..100) {
//                for (y in 20..100) {
//                    if (3.10 * x - y > 0 && 0.72 * y - x > 0) {
//                        loge("=====================\nx=$x  ,y=$y\nx win:3.10*x-y=${3.10 * x - y}\ny win:0.72*y-x=${0.72 * y - x}", "oops")
//                    }
//                }
//            }
//        })
        mBinding.btnCreate.setOnClickListener(ClickProxy(View.OnClickListener { v ->
            //startActivity<CreateWalletActivity> { }

            val x1 = 3.50
            val y1 = 0.66
            loge("start...$x1,$y1", "oops")
            var x = 0

            while (x < 100) {
                var y = 20
                while (y < 100) {
                    if ((x1 * x - y) > 0 && (y1 * y - x) > 0) {
                        loge("=====================\nx=$x  ,y=$y\nx win:$x1*x-y=${x1 * x - y}\ny win:$y1*y-x=${y1 * y - x}", "oops")
                    }
                    y +=10
                }
                x += 10
            }
//            for (x in 1..10) {
//                for (y in 1..10) {
//                    if (x1* x - y > 0 && y1 * y - x > 0) {
//                        loge("=====================\nx=$x  ,y=$y\nx win:$x1*x-y=${x1 * x - y}\ny win:$y1*y-x=${y1 * y - x}", "oops")
//                    }
//
//                }
//            }
        }))

    }

}
