package com.wltt.aframework.adapter

import androidx.recyclerview.widget.RecyclerView
import com.wltt.aframework.tools.LogTool

/**
 * 通用的单布局recyclerView adapter
 * 集成了item点击事件
 *
 */
abstract class AFWRecyclerAdapter<T, TH : RecyclerView.ViewHolder> : RecyclerView.Adapter<TH>() {
    val TAG = "ARecyclerAdapter"

    private var mData: MutableList<T> = ArrayList()
    var itemClickListense: OnItemClickListense? = null

    fun getData(): MutableList<T> {
        return mData
    }

    fun setData(data: MutableList<T>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    /**
     * 用于上啦加载
     */
    fun addData(data: MutableList<T>) {
        mData.addAll(data)
        notifyDataSetChanged()
    }

    /**
     * 删除某个item
     */
    fun removeData(position: Int) {
        if (mData.size > position) {
            mData.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, mData.size - position)
        } else {
            LogTool.e(TAG, "error:remove data position > data.size")
        }
    }

    /**
     * 批量删除
     */
    fun removeData(removeList: List<T>) {
        mData.removeAll(removeList)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return mData.size
    }


    override fun onBindViewHolder(holder: TH, position: Int) {
        holder.itemView.setOnClickListener() {
            itemClickListense!!.let {
                it.onItemClick(position)
            }
        }

        onBindView(holder, mData[position], position)

    }

    interface OnItemClickListense {
        fun onItemClick(position: Int)
    }

    abstract fun onBindView(holder: RecyclerView.ViewHolder, itemBean: T, position: Int)

}