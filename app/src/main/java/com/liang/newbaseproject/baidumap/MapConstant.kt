package com.liang.newbaseproject.baidumap

import android.Manifest

class MapConstant {
    companion object {
        // Constants.kt 或者直接在你的Activity/Fragment中
        const val PERMISSION_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
        const val PERMISSION_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
        const val PERMISSION_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
        const val LOCATION_PERMISSIONS_REQUEST = 100
        const val STORAGE_PERMISSIONS_REQUEST = 101

        const val PACKAGE_URL_SCHEME = "package:" //权限方案

    }

}