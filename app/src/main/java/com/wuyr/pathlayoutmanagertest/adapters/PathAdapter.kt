package com.wuyr.pathlayoutmanagertest.adapters

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.wuyr.pathlayoutmanagertest.R
import java.util.Locale
import java.util.Random

/**
 * Created by wuyr on 18-5-20 上午4:09.
 */
class PathAdapter(
    context: Context, data: List<Any>?
) : BaseAdapter<Any?, PathAdapter.ViewHolder>(
        context,
        data,
        R.layout.adapter_item_view,
        ViewHolder::class.java
    ) {
    private val mToast: Toast
    private var mCurrentType = 0
    private val mRandom = Random()
    private var mBitmapList: MutableList<Bitmap>? = null

    init {
        mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT)
        initBitmaps()
    }

    private fun initBitmaps() {
        mBitmapList = mutableListOf(
            getBitmapById(R.drawable.ic_1),
            getBitmapById(R.drawable.ic_2),
            getBitmapById(R.drawable.ic_3),
            getBitmapById(R.drawable.ic_j20),
            getBitmapById(R.drawable.ic_dragon_head),
            getBitmapById(R.drawable.ic_dragon_body_1),
            getBitmapById(R.drawable.ic_dragon_body_2),
            getBitmapById(R.drawable.ic_dragon_tail)
        )
    }

    private fun getBitmapById(id: Int): Bitmap {
        return decodeSampledBitmapFromResource(mContext.resources, id)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        recyclerBitmaps()
    }

    private fun recyclerBitmaps() {
        mBitmapList!!.clear()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (mCurrentType) {
            TYPE_CARD -> initCardHolder(holder)
            TYPE_J20 -> initJ20Holder(holder)
            TYPE_DRAGON -> initDragonHolder(holder, position)
            else -> {}
        }
        holder.itemView.setOnClickListener { v: View? ->
            mToast.setText(
                String.format(
                    Locale.getDefault(),
                    "item %s clicked", holder.adapterPosition
                )
            )
            mToast.show()
        }
    }

    private fun initCardHolder(holder: ViewHolder) {
        holder.imageView.visibility = View.VISIBLE
        holder.imageView2.setImageBitmap(null)
        holder.imageView2.visibility = View.GONE
        holder.imageView.layoutParams.width = 360
        holder.imageView.requestLayout()
        holder.imageView.setImageBitmap(getBitmap(mRandom.nextInt(3)))
    }

    private fun initJ20Holder(holder: ViewHolder) {
        holder.imageView2.visibility = View.VISIBLE
        holder.imageView.setImageBitmap(null)
        holder.imageView.visibility = View.GONE
        holder.imageView2.layoutParams.width = 180
        holder.imageView2.requestLayout()
        holder.imageView2.setImageBitmap(getBitmap(3))
    }

    private fun initDragonHolder(holder: ViewHolder, position: Int) {
        holder.imageView2.visibility = View.VISIBLE
        holder.imageView.setImageBitmap(null)
        holder.imageView.visibility = View.GONE
        holder.imageView2.layoutParams.width = 135
        holder.imageView2.requestLayout()
        if (position == 10) {
            holder.imageView2.setImageBitmap(getBitmap(7))
        } else if (position < 10) {
            holder.imageView2.setImageBitmap(null)
        } else if (position == mData.size - 1) {
            holder.imageView2.setImageBitmap(getBitmap(4))
        } else if (position < mData.size - 1) {
            holder.imageView2.setImageBitmap(getBitmap(if (mRandom.nextBoolean()) 5 else 6))
        }
    }

    private fun getBitmap(index: Int): Bitmap {
        val bitmap = mBitmapList!![index]
        if (bitmap == null) {
            initBitmaps()
            return mBitmapList!![index]
        }
        return bitmap
    }

    fun setType(type: Int) {
        if (mCurrentType != type) {
            if (type == TYPE_DRAGON) {
                val tempList: MutableList<Any?> = ArrayList()
                for (i in 0..9) {
                    tempList.add(null)
                }
                mData.addAll(tempList)
            } else {
                if (mCurrentType == TYPE_DRAGON) {
                    for (i in 0..9) {
                        val index = mData.size - 1
                        if (index >= 0) {
                            mData.removeAt(index)
                        }
                    }
                }
            }
            mCurrentType = type
            notifyItemRangeChanged(0, itemCount)
        }
    }

    override fun addData(data: List<Any?>) {
        super.addData(data)
        if (mCurrentType == TYPE_DRAGON && mData.size < 20) {
            super.addData(data)
        }
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options): Int {
        var reqWidth = 0
        var reqHeight = 0
        when (mCurrentType) {
            TYPE_CARD -> {
                reqWidth = 180
                reqHeight = 180
            }

            TYPE_J20 -> {
                reqWidth = 135
                reqHeight = 208
            }

            TYPE_DRAGON -> {
                reqWidth = 68
                reqHeight = 116
            }

            else -> {}
        }
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        return inSampleSize
    }

    private fun decodeSampledBitmapFromResource(res: Resources, resId: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, options)
        options.inSampleSize = calculateInSampleSize(options)
        options.inJustDecodeBounds = false
        return try {
            BitmapFactory.decodeResource(res, resId, options)
        } catch (e: Exception) {
            Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var imageView2: ImageView

        init {
            imageView = itemView.findViewById(R.id.image)
            imageView2 = itemView.findViewById(R.id.image2)
        }
    }

    companion object {
        const val TYPE_CARD = 0
        const val TYPE_J20 = 1
        const val TYPE_DRAGON = 2
    }
}
