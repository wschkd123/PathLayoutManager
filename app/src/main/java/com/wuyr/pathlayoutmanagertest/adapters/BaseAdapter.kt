package com.wuyr.pathlayoutmanagertest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by wuyr on 17-10-24 下午1:05.
 */
@Suppress("unused")
abstract class BaseAdapter<O, VH : RecyclerView.ViewHolder>(
    protected var mContext: Context,
    data: List<O>?,
    protected var mLayoutId: Int,
    private val mHolderClass: Class<VH>
) : RecyclerView.Adapter<VH>() {
    protected var mData: MutableList<O>
    protected var mLayoutInflater: LayoutInflater
    protected var mOnSizeChangedListener: OnSizeChangedListener? = null

    init {
        mData = (data ?: ArrayList()) as MutableList<O>
        mLayoutInflater = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return try {
            val constructor = mHolderClass.getDeclaredConstructor(
                View::class.java
            )
            constructor.newInstance(mLayoutInflater.inflate(mLayoutId, parent, false))
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException("can't find ViewHolder.class!")
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun addData(o: O) {
        mData.add(o)
        notifyItemInserted(mData.size)
        notifyOnSizeChanged()
    }

    fun addData(index: Int, o: O) {
        mData.add(index, o)
        notifyItemInserted(index)
        notifyOnSizeChanged()
    }

    open fun addData(data: List<O>) {
        if (!data.isEmpty()) {
            val oldSize = mData.size - 1
            mData.addAll(data)
            notifyItemRangeChanged(oldSize, mData.size)
            notifyOnSizeChanged()
        }
    }

    fun removeData(o: O): Boolean {
        val pos = mData.indexOf(o)
        if (pos != -1) {
            mData.remove(o)
            notifyItemRemoved(pos)
            notifyOnSizeChanged()
            return true
        }
        return false
    }

    fun removeData(pos: Int): Boolean {
        if (pos > -1 && pos < mData.size) {
            mData.removeAt(pos)
            notifyItemRemoved(pos)
            notifyOnSizeChanged()
            return true
        }
        return false
    }

    fun setData(data: MutableList<O>?) {
        if (data != null) {
            mData = data
            notifyDataSetChanged()
            notifyOnSizeChanged()
        }
    }

    fun clearData() {
        mData.clear()
        notifyDataSetChanged()
        notifyOnSizeChanged()
    }

    val data: List<O>
        get() = mData

    private fun notifyOnSizeChanged() {
        if (mOnSizeChangedListener != null) {
            mOnSizeChangedListener!!.onSizeChanged(mData.size)
        }
    }

    fun setOnSizeChangedListener(listener: OnSizeChangedListener?) {
        mOnSizeChangedListener = listener
    }

    interface OnSizeChangedListener {
        fun onSizeChanged(currentSize: Int)
    }
}