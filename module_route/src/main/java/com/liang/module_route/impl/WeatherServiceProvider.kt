package com.liang.module_route.impl

import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.liang.module_route.route.AppRouter
import com.liang.module_route.service.WeatherService

/**
 * - Time: 2024/3/22/0022 13:12
 * - User: Jerry
 * - Description: 描述:通过依赖注入解耦:服务管理
 * - 路由服务管理中心
 */
class WeatherServiceProvider {

    companion object {
        fun startWeatherMainActivity(context: Context) {
//            // 使用依赖查找的方法发现服务，主动去发现服务并使用
//            val weatherService =
//                ARouter.getInstance().build(AppRouter.WEATHER_APP).navigation() as WeatherService
//            weatherService.startWeatherMainActivity(context)

            ARouter.getInstance().navigation(WeatherService::class.java)
                .startWeatherMainActivity(context)
        }
    }
}
