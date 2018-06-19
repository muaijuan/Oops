package com.muaj.oops.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by muaj on 2018/6/6
 * FragmentPagerAdapter
 */

class TabFragmentPagerAdapter(fm: FragmentManager, private val fragments: List<Fragment>)
    : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}