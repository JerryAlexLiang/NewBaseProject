package com.liang.module_route.service

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

interface PictureSelectorService : IProvider {

    fun startPictureSelectorActivity(context: Context)

}