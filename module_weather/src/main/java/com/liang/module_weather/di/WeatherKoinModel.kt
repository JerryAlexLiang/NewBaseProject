package com.liang.module_weather.di

import com.liang.module_base.http.net.RetrofitManager
import com.liang.module_weather.logic.net.PlaceApiService
import com.liang.module_weather.logic.net.WeatherApiService
import com.liang.module_weather.logic.net.WeatherRepository
import com.liang.module_weather.ui.place.PlaceViewModel
import com.liang.module_weather.ui.weather.WeatherViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val weatherModel = module {
    /** 网络请求 **/
    single {
        RetrofitManager.getApiService(
            PlaceApiService::class.java,
            baseUrl = RetrofitManager.WEATHER_BASE_URL
        )
    }

    single {
        RetrofitManager.getApiService(
            WeatherApiService::class.java,
            baseUrl = RetrofitManager.WEATHER_BASE_URL
        )
    }

    /** 数据仓库 Module */
    // single与factory功能一致，只不过创建的是单例
    single {
        WeatherRepository(get(), get())
    }

    /** ViewModel Module */
    viewModel {
        PlaceViewModel(androidApplication(), get())
    }

    viewModel {
        WeatherViewModel(androidApplication(), get())
    }

}
