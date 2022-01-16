package com.wltt.aframework.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import com.wltt.aframework.R

open class AFWCenterDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
        window?.let {
            it.attributes.width = WindowManager.LayoutParams.MATCH_PARENT
            it.attributes.height = WindowManager.LayoutParams.WRAP_CONTENT
            // window.attributes.dimAmount = 0f
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setGravity(Gravity.CENTER)
            //设置弹入弹出动画
            //it.setWindowAnimations(R.style.BottomDialog_Animation)
        }

    }

}