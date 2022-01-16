package com.wltt.aframework.demo

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.view.View
import com.wltt.aframework.R
import com.wltt.aframework.base.AFWCenterDialog
import kotlinx.android.synthetic.main.dialog_alert.*

class AFWAlertDialog(context: Context) : AFWCenterDialog(context) {

    var mTitle:String? = ""
    var mContent = ""
    var cancleText = ""
    var okText = ""
    var mCancelable: Boolean = true
    var mContentBuilder: SpannableStringBuilder? = null
    var buttonClickListener: OnButtonClickListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_alert)
        if (mTitle.isNullOrEmpty()) {
            tvTitle.text = "提示"
        } else {
            tvTitle.text = mTitle
        }
        if (mContentBuilder != null) {
            tvContent.text = mContentBuilder
            tvContent.movementMethod = LinkMovementMethod.getInstance()
            tvContent.highlightColor = Color.TRANSPARENT;
        } else {
            tvContent.text = mContent
        }

        val visibility = if (mCancelable) View.VISIBLE else View.GONE
        btnCancle.visibility = visibility
        Vline.visibility = visibility

        if (!cancleText.isNullOrEmpty()) {
            btnCancle.text = cancleText
        }

        if (!okText.isNullOrEmpty()) {
            btnOk.text = okText
        }

        btnCancle.setOnClickListener() {
            if (buttonClickListener != null) {
                buttonClickListener!!.cancelClick()
            }
            dismiss()
        }

        btnOk.setOnClickListener() {
            if (buttonClickListener != null) {
                buttonClickListener!!.okClick()
            }
            dismiss()
        }

    }

    interface OnButtonClickListener {
        fun cancelClick()
        fun okClick()
    }
}