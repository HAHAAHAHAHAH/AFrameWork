package com.wltt.aframework.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.wltt.aframework.tools.ActivityManagerTool
import com.wltt.aframework.tools.LogTool
import android.view.WindowManager

import android.os.Build

import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import com.wltt.aframework.R
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.wltt.aframework.demo.AFWAlertDialog
import com.wltt.aframework.http.HttpRequest
import com.wltt.aframework.http.Request
import com.wltt.aframework.http.RequestCall
import com.wltt.aframework.tools.ScreenTool
import kotlinx.android.synthetic.main.activity_afw.*


/**
 * 基础Activity
 *
 */
open abstract class AFWActivity : AppCompatActivity(), Request {
    val CLASS_TAG = "AFWActivity"

    lateinit var mContext: Context
    lateinit var mActivity: AFWActivity

    var mPermissionCallBack: PermissionCallBack? = null

    var mHandler: Handler? = Handler {
        handlerMessage(it.what, it.arg1)
        false
    }

    var mDialog: AFWAlertDialog? = null

    /**
     * 开启侵入式状态栏
     */
    abstract fun isFullScreen(): Boolean

    abstract fun handlerMessage(what: Int, message: Any?)

    /**
     * 设置根部局id
     */
    abstract fun getContentViewId(): Int

    /**
     * intent传参时，获取参数
     */
    abstract fun getIntentData(intent: Intent)

    /**
     * 获取承载Fragment的View
     *
     * @return
     */
    abstract fun getFragmentViewId(): Int;

    /**
     * 永久拒绝权限列表
     */
    var permanentRejectionList = ArrayList<String>()

    override fun onStart() {
        LogTool.d(CLASS_TAG, "${javaClass.simpleName}  onStart")
        super.onStart()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        LogTool.d(CLASS_TAG, "${javaClass.simpleName}  onCreate")
        if (isFullScreen()) {
            fullScreen()
        }
        super.onCreate(savedInstanceState)
        ActivityManagerTool.createActivity(this)
        mContext = this
        mActivity = this
        setContentView(R.layout.activity_afw)

        if (isFullScreen()) {
            // 设置了侵入式状态栏，给跟布局设置paddingtop为状态栏高度
            awf_parent.setPadding(0, ScreenTool.getStatusBarHeight(mContext), 0, 0)
        }

        LayoutInflater.from(mContext).inflate(getContentViewId(), awf_parent)
        if (intent != null) {
            getIntentData(intent)
        }
    }

    override fun onResume() {
        LogTool.d(CLASS_TAG, "${javaClass.simpleName}  onResume")
        super.onResume()
    }

    override fun onPause() {
        LogTool.d(CLASS_TAG, "${javaClass.simpleName}  onPause")
        super.onPause()
    }

    override fun onStop() {
        LogTool.d(CLASS_TAG, "${javaClass.simpleName}  onStop")
        super.onStop()
    }

    override fun onRestart() {
        LogTool.d(CLASS_TAG, "${javaClass.simpleName}  onRestart")
        super.onRestart()
    }

    override fun onDestroy() {
        LogTool.d(CLASS_TAG, "${javaClass.simpleName}  onDestroy")
        ActivityManagerTool.destroyActivity(this)
        if (mHandler != null) {
            mHandler!!.removeCallbacksAndMessages(this)
            mHandler = null
        }

        if (mDialog != null) {
            if (mDialog!!.isShowing) {
                mDialog!!.dismiss()
            }
            mDialog = null
        }

        super.onDestroy()
    }

    /**
     * 添加fragment
     *
     * @param fragment Fragment
     */
    fun addFragment(fragment: AFWFragment, addBackStack: Boolean) {
        if (getFragmentViewId() > 0) {
            var transaction: FragmentTransaction =
                supportFragmentManager.beginTransaction()
            transaction.replace(getFragmentViewId(), fragment, fragment.javaClass.simpleName)
            if (addBackStack) {
                transaction.addToBackStack(fragment.javaClass.simpleName)
            }
            transaction.commitAllowingStateLoss()
        }
    }

    /**
     * 移除fragment
     */
    fun removeFragment() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    /**
     * 重写返回键，返回时优先返回回退栈中的fragment
     */
    override fun onBackPressed() {
        if (getFragmentViewId() > 0) {
            removeFragment()
        } else {
            super.onBackPressed()
        }
    }


    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity
     */
    fun fullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                val window: Window = this.window
                val decorView: View = window.getDecorView()
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                val option: Int = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
                decorView.setSystemUiVisibility(option)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.setStatusBarColor(Color.TRANSPARENT)
                //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                val window: Window = this.window
                val attributes: WindowManager.LayoutParams = window.getAttributes()
                val flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                attributes.flags = attributes.flags or flagTranslucentStatus
                //                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes)
            }
        }
    }


    fun showAlertDialog(
        title: String?,
        content: String,
        buttonClickListener: AFWAlertDialog.OnButtonClickListener?
    ) {

        if (mDialog == null) {
            mDialog = AFWAlertDialog(this)
        }

        mDialog!!.let {
            if (it.isShowing) {
                it.dismiss()
            }
            it.mTitle = title
            it.mContent = content
            it.buttonClickListener = buttonClickListener
            it.show()
        }


    }

    /**
     * 检查并申请权限
     */
    fun checkPermissions(
        permissions: Array<String>,
        permissionCallBack: PermissionCallBack,
    ) {
        mPermissionCallBack = permissionCallBack
        permanentRejectionList.clear()
        var needRequestList = ArrayList<String>()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    mContext,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                needRequestList.add(permission)
            }
        }

        if (needRequestList.size > 0) {
            //有未申请的权限，去申请
            ActivityCompat.requestPermissions(this, needRequestList.toTypedArray(), 0)
        } else {
            // 没有未申请的权限，说明权限已全部申请
            mPermissionCallBack?.onPermission(true, permanentRejectionList)
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        LogTool.d(
            CLASS_TAG,
            "onRequestPermissionsResult permissions = $permissions\n grantResults = $grantResults"
        )

        var granted = true
        for (i in grantResults.indices) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                    permanentRejectionList.add(permissions[i])
                    Log.e(CLASS_TAG, "权限被永久拒绝： premission = $permissions")
                }
                granted = false
            }
        }
        mPermissionCallBack?.onPermission(granted, permanentRejectionList)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun <T> asynGet(
        url: String,
        params: HashMap<String, String>,
        requestCall: RequestCall<T>
    ) {
        HttpRequest.asynGet(url, params, requestCall)
    }

    override fun <T> asynPostFrom(
        url: String,
        params: HashMap<String, Any>,
        requestCall: RequestCall<T>
    ) {
        HttpRequest.asynPostFrom(url, params, requestCall)
    }

    override fun <T> asynPostJson(url: String, json: String, requestCall: RequestCall<T>) {
        HttpRequest.asynPostJson(url, json, requestCall)
    }

    override fun <T> upload(
        url: String,
        fileType: String,
        filePath: String,
        requestCall: RequestCall<T>
    ) {
        HttpRequest.upload(url, fileType, filePath, requestCall)
    }

}