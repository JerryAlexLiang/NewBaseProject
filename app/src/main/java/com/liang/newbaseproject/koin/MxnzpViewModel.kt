package com.liang.newbaseproject.koin

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.liang.module_base.base.BaseViewModel
import com.liang.module_base.http.model.DataStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MxnzpViewModel(application: Application, private val mxnzpApiRepository: MxnzpApiRepository) :
    BaseViewModel(application) {

    private val _douyinListLiveData = MutableLiveData<MxnzpBaseBean<List<DouyinData>>>()

    val douyinListLiveData: LiveData<MxnzpBaseBean<List<DouyinData>>>
        get() = _douyinListLiveData

    /**
     * 创建了一个 MutableStateFlow 对象 _loadState 来表示加载请求状态，并在 flow {} 块中更新了该状态。
     * 当加载数据时，将 _loadState 设置为 STATE_LOADING 状态。
     * 当数据加载成功时，将 _loadState 设置为 STATE_SUCCESS 状态，并发出数据。
     * 当数据加载失败时，将 _loadState 设置为 STATE_ERROR 状态
     */
    private val _loadState = MutableStateFlow(DataStatus.STATE_SUCCESS)

    val loadState: StateFlow<DataStatus> = _loadState

//    val loadState: LiveData<DataStatus> = _loadState.asLiveData()

    private val _searchJokesMutableStateFlow =
        MutableStateFlow<MxnzpBaseBean<List<DouyinData>>>(MxnzpBaseBean())

    val searchJokesStateFlow: StateFlow<MxnzpBaseBean<List<DouyinData>>>
        get() = _searchJokesMutableStateFlow

    private val _douyinMutableStateFlow =
        MutableStateFlow<MxnzpBaseBean<List<DouyinData>>>(MxnzpBaseBean())

    val douyinStateFlow: StateFlow<MxnzpBaseBean<List<DouyinData>>>
        get() = _douyinMutableStateFlow

    /**
     * 搜索段子
     * 协程Flow
     * flow {} 块中返回的 Flow 对象是一个冷流（cold stream），
     * 即 Flow 中的数据不会在创建时立即执行，而是在订阅时才开始执行。
     * 因此，在使用 flow {} 块时，不需要在 ViewModel 中使用协程作用域来启动协程。
     *
     * 将 Flow 对象作为 LiveData 对象公开并观察 LiveData 时，ViewModel 会自动启动协程来获取 Flow 中的数据。
     * 这意味着在 ViewModel 中不需要在协程作用域中使用 flow {} 块。
     */
    fun searchJokesByFlow(
        keyword: String,
        page: String
    ) = flow {
        _loadState.value = DataStatus.STATE_LOADING
        val data = mxnzpApiRepository.searchJokesByFlow(keyword, page)
        _loadState.value = DataStatus.STATE_SUCCESS
        emit(data)
    }.catch {
        emit(MxnzpBaseBean())
        _loadState.value = DataStatus.STATE_ERROR
    }.asLiveData()

    /**
     * 在Kotlin中，StateFlow是一个新的数据流类，是在Flow的基础上增加了一些特性而来。
     * StateFlow是一个可观察的数据容器，表示具有单一值的可变状态。它维护一个当前值，并且每当值发生变化时都会发射新值，
     * 从而允许多个观察者观察该值的变化。
     *
     * 1、StateFlow的.value属性和emit()函数都可以用于发射新的值。它们的区别在于：
     * .value属性是一个只读的属性，用于获取当前的值。当订阅者订阅StateFlow时，会立即收到最近的值，并且每当值发生变化时都会收到新值。
     * .value属性的访问是线程安全的，可以在任何线程上进行读取，但不能在非协程上下文中读取。
     *
     * 2、emit()函数是用于发射新值的函数。当调用emit()函数时，订阅者会收到新的值。
     * emit()函数只能在协程上下文中调用，因为它会挂起协程并在发射完新值后恢复协程的执行。
     *
     * 因此，.value属性适用于获取当前值，并且在订阅者订阅之前就已经确定值的情况。
     * 而emit()函数适用于在订阅者订阅后，根据某些条件或事件发射新值的情况。
     * 请注意，如果您使用StateFlow，应该避免使用.emit()函数在非协程上下文中发射值，以免引发线程安全问题。
     *
     * 使用场景区分：
     * 1、在ViewModel中使用StateFlow时，通常情况下应该优先使用.value属性来获取StateFlow的当前值。
     * 这是因为.value属性是一个只读属性，不会改变StateFlow的状态，而且在订阅者订阅之前就已经确定了值。
     * 因此，.value属性适用于那些已经确定值或在初始化时设置值的情况，例如：
     * (1)、ViewModel的初始化过程中，需要设置一些默认的StateFlow的值。
     * (2)、在ViewModel中需要提供一些LiveData的替代方案，以便观察某些状态的变化。
     *
     * 需要注意的是，虽然.value属性是线程安全的，但如果在非协程上下文中访问.value属性，可能会阻塞UI线程，
     * 因此在非协程上下文中应该避免使用.value属性。
     *
     * 2、.emit()函数应该在需要动态更新StateFlow的状态时使用。通常情况下，emit()函数应该在协程上下文中调用。
     * 例如：
     * (1)、当用户在界面上触发某些事件时，需要根据事件来更新StateFlow的状态。
     * 在这种情况下，可以使用.emit()函数来发射新的值，以便订阅者能够观察到新的状态。
     * (2)、另外，在.emit()函数中还可以进行异步的操作，例如向服务器请求数据等。
     *
     * 需要注意的是，由于.emit()函数可以改变StateFlow的状态，因此在多个协程中同时调用.emit()函数时可能会引起线程安全问题，
     * 应该使用线程安全的方式来管理StateFlow的状态。
     *
     * 注意：
     * 如果网络请求的结果不变，StateFlow的collect方法会停留在原地，不会再次触发监听器的回调函数。
     * 这是因为StateFlow是一个响应式数据流，它只在值发生变化时才会触发新的数据流，以避免不必要的数据传递和计算。
     * 如果想在网络请求的结果不变时，也能够触发StateFlow的监听器，可以考虑使用replay缓存来存储最近的数据流，
     * 并使用resetReplayCache方法来重置缓存。这样做可以使StateFlow重新发送最近的数据流，从而触发监听器的回调函数。
     *
     * 但是，请注意，使用resetReplayCache方法可能会影响性能，因为它会重置StateFlow的replay缓存，并重新发送最近的数据流。
     * 因此，如果您的网络请求频繁，并且结果不经常变化，最好不要使用resetReplayCache方法，以避免性能问题。
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun searchJokesByStateFlow(keyword: String, page: String) {
        viewModelScope.launch {
            try {
                _loadState.value = DataStatus.STATE_LOADING
                val data = mxnzpApiRepository.searchJokesByFlow(keyword, page)
                _loadState.value = DataStatus.STATE_SUCCESS
                _searchJokesMutableStateFlow.emit(data)
                // 在网络请求的协程中更新StateFlow的值，并使用resetReplayCache方法重置StateFlow的replay缓存。
                // 这样，即使网络请求的结果不变，StateFlow也会重新发送最近的数据流，从而触发监听器的回调函数。
                _searchJokesMutableStateFlow.resetReplayCache()
            } catch (error: Throwable) {
                _searchJokesMutableStateFlow.emit(MxnzpBaseBean())
                _loadState.value = DataStatus.STATE_ERROR
            } finally {
                _loadState.value = DataStatus.STATE_SUCCESS
            }
        }
    }

    fun getMxnzpDouyinListByStateFlow() {
        viewModelScope.launch {
            try {
                _loadState.value = DataStatus.STATE_LOADING
                val data = mxnzpApiRepository.getMxnzpDouyinList()
                _loadState.value = DataStatus.STATE_SUCCESS
                _douyinMutableStateFlow.emit(data)
//                _douyinMutableStateFlow.value = data
            } catch (error: Throwable) {
                _douyinMutableStateFlow.emit(MxnzpBaseBean())
//                _douyinMutableStateFlow.value = MxnzpBaseBean()
                _loadState.value = DataStatus.STATE_ERROR
            } finally {
                _loadState.value = DataStatus.STATE_SUCCESS
            }

        }
    }


    /**
     * Mxnzp-获取划一划页面的推荐列表数据
     * getMxnzpDouyinList
     */
    fun getMxnzpDouyinList() {
//        launchDataLoad {
//            val mxnzpDouyinList = mxnzpApiRepository.getMxnzpDouyinList()
//            _douyinListLiveData.value = mxnzpDouyinList
//        }

//        launchDataLoadStatus({
//            val mxnzpDouyinList = mxnzpApiRepository.getMxnzpDouyinList()
//            _douyinListLiveData.value = mxnzpDouyinList
//            mxnzpDouyinList
//        }, _douyinListLiveData)

//        launchDataLoadStatus({ mxnzpApiRepository.getMxnzpDouyinList() }, _douyinListLiveData)

        launchDataLoadStatus(
            block = { mxnzpApiRepository.getMxnzpDouyinList() },
            stateLiveData = _douyinListLiveData
        )
    }

    private val _mockLiveData = MutableLiveData<MxnzpBaseBean<MockResBean>>()

    val mockLiveData: LiveData<MxnzpBaseBean<MockResBean>>
        get() = _mockLiveData

    fun getMock(mockRequestBean: MockRequestBean) {
//        launchDataLoadStatus({mxnzpApiRepository.getMock(mockRequestBean)},_mockLiveData)

        viewModelScope.launch {
            val mock = mxnzpApiRepository.getMock(mockRequestBean)
            _mockLiveData.value = mock
        }
    }

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
    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                block()
            } catch (error: Throwable) {
                Log.d("MxnzpViewModel", "launchDataLoad: $error")
            } finally {
                // 模拟延迟，方便观察请求加载状态变化
                delay(3000)
            }
        }
    }

    private fun <T : Any> launchDataLoadStatus(
        block: suspend () -> MxnzpBaseBean<T>,
        stateLiveData: MutableLiveData<MxnzpBaseBean<T>>,
    ): Job {
        return viewModelScope.launch {
            var mxnzpBaseBean = MxnzpBaseBean<T>()
            try {
                mxnzpBaseBean.dataStatus = DataStatus.STATE_LOADING
                // 赋值结果前改变状态（此时会调用onChanged方法）
                stateLiveData.postValue(mxnzpBaseBean)
                // 将结果赋值给mxnzpBaseBean
//                block()
                mxnzpBaseBean = block.invoke()
                if (mxnzpBaseBean.code == 200) {
                    //服务器请求成功
                    mxnzpBaseBean.dataStatus = DataStatus.STATE_SUCCESS
                } else {
                    //服务器请求错误
                    mxnzpBaseBean.dataStatus = DataStatus.STATE_ERROR
                }
            } catch (error: Throwable) {
                //非后台返回错误，捕获到的异常
                mxnzpBaseBean.dataStatus = DataStatus.STATE_ERROR
                Log.d("MxnzpViewModel", "launchDataLoad: $error")
            } finally {
                // 最终发射节点（此时会调用onChanged方法）
                stateLiveData.postValue(mxnzpBaseBean)
            }
        }
    }

}