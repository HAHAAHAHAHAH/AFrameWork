package com.wltt.aframework.base.mvp

open abstract class AFWMode<T : AFWPresenter<*>> {

    var mPresenter: T? = null

    fun attchPresenter(presenter: T?) {
        mPresenter = presenter
    }

    fun detachPresenter() {
        if (mPresenter != null) {
            mPresenter = null
        }
    }

}