package com.wltt.aframework.test

import com.wltt.aframework.base.mvp.AFWMode

class LoginMode:AFWMode<LoginPresenter>() {

    fun login(){
        mPresenter!!.login("MVP")
    }

}