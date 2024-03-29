package com.liang.module_base.utils

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * @Time: 2023/5/15/0015 on 17:36
 * @User: Jerry
 * @Description: 基于Moshi的Json转换封装
 */
object MoshiUtil {

    abstract class MoshiTypeReference<T> // 自定义的类，用来包装泛型

    val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory()).build()

    inline fun <reified T> toJson(src: T, indent: String = ""): String {
        try {
            val jsonAdapter = moshi.adapter<T>(getGenericType<T>())
            return jsonAdapter.indent(indent).toJson(src)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    inline fun <reified T> formatToJson(src: T): String {
        return toJson(src, " ")
    }

    inline fun <reified T> fromJson(jsonStr: String): T? {
        try {
            val jsonAdapter = moshi.adapter<T>(getGenericType<T>())
            return jsonAdapter.fromJson(jsonStr)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    inline fun <reified T> getGenericType(): Type {
        return object :
            MoshiTypeReference<T>() {}::class.java
            .genericSuperclass
            .let { it as ParameterizedType }
            .actualTypeArguments
            .first()
    }

}

