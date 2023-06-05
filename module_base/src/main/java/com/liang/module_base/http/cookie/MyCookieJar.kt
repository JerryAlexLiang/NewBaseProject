package com.liang.module_base.http.cookie

import com.liang.module_base.constant.Constants
import com.liang.module_base.utils.LogUtils
import com.liang.module_base.utils.MMKVUtils
import com.google.gson.Gson
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * @Time: 2023/4/25/0025 on 13:23
 * @User: Jerry
 * @Description: MyCookieJar
 */
class MyCookieJar : CookieJar {

    private val gson = Gson()

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val s = MMKVUtils.getString(Constants.USER_COOKIE)
        if (s != null) {
            val l = gson.fromJson(s, CookieBean::class.java).list
            LogUtils.d(tag = "MyCookieJar", msg = "loadForRequest: $l")
            return l
        }
        return arrayListOf()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        LogUtils.d(tag = "MyCookieJar", msg = "saveFromResponse:1")
        if (MMKVUtils.getString(Constants.USER_COOKIE) != null) {
            LogUtils.d(tag = "MyCookieJar", msg = "saveFromResponse:2")
            return
        }

        for (item in cookies) {
            if (item.toString().contains("loginUserName")) {
                LogUtils.d(tag = "MyCookieJar", msg = "saveFromResponse:3")
                MMKVUtils.put(Constants.USER_COOKIE, gson.toJson(CookieBean(list = cookies)))
                break
            }
        }


    }

}