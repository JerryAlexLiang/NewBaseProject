package com.liang.newbaseproject.citypicker

import com.google.gson.annotations.SerializedName

data class ProvinceInfo(
        @SerializedName("cityList")
        val cityList: List<City>,
        @SerializedName("code")
        val code: String, // 340000
        @SerializedName("name")
        val name: String // 安徽
)

data class City(
        @SerializedName("areaList")
        val areaList: List<Area>,
        @SerializedName("code")
        val code: String, // 340100
        @SerializedName("name")
        val name: String // 合肥
)

data class Area(
        @SerializedName("code")
        val code: String, // 340102
        @SerializedName("name")
        val name: String // 瑶海区
)