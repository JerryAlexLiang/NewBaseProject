package com.liang.module_weather.logic.model

/**
 * - Time: 2024/3/22/0022 15:58
 * - User: Jerry
 * - Description: 创建Weather类，将Realtime和Daily对象封装起来
 */
data class WeatherBean(
    val realtime: RealtimeResponse.Result.Realtime,
    val daily: DailyResponse.Result.Daily
)
