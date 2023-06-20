package com.liang.newbaseproject.bean

import android.content.Context
import com.liang.module_base.base.BaseApp
import com.liang.module_base.utils.LanguageUtilKt
import com.liang.newbaseproject.R
import com.liang.newbaseproject.normal.NormalViewModelActivity
import com.liang.newbaseproject.pictureSelector.PictureSelectorActivity

object MockMainFunBeanList {

    private val bean1 =
        FunItemBean(
            R.string.func_retrofit_common_view_model,
            activity = NormalViewModelActivity::class.java
        )
    private val bean2 =
        FunItemBean(
            R.string.func_retrofit_koin,
//            activity = EventLiveDataActivity::class.java
        )
    private val bean3 =
        FunItemBean(R.string.func_coroutine)
    private val bean4 =
        FunItemBean(R.string.func_day_night)
    private val bean5 =
        FunItemBean(R.string.func_change_language)
    private val bean6 =
        FunItemBean(R.string.func_typewriter)
    private val bean7 =
        FunItemBean(
            R.string.func_choose_image,
            activity = PictureSelectorActivity::class.java
        )
    private val bean8 =
        FunItemBean(
            R.string.func_breathing_plate,
        )

    private var funBeanList: MutableList<FunItemBean> = mutableListOf()

    fun initMainFunData(): MutableList<FunItemBean> {
        funBeanList.clear()
        funBeanList.run {
            add(bean1)
            add(bean2)
            add(bean3)
            add(bean4)
            add(bean5)
            add(bean6)
            add(bean7)
            add(bean8)
        }
        return funBeanList
    }

    private fun getEnglishStr(id: Int): String {
        return LanguageUtilKt.getStringToEnglish(BaseApp.application, id)
    }

    private fun getChineseStr(id: Int): String {
        return LanguageUtilKt.getStringToChinese(BaseApp.application, id)
    }

    private fun getEnglishStrArrays(id: Int): Array<String?> {
        return LanguageUtilKt.getStringsToEnglish(BaseApp.application, id)
    }

    private fun getChineseStrArrays(id: Int): Array<String?> {
        return LanguageUtilKt.getStringsToChinese(BaseApp.application, id)
    }

    private fun getString(resId: Int): String {
        return LanguageUtilKt.getCurrentString(BaseApp.application, resId)
    }

    private fun getString(resId: Int, isChinese: Boolean): String {
        return if (isChinese) getChineseStr(resId) else getEnglishStr(resId)
    }

    private fun getStrLocale(id: Int): String {
        val context: Context = BaseApp.application
        return if (LanguageUtilKt.isChineseVersion) {
            LanguageUtilKt.getStringToChinese(context, id)
        } else {
            LanguageUtilKt.getStringToEnglish(context, id)
        }
    }
}