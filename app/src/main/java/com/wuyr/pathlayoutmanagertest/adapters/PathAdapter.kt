package com.wuyr.pathlayoutmanagertest.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.wuyr.pathlayoutmanagertest.R
import com.wuyr.pathlayoutmanagertest.dpToPx

/**
 * Created by wuyr on 18-5-20 上午4:09.
 */
class PathAdapter(
    context: Context
) : RecyclerView.Adapter<PathAdapter.ViewHolder>() {
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
            .inflate(R.layout.adapter_item_view, parent, false)
        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView2.layoutParams.width = 76.dpToPx(holder.itemView.context)
        holder.imageView2.layoutParams.height = 76.dpToPx(holder.itemView.context)
        holder.imageView2.setImageBitmap(mBitmap)

        holder.itemView.setOnClickListener { v: View? ->
            Toast.makeText(holder.itemView.context, "item %s clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView2: ImageView

        init {
            imageView2 = itemView.findViewById(R.id.image2)
        }
    }

}
