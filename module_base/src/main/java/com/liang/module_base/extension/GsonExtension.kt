package com.liang.module_base.extension

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> Gson.fromJson(json: String?): T =
    fromJson(json, object : TypeToken<T>() {}.type)

/** 将对象转为JSON字符串 */
fun Any?.toJson(): String {
    return Gson().toJson(this)
}