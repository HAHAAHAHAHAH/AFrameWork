package com.wltt.aframework.test

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import com.wltt.aframework.R
import com.wltt.aframework.base.AFWActivity
import com.wltt.aframework.base.PermissionCallBack
import com.wltt.aframework.base.mvp.AFWMVPActivity
import com.wltt.aframework.base.mvp.AFWMode
import com.wltt.aframework.demo.AFWAlertDialog
import com.wltt.aframework.tools.LogTool
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AFWMVPActivity<LoginIView,LoginMode,LoginPresenter>(),LoginIView {

    var permissions = arrayOf<String>(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CALL_PHONE
    )

    override fun isFullScreen(): Boolean {
        return false
    }

    override fun handlerMessage(what: Int, message: Any?) {
        TODO("Not yet implemented")
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_main
    }

    override fun getIntentData(intent: Intent) {

    }

    override fun getFragmentViewId(): Int {
        return R.id.fl_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // addFragment(DemoFragment(), true)
        tvDemo.setOnClickListener() {
            mMode!!.login()
        }
    }


    fun test(){
        checkPermissions(permissions, object : PermissionCallBack {
            override fun onPermission(
                granted: Boolean,
                permanentRejectionList: ArrayList<String>
            ) {
                if (granted) {
                    // 权限申请成功
                    LogTool.d(CLASS_TAG, "权限申请成功")
                } else {
                    // 权限申请失败
                    if (permanentRejectionList.size > 0) {
                        // 有权限被永久拒绝
                        showAlertDialog(
                            "权限申请",
                            "您有权限被永久拒绝，需要去设置页面主动打开权限，才能正常使用功能",
                            object : AFWAlertDialog.OnButtonClickListener {
                                override fun cancelClick() {
                                    TODO("Not yet implemented")
                                }

                                override fun okClick() {
                                    val intent =
                                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                    val uri: Uri =
                                        Uri.fromParts("package", mContext.packageName, null)
                                    intent.data = uri
                                    try {
                                        mContext.startActivity(intent)
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }

                            })
                    } else {
                        // 权限被拒绝，但是没有永久拒绝
                        LogTool.d(CLASS_TAG, "权限被拒绝，但是没有永久拒绝")
                    }
                }


            }

        })
    }

    override fun loginSuccess(t: String) {
        tvDemo.text = t
    }
}
