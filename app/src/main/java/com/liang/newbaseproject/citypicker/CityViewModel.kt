package com.liang.newbaseproject.citypicker

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.liang.module_base.base.BaseViewModel
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class CityViewModel(application: Application) : BaseViewModel(application) {

    // 省级列表
    var provinceList = ArrayList<ProvinceInfo>()
    var provinceListAG = ArrayList<ProvinceInfo>()
    var provinceListHK = ArrayList<ProvinceInfo>()
    var provinceListLS = ArrayList<ProvinceInfo>()
    var provinceListTZ = ArrayList<ProvinceInfo>()

    // 市级列表
    var cityList = ArrayList<City>()

    // 区级列表
    var areaList = ArrayList<Area>()

    // 选中省份
    var checkedProvince: ProvinceInfo? = null

    // 选中省份传递来的城市列表
    var checkedCityList = ArrayList<City>()

    // 选中城市
    var checkedCity: City? = null

    // 省市切换tag 1 省选中 2 城市选中
    private val _provinceOrCityIsCheck: MutableLiveData<Int> = MutableLiveData()
    val provinceOrCityIsCheck: LiveData<Int>
        get() = _provinceOrCityIsCheck

    fun setProvinceOrCityIsCheck(flag: Int) {
        // 省市切换tag 1 省选中 2 城市选中
        _provinceOrCityIsCheck.value = flag
    }

    // 省市JSON数据解析
    private val _dataLiveData: MutableLiveData<CityUiState> = MutableLiveData()
    val dataLiveData: LiveData<CityUiState>
        get() = _dataLiveData

    // 选中省份
    private val _checkedProvinceLiveData: MutableLiveData<ProvinceInfo?> = MutableLiveData()
    val checkedProvinceLiveData: LiveData<ProvinceInfo?>
        get() = _checkedProvinceLiveData

    fun setCheckProvinceInfo(provinceInfo: ProvinceInfo?) {
        _checkedProvinceLiveData.value = provinceInfo
    }

    fun getProvinceData() {
        // 获取assets目录下的json文件数据
        val jsonData = getJson(getApplication(), "city.json")
        _dataLiveData.value = CityUiState.Success(
                Gson().fromJson(
                        jsonData,
                        object : TypeToken<ArrayList<ProvinceInfo>>() {}.type
                )
        )
    }

    private fun getJson(context: Context, fileName: String?): String {
        val stringBuilder = StringBuilder()
        try {
            val assetManager = context.assets
            val bf = BufferedReader(
                    InputStreamReader(
                            assetManager.open(fileName!!)
                    )
            )
            var line: String?
            while (bf.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }


}