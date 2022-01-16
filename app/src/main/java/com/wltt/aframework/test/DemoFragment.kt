package com.wltt.aframework.test

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.wltt.aframework.R
import com.wltt.aframework.adapter.AFWRecyclerMultipleAdapter
import com.wltt.aframework.base.AFWFragment
import com.wltt.aframework.tools.LogTool
import kotlinx.android.synthetic.main.fragment_demo.*

class DemoFragment : AFWFragment() {

    lateinit var adapter:RAdapter
    var sList:MutableList<Any> = ArrayList()


    override fun getContentViewId(): Int {
        return R.layout.fragment_demo
    }

    override fun getIntentData(bundle: Bundle?) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RAdapter(mActivity!!.mContext)
         adapter.register(String.javaClass)
        adapter.register(Int.javaClass)
        adapter.register<T1>()
        //adapter.register(Long.javaClass)
       // adapter.register(Float.javaClass)
        //adapter.register(Double.javaClass)
        //adapter.register(Boolean.javaClass)
        //adapter.register(Short.javaClass)
        sList.add("11111")
        sList.add(R.string.app_name)
        sList.add(T1("haha"))
        sList.add("22222")
        sList.add("33333")
        sList.add("44444")
        sList.add("55555")
        sList.add("66666")
        adapter.setData(sList)

        adapter.itemClickListense =object :AFWRecyclerMultipleAdapter.OnItemClickListense{
            override fun onItemClick(position: Int) {
                LogTool.w("pangtao",sList[position].toString())
                adapter.removeData(position)
            }
        }

        rv.layoutManager = LinearLayoutManager(mActivity)
        rv.adapter = adapter
        adapter.notifyDataSetChanged()




    }
}