package com.liang.newbaseproject.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.liang.newbaseproject.bean.WanDataBean

/**
 * - Time: 2023/6/5/0005 on 16:20
 * - User: Jerry
 * - Description: App全局ViewModel可直接替代EventBus
 */
class AppViewModel : ViewModel() {

    // 全局测试
    val bannerEvent = MutableLiveData<WanDataBean>()


}
