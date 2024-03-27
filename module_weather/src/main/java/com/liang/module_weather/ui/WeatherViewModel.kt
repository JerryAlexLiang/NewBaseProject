package com.liang.module_weather.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.liang.module_base.base.BaseViewModel
import com.liang.module_base.utils.LogUtils
import com.liang.module_weather.logic.model.Place
import com.liang.module_weather.logic.model.PlaceResponse
import com.liang.module_weather.logic.net.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application, private val weatherRepository: WeatherRepository) :
    BaseViewModel(application) {

    // 和界面相关的数据，定义在ViewModel中，可以保证它们在手机屏幕发生旋转的时候不会丢失
    // 稍后在编写UI层代码的时候就会用到这几个变量

    // 城市列表数据List
    var placeList = ArrayList<Place>()

    // 搜索返回Place数据
    private val _placeLiveData: MutableLiveData<PlaceUiState> = MutableLiveData<PlaceUiState>()
    val placeLiveData: LiveData<PlaceUiState>
        get() = _placeLiveData

    fun searchPlace(string: String) {
        viewModelScope.launch {
            _placeLiveData.value = PlaceUiState.Loading
            try {
                val places: PlaceResponse = weatherRepository.searchPlaces(string)
                _placeLiveData.value = PlaceUiState.Success(places)
            } catch (exception: Exception) {
                _placeLiveData.value = PlaceUiState.Error(exception.message.toString())
                LogUtils.e("searchPlace", exception.message.toString())
            }
        }
    }
}