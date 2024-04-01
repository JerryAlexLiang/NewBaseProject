package com.liang.module_weather.logic.net

import com.liang.module_weather.WeatherApp
import com.liang.module_weather.logic.model.PlaceResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * - Time: 2024/3/22/0022 15:47
 * - User: Jerry
 * - Description: 彩云天气城市搜索Retrofit接口
 */
interface PlaceApiService {

    /**
     * - 1、搜索城市只有query一个参数是需要动态指定的，使用@Query注解的方式来实现；
     * - 2、另外两个参数是不会变的，因此固定写在@GET注解中即可；
     * - 3、返回值声明为Call<PlaceResponse>，这样Retrofit就会将服务器返回的JSON数据自动解析成PlaceResponse对象
     * - 4、@Url cannot be used with @GET URL (parameter #1)
     */
    @GET("v2/place?token=${WeatherApp.WEATHER_API_TOKEN}&lang=zh_CN")
//    suspend fun searchPlaces(@Url url: String, @Query("query") query: String): PlaceResponse
    suspend fun searchPlaces(@Query("query") query: String): PlaceResponse
}