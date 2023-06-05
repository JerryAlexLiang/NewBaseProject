package com.liang.module_base.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.liang.module_base.http.exception.DealException
import com.liang.module_base.http.exception.ResultException
import com.liang.module_base.http.model.BaseResult
import com.liang.module_base.http.model.DataStatus
import com.liang.module_base.http.model.StateLiveData
import com.liang.module_base.http.model.StateResultFlow
import com.liang.module_base.utils.LogUtils
import com.liang.module_base.utils.ToastUtil
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * 封装请求与处理回调
     */
    suspend fun <T : Any> executeRequest(
        block: suspend () -> BaseResult<T>,
        stateLiveData: StateLiveData<T>
    ) {
        var baseResp = BaseResult<T>()
        try {
            baseResp.dataStatus = DataStatus.STATE_LOADING
            stateLiveData.postValue(baseResp)
            //将结果复制给baseResp
            baseResp = block.invoke()
            if (baseResp.errorCode == 0 || baseResp.errorCode == 200) {
                baseResp.dataStatus = DataStatus.STATE_SUCCESS
            } else {
                //服务器请求错误
                baseResp.dataStatus = DataStatus.STATE_ERROR
                baseResp.exception = ResultException(
                    baseResp.errorCode.toString(),
                    baseResp.errorMsg ?: ""
                )
                // 全局监测
                BaseApp.globalViewModel.errorResponse.value = baseResp
            }
        } catch (e: Exception) {
            // 非后台返回错误，捕获到的异常
            baseResp.dataStatus = DataStatus.STATE_ERROR
            baseResp.exception = DealException.handlerException(e)
            // 全局监测
            BaseApp.globalViewModel.exception.value = DealException.handlerException(e)
            LogUtils.e(msg = "errorResponse:  ${Gson().toJson(e)}")
            BaseApp.globalViewModel.errorResponse.value = baseResp
        } finally {
            // 最终发射节点（此时会调用onChanged方法）
            stateLiveData.postValue(baseResp)
        }
    }

    /**
     * 方式二：结合Flow请求数据。
     * 根据Flow的不同请求状态，如onStart、onEmpty、onCompletion等设置baseResp.dataState状态值，
     * 最后通过stateLiveData分发给UI层。
     *
     * @param block api的请求方法
     * @param stateLiveData 每个请求传入相应的LiveData，主要负责网络状态的监听
     */
    suspend fun <T : Any> executeReqWithFlow(
        block: suspend () -> BaseResult<T>,
        stateLiveData: StateLiveData<T>
    ): Job {
        return viewModelScope.launch {
            var baseResp = BaseResult<T>()
            flow {
                val respResult = block.invoke()
                baseResp = respResult
                emit(respResult)
            }
                .flowOn(Dispatchers.IO)
                .onStart {
                    baseResp.dataStatus = DataStatus.STATE_LOADING
                    stateLiveData.postValue(baseResp)
                }
                .catch { e ->
                    run {
                        //非后台返回错误，捕获到的异常
                        baseResp.dataStatus = DataStatus.STATE_ERROR
                        baseResp.exception = DealException.handlerException(e)
                        stateLiveData.postValue(baseResp)
                        // 全局监测
                        BaseApp.globalViewModel.exception.value = DealException.handlerException(e)
                    }
                }
                .collect {
                    if (baseResp.errorCode == 0) {
                        baseResp.dataStatus = DataStatus.STATE_SUCCESS
                    } else {
                        //服务器请求错误
                        baseResp.dataStatus = DataStatus.STATE_ERROR
                        baseResp.exception = ResultException(
                            baseResp.errorCode.toString(),
                            baseResp.errorMsg ?: ""
                        )
                        // 全局监测
                        BaseApp.globalViewModel.errorResponse.value = baseResp
                    }
                    stateLiveData.postValue(baseResp)
                }
        }
    }

    suspend fun <T : Any> executeReqWithStateFlow(
        block: suspend () -> BaseResult<T>,
        stateFlow: StateResultFlow<T>
    ): Job {
        return viewModelScope.launch {
            var baseResp = BaseResult<T>()
            flow {
                val respResult = block.invoke()
                baseResp = respResult
                emit(respResult)
            }
                .flowOn(Dispatchers.IO)
                .onStart {
                    baseResp.dataStatus = DataStatus.STATE_LOADING
                    stateFlow.value = baseResp
                }
                .catch { e ->
                    run {
                        //非后台返回错误，捕获到的异常
                        baseResp.dataStatus = DataStatus.STATE_ERROR
                        baseResp.exception = DealException.handlerException(e)
                        stateFlow.value = baseResp
                        // 全局监测
                        BaseApp.globalViewModel.exception.value = DealException.handlerException(e)
                    }
                }
                .collect {
                    if (baseResp.errorCode == 0) {
                        baseResp.dataStatus = DataStatus.STATE_SUCCESS
                    } else {
                        //服务器请求错误
                        baseResp.dataStatus = DataStatus.STATE_ERROR
                        baseResp.exception = ResultException(
                            baseResp.errorCode.toString(),
                            baseResp.errorMsg ?: ""
                        )
                        // 全局监测
                        BaseApp.globalViewModel.errorResponse.value = baseResp
                    }
                    stateFlow.emit(baseResp)
                }
        }
    }

    /**
     * 启动协程，封装了viewModelScope.launch
     *
     * @param tryBlock try语句运行的函数
     * @param catchBlock catch语句运行的函数，可以用来做一些网络异常等的处理，默认空实现
     * @param finallyBlock finally语句运行的函数，可以用来做一些资源回收等，默认空实现
     */
    fun launch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.() -> Unit = {},
        finallyBlock: suspend CoroutineScope.() -> Unit = {}
    ) {
        // 默认是执行在主线程，相当于launch(Dispatchers.Main)
        viewModelScope.launch {
            try {
                tryBlock()
            } catch (e: Exception) {
                // 全局监测
                BaseApp.globalViewModel.exception.value = DealException.handlerException(e)
                catchBlock()
            } finally {
                finallyBlock()
            }
        }
    }

    /**
     * 请求结果处理
     *
     * @param response ApiResponse
     * @param successBlock 服务器请求成功返回成功码的执行回调，默认空实现
     * @param errorBlock 服务器请求成功返回错误码的执行回调，默认返回false的空实现，函数返回值true:拦截统一错误处理，false:不拦截
     */
    suspend fun <T> handleRequest(
        response: BaseResult<T>,
        successBlock: suspend CoroutineScope.(response: BaseResult<T>) -> Unit = {},
        errorBlock: suspend CoroutineScope.(response: BaseResult<T>) -> Boolean = { false }
    ) {
        coroutineScope {
            when (response.errorCode) {
                0 -> successBlock(response) // 服务器返回请求成功码
                else -> { // 服务器返回的其他错误码
                    if (!errorBlock(response)) {
                        // 只有errorBlock返回false不拦截处理时，才去统一提醒错误提示
                        BaseApp.globalViewModel.errorResponse.value = response
                    }
                }
            }
        }
    }

    suspend fun toast(message: String?) {
        withContext(Dispatchers.Main) {
            if (message != null) {
                ToastUtil.showShort(getApplication(), message)
            }
        }
    }

}