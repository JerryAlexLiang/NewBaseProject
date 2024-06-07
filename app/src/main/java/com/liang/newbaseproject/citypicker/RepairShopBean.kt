package com.liang.newbaseproject.citypicker


import com.google.gson.annotations.SerializedName

data class RepairShopBean(
    @SerializedName("code")
    val code: Int, // 0
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("msg")
    val msg: String,
    @SerializedName("title")
    val title: String
)

data class Data(
    @SerializedName("address")
    val address: String, // 北京北京海淀区佟家坟１号楼
    @SerializedName("bx_dlr_cd")
    val bxDlrCd: String, // D0102
    @SerializedName("lat")
    val lat: Double, // 39.952922
    @SerializedName("lng")
    val lng: Double, // 116.239306
    @SerializedName("name")
    val name: String, // 北京鹏奥
    @SerializedName("short_name")
    val shortName: String // 北京鹏奥
)