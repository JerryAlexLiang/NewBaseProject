package com.liang.newbaseproject.bean

data class FunItemBean(
    val funNameResId: Int,
    var funNameChinese: String = "",
    var funNameEnglish: String = "",
    val activity: Class<*>? = null,
    val imageResource: Int = 0,
)


