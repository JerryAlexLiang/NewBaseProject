package com.liang.module_base.base

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.core.app.ActivityCompat
import com.liang.module_base.R
import com.liang.module_base.constant.Constants.Companion.EXTRA_PERMISSION
import com.liang.module_base.constant.Constants.Companion.PACKAGE_URL_SCHEME
import com.liang.module_base.constant.Constants.Companion.PERMISSION_DENIEG
import com.liang.module_base.constant.Constants.Companion.PERMISSION_GRANTED
import com.liang.module_base.constant.Constants.Companion.PERMISSION_REQUEST_CODE
import com.liang.module_base.databinding.ActivityPermissionBinding
import com.liang.module_base.utils.CheckPermissionKt


class PermissionActivity : BaseActivity<ActivityPermissionBinding>() {
//    //首先声明权限授权
//    val PERMISSION_GRANTED = 0 //标识权限授权
//
//    val PERMISSION_DENIEG = 1 //权限不足，权限被拒绝的时候
//
//    private val PERMISSION_REQUEST_CODE = 0 //系统授权管理页面时的结果参数
//
//    private val EXTRA_PERMISSION = "com.lai.permission" //权限参数
//
//    private val PACKAGE_URL_SCHEME = "package:" //权限方案

    private var checkPermission: CheckPermissionKt? = null //检测权限类的权限检测器

    private var isRequestCheck = false //判断是否需要系统权限检测。防止和系统提示框重叠

    //启动当前权限页面的公开接口
    companion object {
        fun startActivityForResult(
            activity: Activity?,
            requestCode: Int,
            permissions: ArrayList<String>
        ) {
            val intent = Intent(activity, PermissionActivity::class.java)
            intent.putStringArrayListExtra(EXTRA_PERMISSION, permissions)
            ActivityCompat.startActivityForResult(activity!!, intent, requestCode, null)
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_permission

    override fun initView(savedInstanceState: Bundle?) {
        if (intent == null || !intent.hasExtra(EXTRA_PERMISSION)) {
            //如果参数不等于配置的权限参数时 异常提示
            throw RuntimeException("当前Activity需要使用静态的StartActivityForResult方法启动")
        }
        checkPermission = CheckPermissionKt(this)
        //改变检测状态
        isRequestCheck = true
    }

    //检测完之后请求用户授权
    override fun onResume() {
        super.onResume()
        if (isRequestCheck) {
            val permissions = getPermissions()
            if (checkPermission!!.permissionSet(permissions)) {
                // 请求权限
                permissions?.let { requestPermissions(it) }
            } else {
                // 获取全部权限
                allPermissionGranted()
            }


        } else {
            isRequestCheck = true
        }
    }

    /**
     * 用于权限管理
     * 如果全部授权的话，则直接通过进入
     * 如果权限拒绝，缺失权限时，则使用dialog提示
     *
     * @param requestCode  请求代码
     * @param permissions  权限参数
     * @param grantResults 结果
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (PERMISSION_REQUEST_CODE == requestCode && hasAllPermissionGranted(grantResults)) {
            // 判断请求码与请求结果是否一致
            // 需要检测权限，直接进入，否则提示对话框进行设置
            isRequestCheck = true
            // 进入
            allPermissionGranted()
        } else {
            // 提示对话框设置
            isRequestCheck = false
            showMissingPermissionDialog()
        }
    }

    //获取全部权限
    private fun hasAllPermissionGranted(grantResults: IntArray): Boolean {
        for (grantResult in grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false
            }
        }
        return true
    }

    //获取全部权限
    private fun allPermissionGranted() {
        setResult(PERMISSION_GRANTED)
        finish()
    }

    //请求权限去兼容版本
    private fun requestPermissions(permissions: ArrayList<String>) {
        ActivityCompat.requestPermissions(
            this,
            permissions.toTypedArray(),
            PERMISSION_REQUEST_CODE
        )
    }

    //返回传递过来的权限参数
    private fun getPermissions(): ArrayList<String>? {
        return intent.getStringArrayListExtra(EXTRA_PERMISSION)
    }

    //显示对话框提示用户缺少权限
    private fun showMissingPermissionDialog() {
        val builder = AlertDialog.Builder(this@PermissionActivity)
        builder.setTitle(R.string.help) //提示帮助
        builder.setMessage(R.string.string_help_text)

        //如果是拒绝授权，则退出应用
        //退出
        builder.setNegativeButton(
            R.string.quit
        ) { _, _ ->
            setResult(PERMISSION_DENIEG) //权限不足
            finish()
        }
        //打开设置，让用户选择打开权限
        builder.setPositiveButton(
            R.string.settings
        ) { _, _ ->
            startAppSettings() //打开设置
        }
        builder.setCancelable(false)
        builder.show()
    }

    // 打开系统应用设置(ACTION_APPLICATION_DETAILS_SETTINGS:系统设置权限)
    private fun startAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + packageName))
        startActivity(intent)
        // Activity切换动画,必须在 StartActivity()  或 finish() 之后立即调用
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        overridePendingTransition(R.anim.scale_enter, R.anim.slide_still)
    }
}