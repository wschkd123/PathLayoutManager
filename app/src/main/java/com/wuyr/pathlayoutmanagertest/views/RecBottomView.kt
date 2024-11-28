package com.wuyr.pathlayoutmanagertest.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.wuyr.pathlayoutmanager.PathLayoutManager
import com.wuyr.pathlayoutmanagertest.R
import com.wuyr.pathlayoutmanagertest.adapters.PathAdapter
import com.wuyr.pathlayoutmanagertest.dpToPx
import java.util.Locale

/**
 *
 * @author wangshichao
 * @date 2024/11/27
 */
class RecBottomView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {

    val path = Path()
    private var recyclerView: RecyclerView
    private val mAdapter: PathAdapter = PathAdapter(context)
    private val mPathLayoutManager: PathLayoutManager = PathLayoutManager(null, 150)


    init {
        setWillNotDraw(false)
        recyclerView = RecyclerView(context).apply {
            layoutManager = mPathLayoutManager
            adapter = mAdapter
        }
        addView(recyclerView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        // 布局完成获取宽高后，初始化LayoutManager
        post {
            initLayoutManager()
        }
    }

    private fun initLayoutManager() {
        mAdapter.apply {
            val list = MutableList<String?>(8) { null }
            mAdapter.setData(list)
        }

        mPathLayoutManager.apply {
            updatePath(path)
            setScrollMode(PathLayoutManager.SCROLL_MODE_LOOP)
            setOrientation(RecyclerView.HORIZONTAL)
            setItemDirectionFixed(true)
            setAutoSelect(true)
            setFlingEnable(false)
            // 刚好显示5个
            setItemOffset(114.dpToPx(context))
            setAutoSelectFraction(0.5f)
            setFixingAnimationDuration(250)
            // 缩放和透明度
            val ratios = floatArrayOf(0.737f, 0f, 0.737f, 0.25f, 1f, 0.5f, 0.737f, 0.75f, 0.737f, 1f)
            setItemScaleRatio(*ratios)
            val alphas = floatArrayOf(0.3f, 0f, 0.7f, 0.25f, 1f, 0.5f, 0.7f, 0.75f, 0.3f, 1f)
            setItemAlpha(*alphas)

            setOnItemSelectedListener { position: Int ->
                Toast.makeText(context, String.format(Locale.getDefault(), "Item %d selected", position), Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w <= 0 || h <= 0) return
        val marginTop = 80.dpToPx(context).toFloat()
        val controlX = w / 2f
        val controlY = h.toFloat() + 16.dpToPx(context).toFloat()
        path.reset()
        path.moveTo(0f, marginTop)
        path.quadTo(controlX, controlY, w.toFloat(), marginTop)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
//        canvas?.drawPath(path, paint)
    }

}