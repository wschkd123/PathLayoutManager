package com.wuyr.pathlayoutmanagertest.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * Created by wuyr on 18-5-22 下午10:32.
 */
class CanvasView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(mBitmap, 0f, 0f, null)
    }

    private var mPath: Path? = Path()
    private val mPaint = Paint()
    private var mBitmap: Bitmap? = null
    private var mCanvas: Canvas? = null

    init {
        mPaint.isAntiAlias = true
        mPaint.color = Color.BLUE
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 10f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444)
        mCanvas = Canvas(mBitmap!!)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled) {
            return false
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> mPath!!.moveTo(event.x, event.y)
            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> mPath!!.lineTo(event.x, event.y)
        }
        mCanvas!!.drawPath(mPath, mPaint)
        invalidate()
        return true
    }

    var path: Path?
        get() = mPath
        set(path) {
            mPath = path
            mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444)
            mCanvas = Canvas(mBitmap!!)
            mCanvas!!.drawPath(mPath!!, mPaint)
            invalidate()
        }

    fun clear() {
        path = Path()
    }
}
