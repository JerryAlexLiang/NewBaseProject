package com.liang.module_base.http.cookie

import com.liang.module_base.base.BaseApp
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor

object CookieClass {

    /** 请求cookie */
//    private val cookieJar: PersistentCookieJar by lazy {
//        PersistentCookieJar(
//            SetCookieCache(),
//            SharedPrefsCookiePersistor(BaseApp.appContext)
//        )
//    }

    /**Cookie*/
    private val cookiePersists = SharedPrefsCookiePersistor(BaseApp.appContext)
    val cookieJar = PersistentCookieJar(SetCookieCache(), cookiePersists)

    /**清除Cookie*/
    fun clearCookie() = cookieJar.clear()

    /**是否有Cookie*/
    fun hasCookie() = cookiePersists.loadAll().isNotEmpty()
}