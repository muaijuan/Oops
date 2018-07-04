package com.muaj.oops.component

import com.muaj.lib.component.AppComponent
import com.muaj.oops.activity.WalletHomeActivity
import com.muaj.oops.activity.MainActivity
import com.muaj.oops.activity.CreateWalletActivity
import com.muaj.oops.activity.SplashscreenActivity
import com.muaj.oops.fragment.HomeFragment
import com.muaj.oops.module.ActivityModule
import dagger.Component

/**
 * Created by muaj on 2018/6/6
 *
 */
@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class])
interface MainComponent {
    fun inject(activity: MainActivity)

    fun inject(fragment: HomeFragment)

    fun inject(activity: WalletHomeActivity)

    fun inject(activity: CreateWalletActivity)

    fun inject(activity: SplashscreenActivity)
}
