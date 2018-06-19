package com.muaj.lib.base

import android.annotation.TargetApi
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.muaj.lib.R
import com.muaj.lib.component.AppComponent
import com.muaj.lib.databinding.ActivityBaseBinding
import com.muaj.lib.util.StatusBarUtils
import com.muaj.lib.databinding.ViewstubToolbarBinding

/**
 * Created by muaj on 2018/6/6
 * Base Activity
 */

abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity(), BaseContract.BaseView {

    protected lateinit var mBinding: V
    private lateinit var mBaseBinding: ActivityBaseBinding
    private var mToolbarStubBinding: ViewstubToolbarBinding? = null

    private var hasStatusBarFlag = false
    private var hasToolStubFlag = false

    /**
     * 获取布局
     *
     * @return
     */
    @get:LayoutRes
    protected  abstract val  layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBaseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_base, null, false)
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(this), layoutId, null, false)
        mBaseBinding.rootLayout.addView(mBinding.root, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        //moveToBack() 打包运行不起作用 所以加上下面这句
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
            finish()
            return
        }
        setContentView(mBaseBinding.root)

        setupActivityComponent(App.appComponent)
        attachView()
        initDatas()

        hasStatusBarFlag = hasStatusBar()
        initStatusBar()

        hasToolStubFlag = hasToolStub()
        //在viewstub初始化的时候调用如下监听器
        mBaseBinding.toolbarViewstub.setOnInflateListener { _, inflated -> mToolbarStubBinding = DataBindingUtil.bind(inflated) }
        initToolStub()

        initListener()
    }

    /**
     * 使用Dagger2对Activity进行注入
     *
     * @param appComponent
     */
    abstract fun setupActivityComponent(appComponent: AppComponent)

    /**
     * Presenter绑定View
     */
    abstract fun attachView()

    /**
     * 初始化数据
     */
    abstract fun initDatas()

    /**
     * 初始化ToolBar
     */
    abstract fun initToolBar()

    /**
     * 初始化监听事件
     */
    abstract fun initListener()

    abstract fun hasStatusBar(): Boolean

    abstract fun hasToolStub(): Boolean


    /**===================== StatusBar ====================== */

    /**
     * init status bar
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun initStatusBar() {
        if (hasStatusBarFlag) {
            StatusBarUtils.setRootFitsSystemWindows(this,true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                StatusBarUtils.setColor(this, Color.WHITE)
                StatusBarUtils.setStatusBarDark(this, true)
            } else {
                StatusBarUtils.setTranslucent(this)
            }
        } else {
            //set status bar transparent
            StatusBarUtils.setTransparent(this)
            StatusBarUtils.setRootFitsSystemWindows(this, false)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                StatusBarUtils.setStatusBarDark(this, true)
            }
        }
    }


    /**===================== ToolBar ====================== */

    /**
     * init toolbar
     */
    private fun initToolStub() {
        if (hasToolStubFlag && mToolbarStubBinding == null) {
            inflateToolbarViewStub()
        }
        if (mToolbarStubBinding != null) {
            mToolbarStubBinding!!.commonToolbar.visibility = if (hasToolStubFlag) View.VISIBLE else View.GONE
        }
    }

    /**
     * if show,inflate toolbar viewstub
     */
    private fun inflateToolbarViewStub() {
        if (!mBaseBinding.toolbarViewstub.isInflated) {
            mBaseBinding.toolbarViewstub.viewStub!!.inflate()
            initToolBar()
        }
    }
}