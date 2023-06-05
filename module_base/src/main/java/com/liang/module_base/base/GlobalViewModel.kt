package com.liang.module_base.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.liang.module_base.http.model.BaseResult

/**
 * @Time: 2023/4/4/0004 on 9:05
 * @User: Jerry
 * @Description: App全局ViewModel可直接替代EventBus
 */
class GlobalViewModel : ViewModel() {

    /** 请求异常（服务器请求失败，譬如：服务器连接超时等） */
    val exception = MutableLiveData<Exception>()

    /** 请求服务器返回错误（服务器请求成功但status错误，譬如：登录过期等） */
    val errorResponse = MutableLiveData<BaseResult<*>?>()

}
