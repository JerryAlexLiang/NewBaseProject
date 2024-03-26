package com.liang.module_base.di

import com.liang.module_base.base.BaseViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val baseModel = module {
    viewModel {
        BaseViewModel(androidApplication())
    }
}
