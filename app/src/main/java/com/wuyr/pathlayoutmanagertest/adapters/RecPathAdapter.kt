package com.wuyr.pathlayoutmanagertest.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.wuyr.pathlayoutmanagertest.R
import com.wuyr.pathlayoutmanagertest.dpToPx

/**
 * Created by wuyr on 18-5-20 上午4:09.
 */
class RecPathAdapter(
    context: Context
) : RecyclerView.Adapter<RecPathAdapter.ViewHolder>() {
    private val mData: MutableList<String?> = mutableListOf()
    private val mBitmap: Bitmap by lazy {
        BitmapFactory.decodeResource(context.resources, R.drawable.ic_character)
    }

    fun setData(data: MutableList<String?>?) {
        if (data != null) {
            mData.clear()
            mData.addAll(data)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(parent.context)
            .inflate(R.layout.char_item_rec_character, parent, false)
        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView2.layoutParams.width = 76.dpToPx(holder.itemView.context)
        holder.imageView2.layoutParams.height = 76.dpToPx(holder.itemView.context)
        holder.imageView2.setImageBitmap(mBitmap)
        holder.positionTv.text = position.toString()

        holder.itemView.setOnClickListener { v: View? ->
            Toast.makeText(holder.itemView.context, "item $position clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView2 = itemView.findViewById<ImageView>(R.id.image2)
        var positionTv = itemView.findViewById<TextView>(R.id.position_tv)
    }

}
