package com.liang.module_route.app

import android.app.Application

interface BaseApplicationImpl {
    fun onCreate(application: Application?)
    fun onCreate(application: Application?, isDebug: Boolean)
}