package com.liang.module_base.screenlight

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import com.liang.module_base.R
import com.liang.module_base.extension.showShortToast

class ScreenOffAdminReceiver : DeviceAdminReceiver() {

    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        context.resources.getString(R.string.device_admin_enabled).showShortToast()
    }

    override fun onDisabled(context: Context, intent: Intent) {
        super.onDisabled(context, intent)
        context.resources.getString(R.string.device_admin_disabled).showShortToast()
    }

}
