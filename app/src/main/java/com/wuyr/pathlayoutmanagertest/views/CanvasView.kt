package com.wuyr.pathlayoutmanagertest.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.wuyr.pathlayoutmanagertest.dpToPx

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

    private var mPath: Path = Path()
    private val mPaint = Paint()

    val start = PointF(0f,0f)
    val end = PointF(0f,0f)
    val control = PointF(0f,0f)

    init {
        mPaint.isAntiAlias = true
        mPaint.color = Color.BLUE
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 10f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // 初始化数据点和控制点的位置
        val centerX = w / 2
        val centerY = h / 2
        start.x = 0f
        start.y = 97.dpToPx(context).toFloat()
        end.x = w.toFloat()
        end.y = 97.dpToPx(context).toFloat()
        control.x = centerX.toFloat()
        control.y = h.toFloat() + 16.dpToPx(context).toFloat()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // 根据触摸位置更新控制点，并提示重绘
        control.x = event.x
        control.y = event.y
        invalidate()
        return true
    }

    var path: Path
        get() = mPath
        set(path) {
            mPath = path
            invalidate()
        }

    fun clear() {
        path.reset()
    }

    override fun onDraw(canvas: Canvas) {
        // 绘制数据点和控制点
        mPaint.color = Color.GRAY
        mPaint.strokeWidth = 20f
        canvas.drawPoint(start.x, start.y, mPaint)
        canvas.drawPoint(end.x, end.y, mPaint)
        canvas.drawPoint(control.x, control.y, mPaint)

        // 绘制辅助线
        mPaint.strokeWidth = 4f
        canvas.drawLine(start.x, start.y, control.x, control.y, mPaint)
        canvas.drawLine(end.x, end.y, control.x, control.y, mPaint)


        // 绘制贝塞尔曲线
        mPaint.color = Color.RED
        mPaint.strokeWidth = 8f
        path.reset()
        mPath.moveTo(start.x, start.y)
        mPath.quadTo(control.x, control.y, end.x, end.y)
        canvas.drawPath(mPath, mPaint)
    }
}
