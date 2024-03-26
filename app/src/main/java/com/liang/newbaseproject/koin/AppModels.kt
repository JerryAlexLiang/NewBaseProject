package com.liang.newbaseproject.koin

import com.liang.module_base.di.baseModel
import com.liang.module_weather.di.weatherModel
import okhttp3.internal.immutableListOf

val appModules = immutableListOf(
    baseModel,
    mainModel,
    weatherModel,
)
