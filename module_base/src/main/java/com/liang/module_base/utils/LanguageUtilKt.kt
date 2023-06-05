package com.liang.module_base.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import com.liang.module_base.extension.getChineseMode
import com.liang.module_base.extension.setChineseMode
import com.liang.module_base.utils.LogUtils.d
import java.util.Locale

/**
 * @Time: 2023/5/5/0005 on 16:19
 * @User: Jerry
 * @Description: 多语言切换工具类
 */
object LanguageUtilKt {

    private const val TAG = "LanguageUtil"
    const val ERROR_LABEL = ""
    private const val SPECIFIC_COUNTRY = "US"
    private const val SPECIFIC_LANGUAGE = "en"
    private const val CHINESE_COUNTRY = "CN"
    private const val CHINESE_LANGUAGE = "zh"

    private const val APPNAME = "com.example.coroutinedemo"

    private fun getLocale(): Locale {
        return if (isChineseVersion) {
            Locale.CHINA
        } else {
            Locale.ENGLISH
        }
    }

    val isChineseVersion: Boolean
        get() = getChineseMode()

    fun setChineseMode(context: Context, isChineseVersion: Boolean) {
        setChineseMode(isChineseVersion)
        d(TAG, "setIsChineseVersion: $isChineseVersion")
        changeLang(context)
    }

    private fun getStringByLocale(
        context: Context,
        stringId: Int,
        language: String,
        country: String
    ): String {
        val resources = getApplicationResource(
            context, context.applicationContext.packageManager,
            APPNAME, Locale(language, country)
        )
        return if (resources == null) {
            "ERROR_LABEL"
        } else {
            try {
                resources.getString(stringId)
            } catch (e: Exception) {
                ERROR_LABEL
            }
        }
    }

    private fun getStringsByLocale(
        context: Context,
        stringId: Int,
        language: String,
        country: String
    ): Array<String?> {
        val resources = getApplicationResource(
            context, context.applicationContext.packageManager,
            APPNAME, Locale(language, country)
        )
        return if (resources == null) {
            arrayOfNulls(0)
        } else {
            try {
                resources.getStringArray(stringId)
            } catch (e: Exception) {
                arrayOfNulls(0)
            }
        }
    }

    fun getCurrentString(context: Context, stringId: Int): String {
        return if (isChineseVersion) getStringToChinese(context, stringId) else getStringToEnglish(
            context,
            stringId
        )
    }

    fun getCurrentStrings(context: Context, stringId: Int): Array<String?> {
        return if (isChineseVersion) getStringsToChinese(
            context,
            stringId
        ) else getStringsToEnglish(context, stringId)
    }

    fun getStringToEnglish(context: Context, stringId: Int): String {
        return getStringByLocale(context, stringId, SPECIFIC_LANGUAGE, SPECIFIC_COUNTRY)
    }

    fun getStringToChinese(context: Context, stringId: Int): String {
        return getStringByLocale(context, stringId, CHINESE_LANGUAGE, CHINESE_COUNTRY)
    }

    fun getStringsToEnglish(context: Context, stringId: Int): Array<String?> {
        return getStringsByLocale(context, stringId, SPECIFIC_LANGUAGE, SPECIFIC_COUNTRY)
    }

    fun getStringsToChinese(context: Context, stringId: Int): Array<String?> {
        return getStringsByLocale(context, stringId, CHINESE_LANGUAGE, CHINESE_COUNTRY)
    }

    private fun getApplicationResource(
        context: Context,
        pm: PackageManager,
        pkgName: String,
        l: Locale
    ): Resources? {
        var resourceForApplication: Resources? = null
        try {
            resourceForApplication = pm.getResourcesForApplication(pkgName)
            updateResource(context, resourceForApplication, l)
        } catch (e: PackageManager.NameNotFoundException) {
        }
        return resourceForApplication
    }

    private fun updateResource(context: Context, resource: Resources, l: Locale) {
        val config = resource.configuration
        config.locale = l
        resource.updateConfiguration(config, null)
    }

    fun changeLang(context: Context?): Context {
        val res = context?.resources
        val config = res?.configuration
        val locale = getLocale()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config?.setLocale(locale)
            config?.setLocales(LocaleList(locale))
        } else {
            config?.locale = locale
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config?.setLayoutDirection(locale)
            ContextWrapper(context?.createConfigurationContext(config!!))
        } else {
            context?.resources?.updateConfiguration(config, res?.displayMetrics)
            ContextWrapper(context)
        }
    }

    val isChinese: Boolean
        get() = isChineseVersion

    fun getChineseStr(context: Context, id: Int): String {
        return getStringToChinese(context, id)
    }

    fun getEnglishStr(context: Context, id: Int): String {
        return getStringToEnglish(context, id)
    }

    fun getStrByLanguage(context: Context, id: Int): String {
        return if (isChinese) {
            getChineseStr(context, id)
        } else {
            getEnglishStr(context, id)
        }
    }
}