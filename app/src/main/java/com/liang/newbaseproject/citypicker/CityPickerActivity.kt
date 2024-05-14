package com.liang.newbaseproject.citypicker

import android.os.Bundle
import androidx.activity.viewModels
import com.liang.module_base.base.BaseActivity
import com.liang.module_base.utils.GsonUtils
import com.liang.module_base.utils.LogUtils
import com.liang.module_base.utils.ToastUtil
import com.liang.module_base.utils.setOnClickListener
import com.liang.newbaseproject.R
import com.liang.newbaseproject.databinding.ActivityCityPickerBinding

class CityPickerActivity : BaseActivity<ActivityCityPickerBinding>() {

    // 初始化ViewModel-使用Koin依赖注入的方法
    private val viewModel: CityViewModel by viewModels()
    override fun getLayoutId(): Int = R.layout.activity_city_picker

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.btnJump.setOnClickListener {
            CityPickerViewPagerActivity.actionStart(this@CityPickerActivity)
        }
    }

    override fun initData() {
        super.initData()
        loadCityData()
    }

    private fun loadCityData() {
        viewModel.getProvinceData()
    }

    override fun startObserver() {
        super.startObserver()
        viewModel.dataLiveData.observe(this) {
            if (it != null) {
                render(it)
            }
        }
    }

    private fun render(uiState: CityUiState) {
        when (uiState) {
            is CityUiState.Error -> onError(uiState)
            CityUiState.Loading -> onLoad()
            is CityUiState.Success -> onSuccess(uiState)
        }
    }

    private fun onSuccess(uiState: CityUiState.Success) {
        dismissLoading()
        val result = uiState.result
        val json = GsonUtils.toJson(result)
        LogUtils.d(tag = "CityJson", msg = json)
//        for (provinceInfo in result) {
////            // 汉字首字母转拼音
//////            val pinyin = PinyinUtilsKt.getPingYin(provinceInfo.name)
////            val pinyin = PinyinUtilsKt.getFirstSpell(provinceInfo.name)
////            val sortString = pinyin.substring(0, 1).uppercase(Locale.CHINA)
////
////            LogUtils.d(tag = "Pinyin1", msg = pinyin)
////            LogUtils.d(tag = "Pinyin2", msg = sortString)
////            // 正则表达式，判断首字母是否是英文字母
////            if (sortString.matches("[A-G]".toRegex())) {
////                viewModel.provinceList.add(provinceInfo)
////
////                viewModel.provinceList.add()
////                LogUtils.d(tag = "Pinyin3", msg = provinceInfo.name)
////            }
//
//            viewModel.provinceList.add(provinceInfo)
//            for (city in provinceInfo.cityList) {
//                viewModel.cityList.add(city)
//            }
//        }


        for ((index, provinceInfo) in result.withIndex()) {
            if (index < 8) {
                viewModel.provinceListAG.add(provinceInfo)
            } else if (index < 17) {
                viewModel.provinceListHK.add(provinceInfo)
            } else if (index < 26) {
                viewModel.provinceListLS.add(provinceInfo)
            } else {
                viewModel.provinceListTZ.add(provinceInfo)
            }

//            for (city in provinceInfo.cityList) {
//                viewModel.cityList.add(city)
//            }
        }

        mBinding.apply {

            for (provinceInfo in viewModel.provinceListAG) {
                tvProvinceAG.append(provinceInfo.name + " ")
            }
            for (provinceInfo in viewModel.provinceListHK) {
                tvProvinceHK.append(provinceInfo.name + " ")
            }
            for (provinceInfo in viewModel.provinceListLS) {
                tvProvinceLS.append(provinceInfo.name + " ")
            }
            for (provinceInfo in viewModel.provinceListTZ) {
                tvProvinceTZ.append(provinceInfo.name + " ")
            }
//            for (city in viewModel.cityList) {
//                tvCity.append(city.name + " ")
//            }
            setOnClickListener(tvProvinceAG, tvProvinceHK, tvProvinceLS, tvProvinceTZ) {
                tvCity.text = ""
                when (this) {
                    tvProvinceAG -> {
                        for (provinceInfo in viewModel.provinceListAG) {
                            for (city in provinceInfo.cityList) {
                                viewModel.cityList.clear()
                                viewModel.cityList.add(city)
                                LogUtils.d(tag = "City", msg = city.name)
                                tvCity.append(city.name + " ")
                            }
                        }
                    }

                    tvProvinceHK -> {
                        for (provinceInfo in viewModel.provinceListHK) {
                            for (city in provinceInfo.cityList) {
//                                viewModel.cityList.clear()
//                                viewModel.cityList.add(city)
                                LogUtils.d(tag = "City", msg = city.name)
                                tvCity.append(city.name + " ")
                            }
                        }
                    }

                    tvProvinceLS -> {
                        for (provinceInfo in viewModel.provinceListLS) {
                            for (city in provinceInfo.cityList) {
//                                viewModel.cityList.clear()
//                                viewModel.cityList.add(city)
                                LogUtils.d(tag = "City", msg = city.name)
                                tvCity.append(city.name + " ")
                            }
                        }
                    }

                    tvProvinceTZ -> {
                        for (provinceInfo in viewModel.provinceListTZ) {
                            for (city in provinceInfo.cityList) {
//                                viewModel.cityList.clear()
//                                viewModel.cityList.add(city)
                                LogUtils.d(tag = "City", msg = city.name)
                                tvCity.append(city.name + " ")
                            }
                        }
                    }
                }

            }
        }
    }

    private fun onLoad() {
        showLoading()
    }

    private fun onError(uiState: CityUiState.Error) {
        dismissLoading()
        LogUtils.e("$uiState.message")
        ToastUtil.showFailRectangleToast(this, true, uiState.message)
    }
}