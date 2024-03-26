package com.liang.module_weather.logic.net

import com.liang.module_base.http.net.RetrofitManager
import com.liang.module_weather.logic.model.DailyResponse
import com.liang.module_weather.logic.model.PlaceResponse
import com.liang.module_weather.logic.model.RealtimeResponse

class WeatherRepository(
    private val placeService: PlaceApiService,
    private val weatherService: WeatherApiService
) {

    /**
     * - 1、搜索城市只有query一个参数是需要动态指定的，使用@Query注解的方式来实现；
     * - 2、另外两个参数是不会变的，因此固定写在@GET注解中即可；
     * - 3、返回值声明为Call<PlaceResponse>，这样Retrofit就会将服务器返回的JSON数据自动解析成PlaceResponse对象
     */
    suspend fun searchPlaces(query: String): PlaceResponse {
        return placeService.searchPlaces(query)
    }

    /**
     * 实时天气
     */
    suspend fun getRealtimeWeather(lng: String, lat: String): RealtimeResponse {
        return weatherService.getRealtimeWeather(RetrofitManager.WEATHER_BASE_URL, lng, lat)
    }

    /**
     * 未来几天天气
     */
    suspend fun getDailyWeather(lng: String, lat: String): DailyResponse {
        return weatherService.getDailyWeather(RetrofitManager.WEATHER_BASE_URL, lng, lat)
    }
}