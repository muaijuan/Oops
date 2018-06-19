package com.muaj.lib.view.spark


import android.annotation.TargetApi
import android.content.Context
import android.content.res.TypedArray
import android.database.DataSetObserver
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Shader
import android.os.Build
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.view.View
import com.muaj.lib.R
import java.util.ArrayList

/**
 * spark line view
 *
 * @author muaj
 * @date 2018/5/30
 */

class SparkView : View {

    // styleable values
    @ColorInt
    private var lineColor: Int = 0
    private var lineWidth: Float = 0.toFloat()
    private var cornerRadius: Float = 0.toFloat()
    @ColorInt
    private var shaderColor: Int = 0
    private var isShader: Boolean = false
    private var isPlayAnimate: Boolean = false

    private val mLinePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mShaderPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    // the onDraw data
    private var shaderPath = Path()
    private var linePath = Path()

    private val contentRect = RectF()
    private var xPoints: MutableList<Float>? = null
    private var yPoints: MutableList<Float>? = null

    // adapter
    /**
     * Get the backing [SparkAdapter]
     */
    /**
     * Sets the backing [SparkAdapter] to generate the points to be graphed
     */
    var adapter: SparkAdapter? = null
        set(adapter) {
            if (this.adapter != null) {
                this.adapter!!.unregisterDataSetObserver(dataSetObserver)
            }
            field = adapter
            if (this.adapter != null) {
                this.adapter!!.registerDataSetObserver(dataSetObserver)
            }

            populatePath()
        }

    // misc fields
    private var scaleHelper: ScaleHelper? = null

    private var animator: SparkAnimator? = null

    private val dataSetObserver = object : DataSetObserver() {
        override fun onChanged() {
            super.onChanged()
            populatePath()
        }

        override fun onInvalidated() {
            super.onInvalidated()
            clearData()
        }
    }

    constructor(context: Context) : super(context) {
        init(context, null, R.attr.spark_SparkViewStyle, R.style.spark_SparkView)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, R.attr.spark_SparkViewStyle, R.style.spark_SparkView)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr, R.style.spark_SparkView)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SparkView,
                defStyleAttr, defStyleRes)

        lineColor = a.getColor(R.styleable.SparkView_spark_lineColor, 0)
        lineWidth = a.getDimension(R.styleable.SparkView_spark_lineWidth, 0f)
        cornerRadius = a.getDimension(R.styleable.SparkView_spark_cornerRadius, 0f)

        isShader = a.getBoolean(R.styleable.SparkView_spark_shader, false)
        shaderColor = a.getColor(R.styleable.SparkView_spark_shaderColor, 0)

        isPlayAnimate = a.getBoolean(R.styleable.SparkView_spark_playAnimate, false)

        a.recycle()

        mLinePaint.style = Paint.Style.STROKE
        mLinePaint.color = lineColor
        mLinePaint.strokeWidth = lineWidth
        mLinePaint.strokeCap = Paint.Cap.ROUND
        if (cornerRadius != 0f) {
            mLinePaint.pathEffect = CornerPathEffect(cornerRadius)
        }

        mShaderPaint.set(mLinePaint)
        mShaderPaint.color = shaderColor
        mShaderPaint.style = Paint.Style.FILL
        mShaderPaint.strokeWidth = 0f

        xPoints = ArrayList()
        yPoints = ArrayList()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateContentRect()
        populatePath()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(linePath, mLinePaint)

        if (isShader) {
            canvas.drawPath(shaderPath, mShaderPaint)
        }
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(left, top, right, bottom)
        updateContentRect()
        populatePath()
    }

    /**
     * Gets the rect representing the 'content area' of the view. This is essentially the bounding
     * rect minus any padding.
     */
    private fun updateContentRect() {
        contentRect.set(
                paddingStart.toFloat(),
                paddingTop.toFloat(),
                (width - paddingEnd).toFloat(),
                (height - paddingBottom).toFloat()
        )
    }

    /**
     * Populates the [.linePath] with points
     */
    private fun populatePath() {
        if (this.adapter == null) return
        if (width == 0 || height == 0) return

        val adapterCount = this.adapter!!.getCount()

        // to draw anything, we need 2 or more points
        if (adapterCount < 2) {
            clearData()
            return
        }

        scaleHelper = ScaleHelper(this.adapter!!, contentRect, lineWidth)

        xPoints!!.clear()
        yPoints!!.clear()

        // make our main graph path
        linePath.reset()
        for (i in 0 until adapterCount) {
            val x = scaleHelper!!.getX(this.adapter!!.getX(i))
            val y = scaleHelper!!.getY(this.adapter!!.getY(i))

            // points to render graphic
            // get points to animate
            xPoints!!.add(x)
            yPoints!!.add(y)

            if (i == 0) {
                linePath.moveTo(x, y)
            } else {
                linePath.lineTo(x, y)
            }

        }

        shaderPath.reset()
        shaderPath.addPath(linePath)
        val lastX = scaleHelper!!.getX((this.adapter!!.getCount() - 1).toFloat())

        if (isShader) {
            shaderPath.lineTo(lastX, height.toFloat())
            shaderPath.lineTo(paddingStart.toFloat(), height.toFloat())
            shaderPath.close()

            val mLinearGradient = LinearGradient(0f, 0f, 0f, height.toFloat(),
                    shaderColor, Color.TRANSPARENT, Shader.TileMode.CLAMP)
            mShaderPaint.shader = mLinearGradient
        }

        if (isPlayAnimate && animator != null) {
            animator!!.getAnimator()!!.start()
        } else {
            invalidate()
        }
    }

    private fun clearData() {
        scaleHelper = null
        shaderPath.reset()
        linePath.reset()
        invalidate()
    }

    override fun getPaddingStart(): Int {
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1)
            super.getPaddingStart()
        else
            paddingLeft
    }

    override fun getPaddingEnd(): Int {
        return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1)
            super.getPaddingEnd()
        else
            paddingRight
    }

    /**
     * Helper class for handling scaling logic.
     */
    internal class ScaleHelper(adapter: SparkAdapter, contentRect: RectF, lineWidth: Float) {
        // the width and height of the view
        val width: Float
        val height: Float
        val size: Int
        // the scale factor for the Y values
        val xScale: Float
        val yScale: Float
        // translates the Y values back into the bounding rect after being scaled
        val xTranslation: Float
        val yTranslation: Float

        init {
            val leftPadding = contentRect.left
            val topPadding = contentRect.top

            // subtract lineWidth to offset for 1/2 of the line bleeding out of the content box on
            // either side of the view
            //            final float lineWidthOffset = fill ? 0 : lineWidth;
            val lineWidthOffset = 0f
            this.width = contentRect.width() - lineWidthOffset
            this.height = contentRect.height() - lineWidthOffset

            this.size = adapter.getCount()

            // get data bounds from adapter
            val bounds = adapter.dataBounds

            // if data is a line (which technically has no size), expand bounds to center the data
            bounds.inset((if (bounds.width() == 0f) -1 else 0).toFloat(), (if (bounds.height() == 0f) -1 else 0).toFloat())

            val minX = bounds.left
            val maxX = bounds.right
            val minY = bounds.top
            val maxY = bounds.bottom

            // xScale will compress or expand the min and max x values to be just inside the view
            this.xScale = width / (maxX - minX)
            // xTranslation will move the x points back between 0 - width
            this.xTranslation = leftPadding - minX * xScale + lineWidthOffset / 2
            // yScale will compress or expand the min and max y values to be just inside the view
            this.yScale = height / (maxY - minY)
            // yTranslation will move the y points back between 0 - height
            this.yTranslation = minY * yScale + topPadding + lineWidthOffset / 2
        }

        /**
         * Given the 'raw' X value, scale it to fit within our view.
         */
        fun getX(rawX: Float): Float {
            return rawX * xScale + xTranslation
        }

        /**
         * Given the 'raw' Y value, scale it to fit within our view. This method also 'flips' the
         * value to be ready for drawing.
         */
        fun getY(rawY: Float): Float {
            return height - rawY * yScale + yTranslation
        }
    }


    /**
     * Set the color of the sparkline
     */
    fun setLineColor(@ColorInt lineColor: Int) {
        this.lineColor = lineColor
        mLinePaint.color = lineColor
        invalidate()
    }

    /**
     * Set the color of the render
     */
    fun setShaderColor(@ColorInt fillColor: Int) {
        this.shaderColor = fillColor
        mShaderPaint.color = fillColor
        invalidate()
    }

    /**
     * Set the width in pixels of the sparkline's stroke
     */
    fun setLineWidth(lineWidth: Float) {
        this.lineWidth = lineWidth
        mLinePaint.strokeWidth = lineWidth
        invalidate()
    }

    fun setPlayAnimate(isPlayAnimate: Boolean) {
        this.isPlayAnimate = isPlayAnimate
    }

    /**
     * Set the corner radius in pixels to use when rounding the sparkline's segments. Passing 0
     * indicates that corners should not be rounded.
     */
    fun setCornerRadius(cornerRadius: Float) {
        this.cornerRadius = cornerRadius
        if (cornerRadius != 0f) {
            mLinePaint.pathEffect = CornerPathEffect(cornerRadius)
            mShaderPaint.pathEffect = CornerPathEffect(cornerRadius)
        } else {
            mLinePaint.pathEffect = null
            mShaderPaint.pathEffect = null
        }
        invalidate()
    }

    /**
     * set view's animator
     *
     * @param animator
     */
    fun setAnimator(animator: SparkAnimator) {
        this.animator = animator
    }

     fun getLinePath(): Path {
        return Path(linePath)
    }

     fun getShaderPath(): Path {
        return Path(shaderPath)
    }


    fun setAnimPath(linePath: Path, pos: FloatArray) {
        this.linePath.reset()
        this.linePath.addPath(linePath)
        this.linePath.rLineTo(0f, 0f)

        this.shaderPath.reset()
        this.shaderPath.addPath(linePath)
        this.shaderPath.rLineTo(0f, 0f)
        this.shaderPath.lineTo(pos[0], height.toFloat())
        this.shaderPath.lineTo(paddingStart.toFloat(), height.toFloat())
        this.shaderPath.close()

        invalidate()
    }

}
