package com.liang.newbaseproject.screenlight

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import com.liang.module_base.extension.showShortToast


class ScreenOffAdminReceiver : DeviceAdminReceiver() {

    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        "设备管理器功能".showShortToast()
    }

    override fun onDisabled(context: Context, intent: Intent) {
        super.onDisabled(context, intent)
        "没有设备管理器功能".showShortToast()
    }

}
