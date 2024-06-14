package com.liang.newbaseproject.bean

import android.content.Context
import com.liang.module_base.base.BaseApp
import com.liang.module_base.utils.LanguageUtilKt
import com.liang.newbaseproject.R
import com.liang.newbaseproject.baidumap.BaiduMapActivity
import com.liang.newbaseproject.citypicker.CityPickerDialogActivity
import com.liang.newbaseproject.citypicker.CityPickerViewPagerActivity
import com.liang.newbaseproject.normal.NormalViewModelActivity
import com.liang.newbaseproject.pickView.OptionPickerActivity
import com.liang.newbaseproject.spinner.SpinnerActivity

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
//            activity = PictureSelectorActivity::class.java
        )
    private val bean8 =
        FunItemBean(
            R.string.func_breathing_plate,
        )
    private val bean9 =
        FunItemBean(
            R.string.func_rating_bar,
        )

    private val bean10 =
        FunItemBean(
            R.string.func_SDK_aar,
        )

    private val bean11 =
        FunItemBean(
            R.string.func_custom_view,
        )

    private val bean12 =
        FunItemBean(
            R.string.func_music_service,
        )

    private val bean13 =
        FunItemBean(
            R.string.func_weather,
        )
    private val bean14 =
        FunItemBean(
            R.string.func_crash_test,
        )

    private val bean15 =
        FunItemBean(
            R.string.func_city_list,
            activity = CityPickerViewPagerActivity::class.java
        )

    private val bean16 =
        FunItemBean(
            R.string.func_city_list,
            activity = CityPickerDialogActivity::class.java
        )

    private val bean17 =
        FunItemBean(
            R.string.fun_option_picker,
            activity = OptionPickerActivity::class.java
        )

    private val bean18 =
        FunItemBean(
            R.string.fun_spinner,
            activity = SpinnerActivity::class.java
        )

    private val bean19 =
        FunItemBean(
            R.string.fun_baidu_map,
            activity = BaiduMapActivity::class.java
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
//            add(bean8)
            add(bean9)
            add(bean10)
            add(bean11)
            add(bean12)
            add(bean13)
            add(bean14)
            add(bean15)
            add(bean16)
            add(bean17)
            add(bean18)
            add(bean19)
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