package com.liang.module_base.constant

class Constants {

    companion object {
        //kv
        const val USER_NAME: String = "user_name"
        const val USER_COOKIE: String = "user_cookie"

        //http
        const val HTTP_SUCCESS = 0
        const val HTTP_AUTH_INVALID = -1001

        //首先声明权限授权
        const val PERMISSION_GRANTED = 0 //标识权限授权

        const val PERMISSION_DENIEG = 1 //权限不足，权限被拒绝的时候

        const val PERMISSION_REQUEST_CODE = 0 //系统授权管理页面时的结果参数

        const val EXTRA_PERMISSION = "com.lai.permission" //权限参数

        const val PACKAGE_URL_SCHEME = "package:" //权限方案
    }
}