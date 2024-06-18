package com.liang.module_base.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

/**
 * 创建日期：2018/12/24 on 11:17
 * 描述: 检查权限的工具类
 * 作者: liangyang
 */
class CheckPermissionKt(context: Context) {
    private val context: Context

    //构造器
    init {
        this.context = context.applicationContext
    }

    //检查权限时，判断系统的权限集合
    fun permissionSet(permissions: ArrayList<String>?): Boolean {
        if (permissions != null) {
            for (permission in permissions) {
                if (isLackPermission(permission)) { //是否添加完全部权限集合
                    return true
                }
            }
        }
        return false
    }

    //检查系统权限，判断当前是否缺少权限(PERMISSION_DENIED:权限是否足够)
    fun isLackPermission(permission: String?): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission!!
        ) == PackageManager.PERMISSION_DENIED
    }

}
