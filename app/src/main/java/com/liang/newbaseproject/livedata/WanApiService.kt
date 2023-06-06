package com.liang.newbaseproject.livedata

import com.liang.module_base.http.model.BaseResult
import com.liang.newbaseproject.bean.WanDataBean
import okhttp3.RequestBody
import retrofit2.http.*

interface WanApiService {

    /**
     * Banner
     */
    @GET("/banner/json")
    suspend fun getBanner(): WanBaseResBean<List<WanDataBean>>

    /**
     * 首页文章列表
     * https://www.wanandroid.com/article/list/0/json
     */
    @GET("/article/list/{pageNo}/json")
    suspend fun getHomeArticleList(
        @Path("pageNo") pageNo: Int,
        @Query("page_size") pageSize: Int
    ): BaseResult<WanArticlePageBean>

    /**
     * 测试POST-JSON
     * 登录测试接口1
     * Retrofit动态设置baseUrl和@url的使用
     * 1、请求方法注解如@GET,@POST，注解（）不能携带值，只能单独使用
     * 2、请求方法注解如@GET,@POST，注解（）不能携带值，只能单独使用
     */
    @POST
    suspend fun loginTest(
        @Url url: String,
        @Body requestBody: RequestBody,
    ): LoginTestResBean

    /*JSON化参数POST请求 */
    @POST
    suspend fun loginTest2(
        @Url url: String,
        @Body body: LoginTestRequestBean
    ): LoginTestResBean

}