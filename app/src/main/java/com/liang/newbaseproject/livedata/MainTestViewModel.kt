package com.liang.newbaseproject.livedata

import android.app.Application
import androidx.lifecycle.*
import com.liang.module_base.base.BaseViewModel
import com.liang.newbaseproject.base.MyApp
import com.liang.newbaseproject.base.twoArgViewModelFactory
import com.liang.newbaseproject.bean.WanDataBean
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainTestViewModel(
    application: Application,
    private val wanApiRepository: WanApiRepository,
) : BaseViewModel(application) {

    companion object {
        /**
         * Factory for creating [MainTestViewModel]
         *
         * @param arg the repository to pass to [MainTestViewModel]
         */
//        val FACTORY = singleArgViewModelFactory(::WanViewModel)
        val FACTORY = twoArgViewModelFactory(::MainTestViewModel)
    }

    private val _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData

    private val _bannerLiveData = MutableLiveData<WanBaseResBean<List<WanDataBean>>>()
    val bannerLiveData: LiveData<WanBaseResBean<List<WanDataBean>>>
        get() = _bannerLiveData

    private val _progressBarLiveData = MutableLiveData<Boolean>()
    val progressBarLiveData: LiveData<Boolean>
        get() = _progressBarLiveData

//    private val _loginTestLiveData = MutableLiveData<LoginTestResBean>()
//
//    val loginTestLiveData: LiveData<LoginTestResBean>
//        get() = _loginTestLiveData

    //  UnPeek-LiveData解决数据倒灌
//    private val _loadingLiveData = MutableResult<Boolean>()
//
//    val loadingLiveData: Result<Boolean> get() = _loadingLiveData

//    private val _bannerLiveData = MutableResult<WanBaseResBean<List<WanDataBean>>>()
//
//    val bannerLiveData: Result<WanBaseResBean<List<WanDataBean>>>
//        get() = _bannerLiveData

//    //  UnPeek-LiveData解决数据倒灌
//    private val _progressBarLiveData = MutableResult<Boolean>()
//
//    val progressBarLiveData: Result<Boolean>
//        get() = _progressBarLiveData

//    private val _loginTestLiveData = MutableResult<LoginTestResBean>()
//
//    val loginTestLiveData: Result<LoginTestResBean>
//        get() = _loginTestLiveData

    init {
        getBanner()
    }

    /**
     * 请求Banner
     */
    fun getBanner() {
        viewModelScope.launch {
            try {
                _loadingLiveData.value = true
                val banner = WanApiRepository.getBanner()
                _bannerLiveData.value = banner
                // 全局ViewModel,替换EventBus
                MyApp.appViewModel.bannerEvent.value = banner.data[2]
            } catch (error: Throwable) {
                _loadingLiveData.value = false
            } finally {
                // 模拟延迟，方便观察请求加载状态变化
                delay(3000)
                _loadingLiveData.value = false
            }
        }
    }

//    /**
//     * 测试POST-JSON
//     * 登录测试接口1
//     */
//    fun loginTest(username: String, password: String) {
//        viewModelScope.launch {
//            try {
//                _progressBarLiveData.value = true
////                val loginTestResBean = wanApiRepository.loginTest(username, password)
//                val loginTestResBean = wanApiRepository.loginTest2(username, password)
//                _loginTestLiveData.value = loginTestResBean
//            } catch (error: Throwable) {
//                _progressBarLiveData.value = false
//            } finally {
//                // 模拟延迟，方便观察请求加载状态变化
//                delay(3000)
//                _progressBarLiveData.value = false
//            }
//        }
//    }

    /**
     * Helper function to call a data load function with a loading spinner; errors will trigger a snackbar.
     * Helper函数使用加载微调器调用数据加载函数;错误将触发一个snackbar
     * By marking block as suspend this creates a suspend lambda which can call suspend functions.
     * 通过将block标记为挂起，这将创建一个可以调用挂起函数的挂起lambda。
     * Params:参数:
     * block - lambda to actually load data. It is called in the viewModelScope.
     * Block - lambda实际加载数据。它在viewModelScope中被调用。
     * Before calling the lambda, the loading spinner will display.
     * 在调用lambda之前，将显示加载微调器。
     * After completion or error, the loading spinner will stop.
     * 完成或出错后，上料转轮将停止。
     *
     */
    //private fun launchDataLoad(block: suspend () -> Unit): Job {
    //        return viewModelScope.launch {
    //            try {
    //                _spinner.value = true
    //                block()
    //            } catch (error: Throwable) {
    //                _snackbar.value = error.message
    //            } finally {
    //                _spinner.value = false
    //            }
    //        }
    //    }
    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {

            } catch (error: Throwable) {

            } finally {

            }
        }
    }

}


@Suppress("UNCHECKED_CAST")
class MainTestViewModelFactory(
    private val application: Application,
    private val wanApiRepository: WanApiRepository,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainTestViewModel(application, wanApiRepository) as T
    }
}
