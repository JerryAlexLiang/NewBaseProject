package com.liang.module_base.utils

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import org.json.JSONException
import org.json.JSONObject

/**
 * - 创建日期：2019/8/21 on 10:17
 * - 描述: 封装的是使用Gson解析json的方法
 * - 作者: liangyang
 */
object GsonUtilsKt {

    /**
     * 把一个json字符串变成对象
     *
     * @param json
     * @param cls
     * @return
     */
    fun <T> parseJsonObjectToBean(json: String?, cls: Class<T>?): T? {
        val gson = Gson()
        var t: T? = null
        t = try {
            gson.fromJson(json, cls)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return t
    }

    //解析json数组
    fun <T> parseJsonArrayToBean(json: String?, clazz: Class<T>?): List<T>? {
        var lst: MutableList<T>? = null
        try {
            lst = ArrayList()
            val array = JsonParser().parse(json).asJsonArray
            for (elem in array) {
                lst.add(Gson().fromJson(elem, clazz))
            }
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            return null
        }
        return lst
    }

    /**
     * 对象转为String字符串
     *
     * @param target
     * @return
     */
    fun toJson(target: Any?): String {
        val gson = Gson()
        return gson.toJson(target)
    }

    /**
     * 获取json串中某个字段的值，注意，只能获取同一层级的value
     *
     * @param json
     * @param key
     * @return
     */
    fun getFieldValue(json: String, key: String?): String? {
        if (TextUtils.isEmpty(json)) return null
        if (!json.contains(key!!)) return ""
        var jsonObject: JSONObject? = null
        var value: String? = null
        try {
            jsonObject = JSONObject(json)
            value = jsonObject.getString(key)
        } catch (e: JSONException) {
            e.printStackTrace()
            return ""
        }
        return value
    }
}