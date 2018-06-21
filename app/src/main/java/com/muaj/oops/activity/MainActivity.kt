package com.muaj.oops.activity

import android.support.v4.app.Fragment
import com.muaj.lib.base.BaseActivity
import com.muaj.lib.component.AppComponent
import com.muaj.oops.R
import com.muaj.oops.adapter.TabFragmentPagerAdapter
import com.muaj.oops.component.DaggerMainComponent
import com.muaj.oops.fragment.HomeFragment
import com.muaj.oops.databinding.ActivityMainBinding


/**
 * Created by muaj on 2018/6/6
 * Main Activity
 */
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private var tabAdapter: TabFragmentPagerAdapter? = null

    override val layoutId: Int
        get() = R.layout.activity_main

    override val hasStatusBar: Boolean
        get() = false

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
        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(HomeFragment())

        tabAdapter = TabFragmentPagerAdapter(supportFragmentManager, fragmentList)
        mBinding.viewpager.adapter = tabAdapter
    }

    override fun initToolBar() {}

    override fun initListener() {

    }

}
