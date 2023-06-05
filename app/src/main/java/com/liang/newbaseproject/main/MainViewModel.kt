package com.liang.newbaseproject.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.liang.newbaseproject.bean.FunItemBean
import com.liang.newbaseproject.bean.MockMainFunBeanList
import com.liang.module_base.base.BaseViewModel


class MainViewModel(application: Application) : BaseViewModel(application) {

    private val _funBeanListLiveData = MutableLiveData<MutableList<FunItemBean>>()

    val funBeanListLiveData: LiveData<MutableList<FunItemBean>>
        get() = _funBeanListLiveData

    init {
        val initFunData = MockMainFunBeanList.initMainFunData()
        _funBeanListLiveData.value = initFunData
    }
}