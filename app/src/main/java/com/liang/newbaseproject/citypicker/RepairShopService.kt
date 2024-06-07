package com.liang.newbaseproject.citypicker

import retrofit2.http.GET
import retrofit2.http.Query

fun interface RepairShopService {

    @GET("v1/app/white/dealer/list_mini")
    suspend fun getRepairShopList(@Query("city_id") query: String): RepairShopBean
}