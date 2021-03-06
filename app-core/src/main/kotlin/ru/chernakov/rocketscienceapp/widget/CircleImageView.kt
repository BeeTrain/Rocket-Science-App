package ru.chernakov.rocketscienceapp.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Matrix
import android.graphics.Outline
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import ru.chernakov.rocketscienceapp.core.R
import ru.chernakov.rocketscienceapp.extension.android.content.getColorKtx
import kotlin.math.min
import kotlin.math.pow

@Suppress("TooGenericExceptionCaught", "MagicNumber")
class CircleImageView : AppCompatImageView {
    private val drawableRect = RectF()
    private val borderRect = RectF()
    private val shaderMatrix = Matrix()
    private val bitmapPaint = Paint()
    private val borderPaint = Paint()
    private val circleBackgroundPaint = Paint()
    private var mBorderColor = DEFAULT_BORDER_COLOR
    private var mBorderWidth = DEFAULT_BORDER_WIDTH
    private var mCircleBackgroundColor = DEFAULT_CIRCLE_BACKGROUND_COLOR
    private var mBitmap: Bitmap? = null
    private var bitmapShader: BitmapShader? = null
    private var bitmapWidth: Int = 0
    private var bitmapHeight: Int = 0
    private var drawableRadius: Float = 0.toFloat()
    private var borderRadius: Float = 0.toFloat()
    private var colorFilter: ColorFilter? = null
    private var ready: Boolean = false
    private var setupPending: Boolean = false
    private var mBorderOverlay: Boolean = false
    var isDisableCircularTransformation: Boolean = false
        set(disableCircularTransformation) {
            if (isDisableCircularTransformation == disableCircularTransformation) {
                return
            }
            field = disableCircularTransformation
            initializeBitmap()
        }

    var borderColor: Int
        get() = mBorderColor
        set(@ColorInt borderColor) {
            if (borderColor == mBorderColor) {
                return
            }
            mBorderColor = borderColor
            borderPaint.color = mBorderColor
            invalidate()
        }

    var circleBackgroundColor: Int
        get() = mCircleBackgroundColor
        set(@ColorInt circleBackgroundColor) {
            if (circleBackgroundColor == mCircleBackgroundColor) {
                return
            }
            mCircleBackgroundColor = circleBackgroundColor
            circleBackgroundPaint.color = circleBackgroundColor
            invalidate()
        }

    var borderWidth: Int
        get() = mBorderWidth
        set(borderWidth) {
            if (borderWidth == mBorderWidth) {
                return
            }
            mBorderWidth = borderWidth
            setup()
        }

    var isBorderOverlay: Boolean
        get() = mBorderOverlay
        set(borderOverlay) {
            if (borderOverlay == mBorderOverlay) {
                return
            }
            mBorderOverlay = borderOverlay
            setup()
        }

    constructor(context: Context) : super(context) {
        init()
    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet, defStyle: Int = 0) : super(
        context,
        attrs,
        defStyle
    ) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyle, 0)
        mBorderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_border_width, DEFAULT_BORDER_WIDTH)
        mBorderColor = a.getColor(R.styleable.CircleImageView_border_color, DEFAULT_BORDER_COLOR)
        mBorderOverlay = a.getBoolean(R.styleable.CircleImageView_border_overlay, DEFAULT_BORDER_OVERLAY)
        mCircleBackgroundColor = a.getColor(
            R.styleable.CircleImageView_circle_background_color,
            DEFAULT_CIRCLE_BACKGROUND_COLOR
        )
        a.recycle()
        init()
    }

    private fun init() {
        super.setScaleType(SCALE_TYPE)
        ready = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            outlineProvider = OutlineProvider()
        }
        if (setupPending) {
            setup()
            setupPending = false
        }
    }

    override fun getScaleType(): ScaleType {
        return SCALE_TYPE
    }

    override fun setScaleType(scaleType: ScaleType) {
        require(scaleType == SCALE_TYPE) { String.format("ScaleType %s not supported.", scaleType) }
    }

    override fun setAdjustViewBounds(adjustViewBounds: Boolean) {
        require(!adjustViewBounds) { "adjustViewBounds not supported." }
    }

    override fun onDraw(canvas: Canvas) {
        if (isDisableCircularTransformation) {
            super.onDraw(canvas)
            return
        }
        if (mBitmap == null) {
            return
        }
        if (mCircleBackgroundColor != Color.TRANSPARENT) {
            canvas.drawCircle(
                drawableRect.centerX(),
                drawableRect.centerY(),
                drawableRadius,
                circleBackgroundPaint
            )
        }
        canvas.drawCircle(
            drawableRect.centerX(),
            drawableRect.centerY(),
            drawableRadius,
            bitmapPaint
        )
        if (mBorderWidth > 0) {
            canvas.drawCircle(
                borderRect.centerX(),
                borderRect.centerY(),
                borderRadius,
                borderPaint
            )
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setup()
    }

    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        super.setPadding(left, top, right, bottom)
        setup()
    }

    override fun setPaddingRelative(start: Int, top: Int, end: Int, bottom: Int) {
        super.setPaddingRelative(start, top, end, bottom)
        setup()
    }

    fun setCircleBackgroundColorResource(@ColorRes circleBackgroundRes: Int) {
        circleBackgroundColor = context.getColorKtx(circleBackgroundRes)
    }

    override fun setImageBitmap(bm: Bitmap) {
        super.setImageBitmap(bm)
        initializeBitmap()
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        initializeBitmap()
    }

    override fun setImageResource(@DrawableRes resId: Int) {
        super.setImageResource(resId)
        initializeBitmap()
    }

    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
        initializeBitmap()
    }

    override fun setColorFilter(cf: ColorFilter) {
        if (cf === colorFilter) {
            return
        }
        colorFilter = cf
        applyColorFilter()
        invalidate()
    }

    override fun getColorFilter(): ColorFilter? {
        return colorFilter
    }

    private fun applyColorFilter() {
        bitmapPaint.colorFilter = colorFilter
    }

    @Suppress("ReturnCount")
    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        try {
            val bitmap: Bitmap = if (drawable is ColorDrawable) {
                Bitmap.createBitmap(
                    COLORDRAWABLE_DIMENSION,
                    COLORDRAWABLE_DIMENSION,
                    BITMAP_CONFIG
                )
            } else {
                Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    BITMAP_CONFIG
                )
            }
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun initializeBitmap() {
        if (isDisableCircularTransformation) {
            mBitmap = null
        } else {
            mBitmap = getBitmapFromDrawable(drawable)
        }
        setup()
    }

    private fun setup() {
        if (!ready) {
            setupPending = true
            return
        }
        if (width == 0 && height == 0) {
            return
        }
        if (mBitmap == null) {
            invalidate()
            return
        }
        bitmapShader = BitmapShader(mBitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        bitmapPaint.isAntiAlias = true
        bitmapPaint.shader = bitmapShader
        borderPaint.style = Paint.Style.STROKE
        borderPaint.isAntiAlias = true
        borderPaint.color = mBorderColor
        borderPaint.strokeWidth = mBorderWidth.toFloat()
        circleBackgroundPaint.style = Paint.Style.FILL
        circleBackgroundPaint.isAntiAlias = true
        circleBackgroundPaint.color = mCircleBackgroundColor
        bitmapHeight = mBitmap!!.height
        bitmapWidth = mBitmap!!.width
        borderRect.set(calculateBounds())
        borderRadius = Math.min(
            (borderRect.height() - mBorderWidth) / 2.0f,
            (borderRect.width() - mBorderWidth) / 2.0f
        )
        drawableRect.set(borderRect)
        if (!mBorderOverlay && mBorderWidth > 0) {
            drawableRect.inset(mBorderWidth - 1.0f, mBorderWidth - 1.0f)
        }
        drawableRadius = Math.min(drawableRect.height() / 2.0f, drawableRect.width() / 2.0f)
        applyColorFilter()
        updateShaderMatrix()
        invalidate()
    }

    private fun calculateBounds(): RectF {
        val availableWidth = width - paddingLeft - paddingRight
        val availableHeight = height - paddingTop - paddingBottom
        val sideLength = min(availableWidth, availableHeight)
        val left = paddingLeft + (availableWidth - sideLength) / 2f
        val top = paddingTop + (availableHeight - sideLength) / 2f

        return RectF(left, top, left + sideLength, top + sideLength)
    }

    private fun updateShaderMatrix() {
        val scale: Float
        var dx = 0f
        var dy = 0f
        shaderMatrix.set(null)
        if (bitmapWidth * drawableRect.height() > drawableRect.width() * bitmapHeight) {
            scale = drawableRect.height() / bitmapHeight.toFloat()
            dx = (drawableRect.width() - bitmapWidth * scale) * 0.5f
        } else {
            scale = drawableRect.width() / bitmapWidth.toFloat()
            dy = (drawableRect.height() - bitmapHeight * scale) * 0.5f
        }
        shaderMatrix.setScale(scale, scale)
        shaderMatrix.postTranslate(
            (dx + 0.5f).toInt() + drawableRect.left,
            (dy + 0.5f).toInt() + drawableRect.top
        )
        bitmapShader!!.setLocalMatrix(shaderMatrix)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return inTouchableArea(event.x, event.y) && super.onTouchEvent(event)
    }

    private fun inTouchableArea(x: Float, y: Float): Boolean {
        return (x - borderRect.centerX()).toDouble().pow(2.0) +
            (y - borderRect.centerY()).toDouble().pow(2.0) <=
            borderRadius.toDouble().pow(2.0)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private inner class OutlineProvider : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            val bounds = Rect()
            borderRect.roundOut(bounds)
            outline.setRoundRect(bounds, bounds.width() / 2.0f)
        }
    }

    companion object {
        private val SCALE_TYPE = ScaleType.CENTER_CROP
        private val BITMAP_CONFIG = Bitmap.Config.ARGB_8888
        private val COLORDRAWABLE_DIMENSION = 2
        private val DEFAULT_BORDER_WIDTH = 0
        private val DEFAULT_BORDER_COLOR = Color.BLACK
        private val DEFAULT_CIRCLE_BACKGROUND_COLOR = Color.TRANSPARENT
        private val DEFAULT_BORDER_OVERLAY = false
    }
}