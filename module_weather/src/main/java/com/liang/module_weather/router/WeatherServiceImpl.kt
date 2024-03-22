package com.liang.module_weather.router

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.liang.module_base.utils.ToastUtil
import com.liang.module_route.route.AppRouter
import com.liang.module_route.service.WeatherService
import com.liang.module_weather.WeatherActivity


/**
 * - Time: 2024/3/22/0022 13:04
 * - User: Jerry
 * - Description: 通过依赖注入解耦：服务管理，实现接口
 */
@Route(path = AppRouter.WEATHER_APP)
class WeatherServiceImpl : WeatherService {
    override fun startWeatherMainActivity(context: Context) {
        ToastUtil.showSuccessToast(context, true, "Weather")
        WeatherActivity.actionStart(context)
    }

    override fun init(context: Context?) {

    }
}
