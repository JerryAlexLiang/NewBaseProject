package com.liang.newbaseproject.splash

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.liang.newbaseproject.bean.MockTypewriterBeanList
import com.liang.module_base.base.BaseViewModel

class SplashViewModel(application: Application) : BaseViewModel(application) {

    private val _typeLiveData = MutableLiveData<TypewriterBean>()

    val typeLiveData: LiveData<TypewriterBean>
        get() = _typeLiveData

    private val typewriterBeans = MockTypewriterBeanList.initTypewriterBeans()

    init {
        setTypeData()
    }

    fun setTypeData(
        isOpenLauncherText: Boolean = true
    ) {
        val typewriterBean = typewriterBeans[(typewriterBeans.indices).random()]
        typewriterBean.isPlayVoice = false
//        typewriterBean.isOpenLauncherText = false
        _typeLiveData.value = typewriterBean
    }

    fun setTypeDataOpenVoice(typewriterBeans: MutableList<TypewriterBean>) {
        val typewriterBean = typewriterBeans[(typewriterBeans.indices).random()]
        typewriterBean.isPlayVoice = true
        _typeLiveData.value = typewriterBean
    }

}