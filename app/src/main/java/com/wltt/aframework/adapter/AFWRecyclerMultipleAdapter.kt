package com.wltt.aframework.adapter

import android.annotation.SuppressLint
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.wltt.aframework.tools.LogTool
/**
 * 通用的多布局recyclerView adapter
 *  一种布局时，可以不register
 * 多种布局时，必须register ，根据register的顺序作为viewtype
 * 根据注册的顺序作为viewtype
 * onCreateViewHolder 时根据viewtype创建viewholder
 */
abstract class AFWRecyclerMultipleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val TAG = "ARecyclerMultipleAdapter"
    private var types: MutableList<Type<*>> = ArrayList()
    private lateinit var mType: Type<*>
    private var mData: MutableList<Any> = ArrayList()
    var itemClickListense: OnItemClickListense? = null

    inline fun <reified T> register() {
        register(Type(T::class.java))
    }

    fun <T> register(clazz: Class<T>) {
        register(Type(clazz))
    }

    fun <T> register(type: Type<T>) {
        types.add(type)
    }

    fun getData(): MutableList<Any> {
        return mData
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: MutableList<Any>) {
        mData.clear()
        mData.addAll(data)
    }

    fun addData(data: MutableList<Any>) {
        mData.addAll(data)
    }

    /**
     * 删除某个item
     */
    fun removeData(position: Int) {
        if (mData.size > position) {
            mData.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,mData.size - position)
        } else {
            LogTool.e(TAG, "error:remove data position > data.size")
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun getItemViewType(position: Int): Int {
        if (types.size > 1) {
            for (index in types.indices) {
                mType = types[index]

                Log.d("pangtao", "data className = ${mData[position].javaClass.name}")
                Log.d("pangtao", "mType className = ${mType.className}")
                if (mType.className == mData[position].javaClass.name) {
                    return index
                }
            }
            //throw Exception("存在未注册的实体!")
        }
        return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener() {
            itemClickListense!!.let {
                it.onItemClick(position)
            }
        }

        onBindView(holder, position)

    }

    interface OnItemClickListense {
        fun onItemClick(position: Int)
    }

    abstract fun onBindView(holder: RecyclerView.ViewHolder, position: Int)

}