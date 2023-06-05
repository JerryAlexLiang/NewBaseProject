package com.liang.module_base.http.model

enum class DataStatus {

    //加载中
    STATE_LOADING,

    //1.服务器处理后的失败  2.网络等原因导致的失败
    STATE_ERROR,

    //成功
    STATE_SUCCESS,

}