package com.wuyr.pathlayoutmanagertest.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.wuyr.pathlayoutmanagertest.dpToPxFloat

/**
 * @author wangshichao
 * @date 2024/11/27
 *
 * 底部弧线剪裁的图片
 */
class RecCutImageView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : AppCompatImageView(context, attributeSet) {
    private val path = Path()
    private val targetAspectRatio = 1.44f
    private val cornerRadius: Float = 16.dpToPxFloat(context)

    /**
     * 底部三角形高度
     */
    private val triangleHeight = 106.dpToPxFloat(context)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w <= 0 || h <= 0) return

        var width:Float = w.toFloat()
        var height: Float = h.toFloat()
        if (h.div(w.toFloat()) > targetAspectRatio) {
            width = h.div(targetAspectRatio)
        } else {
            //TODO
        }
        createPath(width, height)
    }

    private fun createPath(w: Float, h: Float) {
        path.reset()
        //左上角圆角
        path.moveTo(0f, cornerRadius)
        path.quadTo(0f, 0f, cornerRadius, 0f)
        //上边
        path.lineTo((w - cornerRadius), 0f)
        //右上角圆角
        path.quadTo(w, 0f, w, cornerRadius)
        //右边
        path.lineTo(w, h - cornerRadius - triangleHeight)
        //右下角圆角
        path.quadTo(w, h - triangleHeight, w.div(2), h)
        //底部弧线
        path.quadTo(w.div(2), h, 0f, h - triangleHeight)
        //左下角圆角
        path.quadTo(0f, h - triangleHeight, 0f, h - cornerRadius - triangleHeight)
        path.close()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.clipPath(path)
        super.onDraw(canvas)
    }

}