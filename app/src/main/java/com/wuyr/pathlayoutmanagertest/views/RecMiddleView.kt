package com.wuyr.pathlayoutmanagertest.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.wuyr.pathlayoutmanagertest.R

/**
 * @author wangshichao
 * @date 2024/11/27
 *
 *
 */
class RecMiddleView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {
    init {
        LayoutInflater.from(context).inflate(R.layout.char_view_rec_middle, this)
    }


}