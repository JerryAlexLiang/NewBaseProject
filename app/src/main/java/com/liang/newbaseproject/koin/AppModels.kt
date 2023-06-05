package com.liang.newbaseproject.koin

import com.liang.module_base.di.baseModel
import okhttp3.internal.immutableListOf

val appModules =
    immutableListOf(baseModel, netModule, repositoryModule, viewModelModule, adapterModule)
