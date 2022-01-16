package com.wltt.aframework.base.mvp

open abstract class AFWPresenter<TV> {

    var mView: TV? = null

    open fun attchView(view: TV?) {
        mView = view
    }

    /**
     * 解除当前presenter依赖view关系
     */
    open fun detachView() {
        if (mView != null) {
            mView = null
        }
    }


}