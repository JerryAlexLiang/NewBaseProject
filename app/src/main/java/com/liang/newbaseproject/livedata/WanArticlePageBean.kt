package com.liang.newbaseproject.livedata

import com.liang.newbaseproject.livedata.WanArticleBean

data class WanArticlePageBean(
    var curPage: Int?,
    var datas: List<WanArticleBean>?,
    var offset: Int?,
    var over: Boolean?,
    var pageCount: Int?,
    var size: Int?,
    var total: Int?
)