package com.muaj.oops.fragment

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import com.muaj.lib.base.App

import com.muaj.lib.rv.RVAdapter
import com.muaj.lib.view.spark.RandomizedAdapter
import com.muaj.oops.R
import com.muaj.lib.base.BaseFragment
import com.muaj.lib.component.AppComponent
import com.muaj.lib.extensions.loge
import com.muaj.lib.extensions.logv
import com.muaj.oops.component.DaggerMainComponent
import com.muaj.oops.databinding.FragmentHomeBinding
import com.muaj.oops.item.CoinItem
import com.muaj.oops.module.ActivityModule

/**
 * Created by muaj on 2018/6/6
 * Home Fragment
 */

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val layoutId: Int
        get() = R.layout.fragment_home

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
        val adapter = RVAdapter()
        adapter.addItem(CoinItem())
        adapter.addItem(CoinItem())
        adapter.addItem(CoinItem())
        adapter.addItem(CoinItem())
        adapter.addItem(CoinItem())
        mBinding.recycl.layoutManager = LinearLayoutManager(context)
        mBinding.recycl.adapter = adapter

    }

    override fun initListener() {

        val totalScrollRange = IntArray(1)
        val scrimHeightTrigger = mBinding.collapsingLayout.scrimVisibleHeightTrigger

        val isShow = booleanArrayOf(false)
        mBinding.appBar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (totalScrollRange[0] == 0) {
                totalScrollRange[0] = appBarLayout.totalScrollRange
            }
            loge("verticalOffset:$verticalOffset")

            if (totalScrollRange[0] + verticalOffset < scrimHeightTrigger) {

                if (!isShow[0]) {
                    val bg = ContextCompat.getDrawable(App.mContext, R.drawable.gradient_bg)
                    val colorAnim = ObjectAnimator.ofInt(bg, "alpha", 0, 255)
                    colorAnim.duration = 800
                    colorAnim.addUpdateListener { mBinding.toolLayout.background = bg }
                    colorAnim.start()

                    isShow[0] = true
                }

            } else {
                isShow[0] = false
                mBinding.toolLayout.setBackgroundColor(Color.TRANSPARENT)
            }
        }

    }
}
