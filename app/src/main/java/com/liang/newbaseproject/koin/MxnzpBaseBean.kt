package com.liang.newbaseproject.koin

import com.liang.module_base.http.model.DataStatus

data class MxnzpBaseBean<T>(
    val code: Int? = -1,
    val `data`: T? = null,
    val msg: String? = null,

    //数据状态
    var dataStatus: DataStatus? = null,
)