package com.liang.module_weather.logic.net

import com.liang.module_weather.WeatherApp
import com.liang.module_weather.logic.model.DailyResponse
import com.liang.module_weather.logic.model.RealtimeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

/**
 * - Time: 2024/3/22/0022 15:59
 * - User: Jerry
 * - Description: 天气API
 */
interface WeatherApiService {

    // 实时天气
    @GET("v2.5/${WeatherApp.WEATHER_API_TOKEN}/{lng},{lat}/realtime.json")
    suspend fun getRealtimeWeather(
        @Url url: String,
        @Path("lng") lng: String,
        @Path("lat") lat: String
    ): RealtimeResponse

    // 未来几天天气
    @GET("v2.5/${WeatherApp.WEATHER_API_TOKEN}/{lng},{lat}/daily.json")
    suspend fun getDailyWeather(
        @Url url: String,
        @Path("lng") lng: String,
        @Path("lat") lat: String
    ): DailyResponse
}
