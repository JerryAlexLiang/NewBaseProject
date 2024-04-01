package com.liang.module_weather.ui.weather

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.liang.module_base.base.BaseViewModel
import com.liang.module_base.utils.LogUtils
import com.liang.module_weather.WeatherConstant
import com.liang.module_weather.logic.model.WeatherBean
import com.liang.module_weather.logic.net.WeatherRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * - Time: 2024/3/28/0028 15:11
 * - User: Jerry
 * - Description: 天气预报ViewModel
 */
class WeatherViewModel(application: Application, val weatherRepository: WeatherRepository) :
    BaseViewModel(application) {

//    private val _weatherLiveData: MutableLiveData<Location> = MutableLiveData<Location>()
//    val weatherLiveData: LiveData<Location>
//        get() = _weatherLiveData

    private val _weatherLiveData: MutableLiveData<WeatherUiState> =
        MutableLiveData<WeatherUiState>()
    val weatherLiveData: LiveData<WeatherUiState>
        get() = _weatherLiveData

    // 是否是打开APP时的刷新状态
    var isFirstRefresh = true

    // 和界面相关的数据，定义在ViewModel中，可以保证它们在手机屏幕发生旋转的时候不会丢失，后面在编写UI层代码的时候就会用到这几个变量
    // 定义3个变量locationLng、locationLat、cityName
    var locationLat = ""
    var locationLng = ""
    var cityName = ""

    fun refreshWeather(lng: String, lat: String) {
        _weatherLiveData.value = WeatherUiState.Loading

        viewModelScope.launch {
            try {
                //1、async函数必须在协程作用域内才能调用，它会创建一个新的子协程并返回一个Deferred对象；
                //2、如果我们想要获取async函数代码块中的执行结果，只需要调用Deferred对象的await()方法即可。
                val realtimeWeatherDeferred = async {
                    weatherRepository.getRealtimeWeather(lng, lat)
                }

                val dailyWeatherDeferred = async {
                    weatherRepository.getDailyWeather(lng, lat)
                }

                //3、如果我们想要获取async函数代码块中的执行结果，只需要调用Deferred对象的await()方法即可。
                //4、在调用了async函数之后，代码块中的代码就会立刻开始执行，当调用await()方法时，如果代码块中的代码还没有执行完，
                // 那么await()方法会将当前协程阻塞住，知道可以获得async函数的执行结果
                val realtimeResponse = realtimeWeatherDeferred.await()
                val dailyResponse = dailyWeatherDeferred.await()

                //在同时获取到RealtimeResponse和DailyResponse之后，判断响应状态
                if (realtimeResponse.status == WeatherConstant.STATUS_OK && dailyResponse.status == WeatherConstant.STATUS_OK) {
                    val weatherBean =
                        WeatherBean(realtimeResponse.result.realtime, dailyResponse.result.daily)
                    _weatherLiveData.value = WeatherUiState.Success(weatherBean)
                } else {
                    val errorMsg =
                        "realtime response status is ${realtimeResponse.status} , daily response status is ${dailyResponse.status}"
                    _weatherLiveData.value = WeatherUiState.Error(errorMsg)
                    LogUtils.e(tag = "refreshWeather", msg = errorMsg)
                }

            } catch (exception: Exception) {
                _weatherLiveData.value = WeatherUiState.Error(exception.message.toString())
                LogUtils.e(tag = "refreshWeather", msg = exception.message.toString())
            }
        }
    }
}