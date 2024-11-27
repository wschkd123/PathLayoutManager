package com.wuyr.pathlayoutmanagertest

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

/**
 * kotlin 扩展函数
 *
 * @author p_jruixu
 */

/**
 * dp 转为 px
 */
fun Int.dpToPx(context: Context) = YWCommonUtil.dp2px(context, this.toFloat())

fun Int.dpToPxFloat(context: Context) = YWCommonUtil.dp2px(context, this.toFloat()).toFloat()

/**
 * 颜色资源 id 转为 颜色
 */
fun Int.resToColor(context: Context) = ContextCompat.getColor(context, this)

/**
 * 尺寸资源 id 转为 像素
 */
fun Int.resToDimen(context: Context) = context.resources.getDimension(this)

/**
 * 图片资源 id 转为 图片
 */
fun Int.resToDrawable(context: Context): Drawable? = context.resources.getDrawable(this)

/**
 * 文字资源 id 转为 文字
 */
fun Int.resToString(context: Context, vararg params: Any): String = context.getString(this, *params)