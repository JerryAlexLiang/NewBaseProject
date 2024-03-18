package com.liang.newbaseproject.koin

import com.liang.module_base.http.model.BaseResult
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * @Time: 2023/4/6/0006 on 14:11
 * @User: Jerry
 * @Description: 段子乐开源计划后台接口平台
 */
interface MxnzpApiService {

    /**
     * 获取主页的推荐关注数据
     * home/attention/recommend
     */
    @Headers("project_token: D54170CB6AA84AA8AFFD2DFC02AC51F5")
    @POST
    suspend fun getHomeAttentionRecommendList(@Url url: String): BaseResult<MutableList<HomeRecommendBean>>

    /**
     *  Mxnzp-获取划一划页面的推荐列表数据
     *  http://tools.cretinzp.com/jokes/douyin/list
     * Retrofit动态设置baseUrl和@url的使用
     * 1、请求方法注解如@GET,@POST，注解（）不能携带值，只能单独使用
     * 2、请求方法注解如@GET,@POST，注解（）不能携带值，只能单独使用
     *
     * suspend - 和协程联用
     * 使用suspend标记的方法，标记这个是一个协程使用的异步方法
     */
    @Headers("project_token: D54170CB6AA84AA8AFFD2DFC02AC51F5")
    @POST
    suspend fun getMxnzpDouyinList(@Url url: String): MxnzpBaseBean<List<DouyinData>>

    /**
     * 搜索段子
     * 协程Flow
     * /home/jokes/search
     */
    @Headers("project_token: D54170CB6AA84AA8AFFD2DFC02AC51F5")
    @POST
    suspend fun searchJokesByFlow(
        @Url url: String,
        @Query("keyword") keyword: String,
        @Query("page") page: String
    ): MxnzpBaseBean<List<DouyinData>>

    @Headers(
        "Content-type:application/json;charset=utf-8",
        "Authorization: eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyX3R5cGUiOiJTRVJWSUNFIiwidXNlcl9pZCI6NjcwMTQsImFwaV9rZXkiOiIzNThkYzZiYTRkYmM0YTkyODdjN2M1YzIyMjMwMDQxYSIsInVzZXJfa2V5IjoiMGEwNmQzNjgtZTZiNi00MjNiLWIwMjctZDEwMTE5MzhkNTI3IiwiY3VzdG9tZXJfaWQiOiIxNjUxOTMiLCJ1c2VybmFtZSI6IjE4NzIxODU4OTczIn0.iZR9WjHdAM_6pfM9BAuUWAI6gME0l9w21c5LiJsMUUNWOnWIxuuzbcKqHTWCxk9wejVWgvq0tMMTubBJj-6yqg"
    )
    @POST
    suspend fun getMock(
        @Url url: String,
        @Body requestBody: RequestBody,
    ): MxnzpBaseBean<MockResBean>
}