package com.liang.newbaseproject.koin

import com.google.gson.Gson
import com.liang.module_base.http.model.BaseResult
import com.liang.module_base.http.net.RetrofitManager
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody


class MxnzpApiRepository(private val mxnzpApiService: MxnzpApiService) {
//object  MxnzpApiRepository {

//    private val mxnzpApiService by lazy { RetrofitManager.getApiService(MxnzpApiService::class.java) }
    /**
     * 获取主页的推荐关注数据
     * home/attention/recommend
     */
    suspend fun getHomeAttentionRecommendList(): BaseResult<MutableList<HomeRecommendBean>> =
        mxnzpApiService.getHomeAttentionRecommendList(RetrofitManager.BASE_URL_Mxnzp + "home/attention/recommend")

    /**
     * Mxnzp-获取划一划页面的推荐列表数据
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
//    suspend fun getMxnzpDouyinList(): MxnzpBaseBean<List<DouyinData>> =
//        withContext(Dispatchers.IO) {
//            mxnzpApiService.getMxnzpDouyinList(RetrofitManager.BASE_URL_Mxnzp + "douyin/list")
//        }

    suspend fun getMxnzpDouyinList(): MxnzpBaseBean<List<DouyinData>> =
        mxnzpApiService.getMxnzpDouyinList(RetrofitManager.BASE_URL_Mxnzp + "douyin/list")

    /**
     * 搜索段子
     * 协程Flow
     * /home/jokes/search
     */
    suspend fun searchJokesByFlow(
        keyword: String,
        page: String
    ): MxnzpBaseBean<List<DouyinData>> =
        mxnzpApiService.searchJokesByFlow(
            RetrofitManager.BASE_URL_Mxnzp + "home/jokes/search",
            keyword,
            page
        )

    suspend fun getMock(mockRequestBean: MockRequestBean): MxnzpBaseBean<MockResBean> {
        val gson = Gson()
        val route: String = gson.toJson(mockRequestBean)
//        val requestBody: RequestBody =
//            RequestBody.create(
//                "application/json; Accept: application/json".toMediaTypeOrNull(),
//                route
//            )

        // Okhttp3-不推荐使用RequestBody.create（contentType，content）
        // Java解决方案： 采用 create(String, MediaType) 代替 create(MediaType, String)
        // Kotlin解决方案： 使用扩展功能 content.toRequestBody(contentType);对于文件类型 file.asRequestBody(contentType)
        val mediaType = "application/json; Accept: application/json".toMediaTypeOrNull()
        val requestBody = route.toRequestBody()

        return mxnzpApiService.getMock(
            RetrofitManager.BASE_URL_Mock + "model/v2/open/engines/chatglm_qa_6b/chatglm_6b",
            requestBody
        )
    }
}
