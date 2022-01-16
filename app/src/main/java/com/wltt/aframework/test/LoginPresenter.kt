package com.wltt.aframework.test

import com.wltt.aframework.base.mvp.AFWPresenter

class LoginPresenter: AFWPresenter<LoginIView>() {

    fun login(t:String){
        mView!!.loginSuccess(t)
    }

}