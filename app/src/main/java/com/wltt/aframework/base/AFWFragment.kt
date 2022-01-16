package com.wltt.aframework.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wltt.aframework.tools.LogTool

open abstract class AFWFragment : Fragment() {
    val CLASS_TAG = "DemoFragment"

    protected var mActivity: AFWActivity? = null
    protected var parentView: View? = null

    /**
     * parentView的Id
     */
    abstract fun getContentViewId(): Int

    /**
     * 获取传值
     */
    abstract fun getIntentData(bundle: Bundle?)


    override fun onAttach(context: Context) {
        super.onAttach(context)
        LogTool.d(CLASS_TAG, "${javaClass.simpleName}:onAttach")
        if (context is AFWActivity) {
            mActivity = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        parentView = inflater.inflate(getContentViewId(), container, false)
        return parentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (arguments != null) {
            getIntentData(arguments)
        }
    }

    fun addFragment(fragment: AFWFragment, addBackStack: Boolean) {
        mActivity?.let {
            it.addFragment(fragment, addBackStack)
        }
    }

    fun removeFragment() {
        mActivity?.let { it.removeFragment() }
    }

    fun checkPermissions(
        permissions: Array<String>,
        permissionCallBack: PermissionCallBack,
    ) {
        mActivity?.let { it.checkPermissions(permissions, permissionCallBack) }
    }


}