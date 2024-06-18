package com.liang.module_base.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * - Time: 2024/06/18 9:22
 * - User: Jerry
 * - Description: 创建权限请求工具类
 */
object PermissionUtils {

    //获取全部权限
    fun hasAllPermissionGranted(grantResults: IntArray): Boolean {
        for (grantResult in grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false
            }
        }
        return true
    }

    // 检查是否所有权限都已授予
    fun checkPermissionsGranted(context: Context, permissions: List<String>): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun requestPermissions(
        context: Context,
        permissions: List<String>,
        requestCode: Int,
        rationale: (() -> Unit)? = null
    ) {
        if (permissions.isEmpty()) {
            return
        }

        if (shouldShowRequestPermissionRationale(context, permissions)) {
            // 权限被拒绝过，显示一些解释
            rationale?.invoke()
        } else {
            // 直接请求权限
            ActivityCompat.requestPermissions(
                context as Activity,
                permissions.toTypedArray(),
                requestCode
            )
        }
    }

    private fun shouldShowRequestPermissionRationale(
        context: Context,
        permissions: List<String>
    ): Boolean {
        for (permission in permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    context as Activity,
                    permission
                )
            ) {
                return true
            }
        }
        return false
    }

    fun isPermissionGranted(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
}