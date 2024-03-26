package com.liang.module_base.http.net

import com.liang.module_base.base.BaseApp.Companion.appContext
import com.liang.module_base.http.cookie.CookieClass
import com.liang.module_base.http.interceptor.CacheInterceptor
import com.liang.module_base.http.interceptor.logInterceptor
import com.liang.module_base.utils.LogUtils
import com.liang.module_base.utils.MoshiUtil
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @Time: 2023/3/24/0024 on 15:39
 * @User: Jerry
 * @Description: Retrofit类
 */
object RetrofitManager {

    /** 请求超时时间 */
    private const val TIME_OUT_SECONDS = 10

    private val logger =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

//    /** 请求cookie */
//    private val cookieJar: PersistentCookieJar by lazy {
//        PersistentCookieJar(
//            SetCookieCache(),
//            SharedPrefsCookiePersistor(appContext)
//        )
//    }

    private const val BASE_URL = "https://www.wanandroid.com/"
    const val BASE_URL2 = "http://39.104.85.192:8080/hyundai/"
    const val BASE_URL_Mxnzp = "http://tools.cretinzp.com/jokes/"
    const val BASE_URL_Mock = "https://maas.aminer.cn/api/paas/"

    //彩云天气API
    const val WEATHER_BASE_URL = "https://api.caiyunapp.com/"

    /** OkHttpClient相关配置 */
    private val client: OkHttpClient
        get() = OkHttpClient.Builder()
            // 请求过滤器
//            .addInterceptor(logger)
            .addInterceptor(logInterceptor)
            //设置缓存配置,缓存最大10M,设置了缓存之后可缓存请求的数据到data/data/包名/cache/net_cache目录中
            .cache(Cache(File(appContext.cacheDir, "net_cache"), 10 * 1024 * 1024))
            //添加缓存拦截器 可传入缓存天数
            .addInterceptor(CacheInterceptor(30))
            .callTimeout(TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
            // 请求超时时间
            .connectTimeout(TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT_SECONDS.toLong(), TimeUnit.SECONDS)
//            .cookieJar(cookieJar)
            .cookieJar(CookieClass.cookieJar)
//            .cookieJar(MyCookieJar())
            .retryOnConnectionFailure(true)
            .followRedirects(false)
            .build()

    /**
     * Retrofit配置
     * GSON、Moshi配合retrofit
     */
    fun <T> getApiService(serviceClass: Class<T>, baseUrl: String? = null): T {
        LogUtils.d(msg = "BaseUrl：$baseUrl")
        return Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl ?: BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(MoshiUtil.moshi))
            .build()
            .create(serviceClass)
    }

//    fun getWanApiService(): WanApiService {
//        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
//
////        val client = OkHttpClient.Builder()
////            .addInterceptor(logger)
////            .build()
//
//        return Retrofit.Builder()
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl(BASE_URL)
//            .build()
//            .create(WanApiService::class.java)
//    }

}
