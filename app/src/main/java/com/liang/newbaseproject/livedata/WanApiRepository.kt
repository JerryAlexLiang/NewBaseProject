package com.liang.newbaseproject.livedata

import com.liang.module_base.http.net.RetrofitManager
import com.liang.newbaseproject.bean.WanDataBean
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

/**
 * - Time: 2023/6/1/0001 on 14:56
 * - User: Jerry
 * - Description: Repository仓库层-object类
 */
object WanApiRepository {

    //    private val wanApiService by lazy { RetrofitManager.getWanApiService() }
    private val wanApiService by lazy { RetrofitManager.getApiService(WanApiService::class.java) }


    /**
     * 请求Banner
     * Room 和 Retrofit 均会让挂起函数具有主线程安全性。
     * 尽管这些挂起函数从网络中提取数据并将数据写入数据库，您可以安全地从 Dispatchers.Main 调用这些函数
     * Room 和 Retrofit 都使用自定义调度程序，而不使用 Dispatchers.IO。
     * Room 会使用已配置的默认查询和事务 Executor 运行协程。
     * Retrofit 将在后台创建新的 Call 对象，并对其调用队列以异步发送请求。
     *
     * 无需使用 withContext 来调用主线程安全挂起函数。
     * 由于 Room 和 Retrofit 都提供主线程安全挂起函数，
     * 因此可以安全地通过 Dispatchers.Main 安排此异步工作。
     */
//    suspend fun getBanner(): WanBaseResBean<List<WanDataBean>> =
//        withContext(Dispatchers.IO) {
//            val banner = wanApiService.getBanner()
//            banner
//        }
//    suspend fun getBanner(): WanBaseResBean<List<WanDataBean>> {
//        return wanApiService.getBanner()
//    }
    suspend fun getBanner(): WanBaseResBean<List<WanDataBean>> =
        wanApiService.getBanner()

    /**
     * 测试POST-JSON
     * 登录测试接口1
     */
//    suspend fun loginTest(username: String, password: String): LoginTestResBean =
//        withContext(Dispatchers.IO) {
//            // 封装JSON数据
//            val parameter = JSONObject()
//            parameter.put("username", username)
//            parameter.put("password", password)
//            val requestBody = parameter.toString()
//                .toRequestBody("application/json".toMediaTypeOrNull())
//
//            val loginTestResBean =
//                wanApiService.loginTest(RetrofitManager.BASE_URL2 + "signin", requestBody)
//            loginTestResBean
//        }

    suspend fun loginTest(username: String, password: String): LoginTestResBean {
        // 封装JSON数据
        val parameter = JSONObject()
        parameter.put("username", username)
        parameter.put("password", password)
        // Okhttp3-不推荐使用RequestBody.create（contentType，content）
        // Java解决方案： 采用 create(String, MediaType) 代替 create(MediaType, String)
        // Kotlin解决方案： 使用扩展功能 content.toRequestBody(contentType);对于文件类型 file.asRequestBody(contentType)
        // val mediaType = "application/json; Accept: application/json".toMediaTypeOrNull()
        val requestBody = parameter.toString()
            .toRequestBody("application/json".toMediaTypeOrNull())
        return wanApiService.loginTest(RetrofitManager.BASE_URL2 + "signin", requestBody)
    }

    suspend fun loginTest2(username: String, password: String): LoginTestResBean {
        val loginTestRequestBean = LoginTestRequestBean()
        loginTestRequestBean.username = username
        loginTestRequestBean.password = password
        return wanApiService.loginTest2(RetrofitManager.BASE_URL2 + "signin", loginTestRequestBean)
    }

}