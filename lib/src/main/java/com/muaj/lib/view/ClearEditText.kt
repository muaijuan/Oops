package com.muaj.lib.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import com.muaj.lib.R
import com.muaj.lib.extensions.loadDrawable

/**
 * Created by muaj on 2018/6/21
 * 一键清除EditText
 */
class ClearEditText @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    /**
     * 删除按钮的引用
     */
    private var mClearDrawable: Drawable? = null
    /**
     * 控件是否有焦点
     */
    private var hasFocusFlag: Boolean = false

    private var textChangeListener: OnTextChangeListener? = null
    private var focusChangeListener: OnFocusChangeListener? = null


    init {
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = compoundDrawables[2]
        if (mClearDrawable == null) {
            //          throw new NullPointerException("You can add drawableRight attribute in XML");
            mClearDrawable = loadDrawable(R.drawable.svg_ic_clear)
        }

        mClearDrawable!!.setBounds(0, 0, mClearDrawable!!.intrinsicWidth, mClearDrawable!!.intrinsicHeight)
        //默认设置隐藏图标
        setClearIconVisible(false)
        //设置焦点改变的监听
        onFocusChangeListener = FocusChangeListenerImpl()
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(TextWatchImpl())
    }

    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            if (compoundDrawables[2] != null) {

                val touchable = event.x > width - totalPaddingRight && event.x < width - paddingRight

                if (touchable) {
                    this.setText("")
                }
            }
        }

        return super.onTouchEvent(event)
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    private fun setClearIconVisible(visible: Boolean) {
        val right = if (visible) mClearDrawable else null
        setCompoundDrawables(compoundDrawables[0],
                compoundDrawables[1], right, compoundDrawables[3])
    }

    /**
     * 设置晃动动画
     */
    fun setShakeAnimation() {
        this.animation = shakeAnimation(5)
    }

    /**
     * 晃动动画
     *
     * @param counts 1秒钟晃动多少下
     * @return
     */
    private fun shakeAnimation(counts: Int): Animation {
        val translateAnimation = TranslateAnimation(0f, 10f, 0f, 0f)
        translateAnimation.interpolator = CycleInterpolator(counts.toFloat())
        translateAnimation.duration = 1000
        return translateAnimation
    }

    /*============================ Impl ================================================*/

    fun setTextChangeListener(listener: OnTextChangeListener) {
        this.textChangeListener = listener
    }

    fun setFocusChangeListener(listener: OnFocusChangeListener) {
        this.focusChangeListener = listener
    }

    interface OnTextChangeListener {
        fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int)

        fun afterTextChanged(editable: Editable)
    }

    interface OnFocusChangeListener {
        fun onFocusChanged(v: View, hasFocus: Boolean)
    }

    private inner class TextWatchImpl : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            textChangeListener?.onTextChanged(s, start, before, count)
        }

        override fun afterTextChanged(editable: Editable) {
            /*判断输入框有没有内容，设置是否显示删除按钮*/
            setClearIconVisible(if (hasFocusFlag) text.toString().trim { it <= ' ' }.isNotEmpty() else false)
            textChangeListener?.afterTextChanged(editable)
        }
    }

    private inner class FocusChangeListenerImpl : View.OnFocusChangeListener {
        override fun onFocusChange(v: View, hasFocus: Boolean) {
            hasFocusFlag = hasFocus
            setClearIconVisible(if (hasFocus) text.toString().trim { it <= ' ' }.isNotEmpty() else false)
            focusChangeListener?.onFocusChanged(v, hasFocus)
        }

    }

}