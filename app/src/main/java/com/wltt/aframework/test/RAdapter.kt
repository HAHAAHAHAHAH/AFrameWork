package com.wltt.aframework.test

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wltt.aframework.R
import com.wltt.aframework.adapter.AFWRecyclerMultipleAdapter

class RAdapter : AFWRecyclerMultipleAdapter {

    var mContext: Context;
    lateinit var item: Any

    constructor(mContext: Context) : super() {
        this.mContext = mContext
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView1: TextView = itemView.findViewById(android.R.id.text1)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView: View = LayoutInflater.from(mContext)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        Log.d("pangtao", "viewType = $viewType")
        when (viewType) {

            0 -> {
                itemView.setBackgroundResource(R.color.black)
            }

            1 -> {
                itemView.setBackgroundResource(R.color.red)
            }

            2 -> {
                itemView.setBackgroundResource(R.color.cardview_dark_background)
            }

        }

        return ViewHolder(itemView)

    }

    override fun onBindView(holder: RecyclerView.ViewHolder, position: Int) {
        item = getData()[position]
        if (holder is ViewHolder) {
            if (item is String) {
                holder.textView1.text = item as String
                holder.textView1
                    .setTextColor(mContext.resources.getColor(R.color.white))
            } else if (item is Int) {
                holder.textView1.text =
                    mContext.getText(item as Int)
                holder.textView1
                    .setTextColor(mContext.resources.getColor(R.color.white))
            } else if (item is T1) {
                holder.textView1.text = (item as T1).name
                holder.textView1
                    .setTextColor(mContext.resources.getColor(R.color.red))
            }
        }
    }

}