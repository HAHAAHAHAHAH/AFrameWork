package com.wltt.aframework.base.mvp

import android.os.Bundle
import com.wltt.aframework.base.AFWActivity
import com.wltt.aframework.base.AFWFragment
import com.wltt.aframework.tools.ClassTool

open abstract class AFWMVPFragment<IView, IMode : AFWMode<IPresenter>, IPresenter : AFWPresenter<IView>> :
    AFWFragment() {

    var mMode: IMode? = null
    var mPresenter: IPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        mMode = ClassTool.getT<IMode>(this, 1)
        mPresenter = ClassTool.getT<IPresenter>(this, 2)
        mPresenter!!.attchView(this as IView)
        mMode!!.attchPresenter(mPresenter)
        super.onCreate(savedInstanceState)
    }


    override fun onDestroy() {

        if (mMode != null) {
            mMode!!.detachPresenter()
            mMode = null
        }

        if (mPresenter != null) {
            mPresenter!!.detachView()
            mPresenter = null
        }

        super.onDestroy()
    }

}