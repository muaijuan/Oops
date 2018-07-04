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
    protected lateinit var mBaseBinding: ActivityBaseBinding
    protected var mToolbarStubBinding: ViewstubToolbarBinding? = null

    /**
     * 获取布局
     *
     * @return
     */
    @get:LayoutRes
    protected abstract val layoutId: Int

    /**
     * 是否有状态栏
     * 若true则页面不占据状态栏位置，若false则占据状态栏位置
     * @return
     */
    protected abstract val hasStatusBar: Boolean

    /**
     * 是否有toolbar
     *
     * @return
     */
    protected abstract val hasToolStub: Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBaseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_base, null, false)
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(this),layoutId , null, false)
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
        initStatusBar()

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

    open fun setStatusBar(){}

    /**===================== StatusBar ====================== */

    /**
     * init status bar
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private fun initStatusBar() {
        if (hasStatusBar) {
            StatusBarUtils.setRootFitsSystemWindows(this, true)
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
        setStatusBar()
    }


    /**===================== ToolBar ====================== */

    /**
     * init toolbar
     */
    private fun initToolStub() {
        if (hasToolStub && mToolbarStubBinding == null) {
            inflateToolbarViewStub()
        }
        if (mToolbarStubBinding != null) {
            mToolbarStubBinding!!.commonToolbar.visibility = if (hasToolStub) View.VISIBLE else View.GONE
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
