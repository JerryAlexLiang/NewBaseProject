package com.liang.module_weather

import android.content.Context
import com.liang.module_base.base.BaseApp
import com.liang.module_base.utils.LogUtils
import com.liang.module_base.utils.ToastUtil
import com.liang.module_weather.di.weatherModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

//class WeatherApp : BaseApp(),BaseApplicationImpl {
class WeatherApp : BaseApp() {

    lateinit var context: Context

    companion object {
        // 彩云天气API-申请的命令牌
        const val WEATHER_API_TOKEN = "V7I0xO493GuCeYpm"
    }

    private fun initKoin() {
        LogUtils.d("**********initKoinWeather**********")
        ToastUtil.showSuccessRectangleToast(context, true, "init Koin Success")

        startKoin {
            androidLogger()
            androidContext(this@WeatherApp)
            modules(weatherModel)
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        // 3、第三步：在代码中读取gradle.propeties(执行完前两步后先同步并且build一下)
        //private boolean isUseIjkPlayer = BuildConfig.IS_USE_IJKPLAYER_FOR_VOD;
        if (BuildConfig.isModuleWeatherBuildModule) {
            initKoin()
        }

    }

//    override fun onCreate(application: Application?) {
//        context = applicationContext
//        initKoin()
//    }
//
//    override fun onCreate(application: Application?, isDebug: Boolean) {
//
//    }

}