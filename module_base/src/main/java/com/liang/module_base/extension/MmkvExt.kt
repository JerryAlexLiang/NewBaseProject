package com.liang.module_base.extension

import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.StringUtils
import com.liang.module_base.base.BaseApp
import com.liang.module_base.http.cookie.CookieClass
import com.liang.module_base.utils.MMKVUtils
import com.google.gson.reflect.TypeToken
import com.liang.module_base.R
import com.tencent.mmkv.MMKV

/**
 * description:
 *
 * @author Db_z
 * @Date 2021/10/8 16:43
 */
private const val THEME = "theme"
private const val NAV_BAR = "nav_bar"
private const val NAV_BAR_COLOR = "nav_bar_color"
private const val KEY_NIGHT_MODE = "key_night_mode"
private const val SAVE_USER = "save_user"
private const val KEY_SEARCH_HISTORY = "search_history"
private const val IS_CHINESE_MODE = "isChineseMode"

fun setAppThemeColor(theme: Int) {
    MMKV.defaultMMKV().putInt(THEME, theme)
}

fun getAppThemeColor() =
    MMKV.defaultMMKV()
        .getInt(THEME, ContextCompat.getColor(BaseApp.appContext, R.color.colorPrimary))

fun setNightMode(nightMode: Boolean) {
    MMKV.defaultMMKV().putBoolean(KEY_NIGHT_MODE, nightMode)
}

fun getNightMode() = MMKV.defaultMMKV().getBoolean(KEY_NIGHT_MODE, false)

/**
 * 中英文模式本地化设置
 * 默认中文模式
 */
fun getChineseMode() = MMKVUtils.getBoolean(IS_CHINESE_MODE, true)

fun setChineseMode(isChineseMode: Boolean) = MMKVUtils.put(IS_CHINESE_MODE, isChineseMode)

fun setNavBar(navBar: Boolean) {
    MMKV.defaultMMKV().putBoolean(NAV_BAR, navBar)
}

fun getNavBar() = MMKV.defaultMMKV().getBoolean(NAV_BAR, true)

fun setNavBarColor(isNavBarColor: Boolean) {
    MMKV.defaultMMKV().putBoolean(NAV_BAR_COLOR, isNavBarColor)
}

fun getNavBarColor() = MMKV.defaultMMKV().getBoolean(NAV_BAR_COLOR, false)

fun saveUser(userJson: String) {
    MMKV.defaultMMKV().putString(SAVE_USER, userJson)
}

fun getUser() = MMKV.defaultMMKV().getString(SAVE_USER, "")

/**
 * 是否登录，如果登录了就执行then，没有登录就直接跳转登录界面
 * @return true-已登录，false-未登录
 */
fun checkNeedLogin(then: (() -> Unit)? = null) =
    if (isLogin()) {
        then?.invoke()
        true
    } else {
//        startLoginActivity()
        false
    }

/**
 * 是否已登录
 */
fun isLogin() = !StringUtils.isEmpty(getUser()) && CookieClass.hasCookie()

fun saveSearchHistoryData(words: String) {
    if (StringUtils.isEmpty(words) || words == " ") return
    val history = getSearchHistoryData()
    if (history.contains(words)) {
        history.remove(words)
    }
    history.add(0, words)
    val listStr = GsonUtils.toJson(history)
    MMKV.defaultMMKV().putString(KEY_SEARCH_HISTORY, listStr)
}

fun getSearchHistoryData(): MutableList<String> {
    val listStr = MMKV.defaultMMKV().getString(KEY_SEARCH_HISTORY, "")
    return if (listStr.isNullOrEmpty()) {
        mutableListOf()
    } else {
        GsonUtils.fromJson(listStr, object : TypeToken<MutableList<String>>() {}.type)
    }
}

fun deleteSearchHistoryData(words: String) {
    val history = getSearchHistoryData()
    history.remove(words)
    val listStr = GsonUtils.toJson(history)
    MMKV.defaultMMKV().putString(KEY_SEARCH_HISTORY, listStr)
}