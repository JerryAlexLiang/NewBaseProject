package com.liang.newbaseproject.livedata

import com.liang.module_base.http.model.BaseResult

class WanApiRepositoryKoin(private val wanApiService: WanApiService) {
//object WanApiRepositoryKoin {

//    private val wanApiService by lazy { RetrofitManager.getApiService(WanApiService::class.java) }

    /**
     * 首页文章列表
     * https://www.wanandroid.com/article/list/0/json
     * @Path("pageNo") pageNo: Int,
     * @Query("page_size") pageSize: Int
     */
    suspend fun getHomeArticleList(pageNo: Int, pageSize: Int): BaseResult<WanArticlePageBean> =
        wanApiService.getHomeArticleList(pageNo, pageSize)
}