package com.liang.newbaseproject.livedata

/**
 * @Time: 2023/3/27/0027 on 14:54
 * @User: Jerry
 * @Description: 接口返回外层封装实体
 */
data class WanBaseResBean<T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String
)
