package com.muaj.lib.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muaj.lib.component.AppComponent

/**
 * Created by muaj on 2018/6/6
 * Base Fragment
 */

abstract class BaseFragment<V : ViewDataBinding> : Fragment(), BaseContract.BaseView {

    protected lateinit var mBinding: V

    /**
     * 获取布局
     *
     * @return
     */
    @get:LayoutRes
    protected abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActivityComponent(App.appComponent)
        attachView()
        initDatas()
        initListener()
    }

    /**
     * 使用Dagger2对Activity进行注入
     *
     * @param appComponent
     */
    protected abstract fun setupActivityComponent(appComponent: AppComponent)

    /**
     * Presenter绑定View
     */
    abstract fun attachView()

    /**
     * 初始化数据
     */
    abstract fun initDatas()

    /**
     * 初始化监听事件
     */
    abstract fun initListener()

}
