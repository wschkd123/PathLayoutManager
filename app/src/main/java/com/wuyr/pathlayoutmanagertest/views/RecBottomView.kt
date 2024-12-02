package com.wuyr.pathlayoutmanagertest.views

import android.content.Context
import android.graphics.Path
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.wuyr.pathlayoutmanager.PathLayoutManager
import com.wuyr.pathlayoutmanagertest.adapters.RecPathAdapter
import com.wuyr.pathlayoutmanagertest.dpToPx
import com.wuyr.pathlayoutmanagertest.dpToPxFloat
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

    private val pathStartY = 40.dpToPxFloat(context)
    val path = Path()
    private var recyclerView: RecyclerView
    private val mAdapter: RecPathAdapter = RecPathAdapter(context)
    private val mPathLayoutManager: PathLayoutManager = PathLayoutManager(null, 150, RecyclerView.HORIZONTAL)


    init {
        setWillNotDraw(false)
        recyclerView = RecyclerView(context).apply {
            layoutManager = mPathLayoutManager
            adapter = mAdapter
        }
        addView(recyclerView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))
        initLayoutManager()

        // 布局完成获取宽高后，初始化LayoutManager
        post {
            setData()
        }
    }

    fun setData() {
        mPathLayoutManager.updatePath(path)
        val list = MutableList<String?>(10) { null }
        mAdapter.setData(list)
        mPathLayoutManager.setItemOffset(90.dpToPx(context))
        setScrollMode()
        mPathLayoutManager.scrollToPosition(0)
    }

    private fun initLayoutManager() {
        mPathLayoutManager.apply {
            setItemDirectionFixed(true)
            setAutoSelect(true)
            setFlingEnable(false)
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

    private fun setScrollMode() {
        if (mPathLayoutManager.canLoopScroll()) {
            mPathLayoutManager.setScrollMode(PathLayoutManager.SCROLL_MODE_LOOP)
        } else {
            mPathLayoutManager.setScrollMode(PathLayoutManager.SCROLL_MODE_OVERFLOW)
        }
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w <= 0 || h <= 0) return
        val controlX = w / 2f
        val controlY = h.toFloat() + 16.dpToPx(context).toFloat()
        path.reset()
        path.moveTo(0f, pathStartY)
        path.quadTo(controlX, controlY, w.toFloat(), pathStartY)
    }


}