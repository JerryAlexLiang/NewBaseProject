package com.liang.module_base.loadsir

import android.content.Context
import android.view.View
import com.kingja.loadsir.callback.Callback
import com.liang.module_base.R

class EmptyCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.empty_view
    }

    override fun onViewCreate(context: Context?, view: View?) {
        super.onViewCreate(context, view)

    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return super.onReloadEvent(context, view)
    }
}