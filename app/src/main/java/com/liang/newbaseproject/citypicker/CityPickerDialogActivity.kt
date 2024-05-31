package com.liang.newbaseproject.citypicker

import android.os.Bundle
import com.liang.module_base.base.BaseActivity
import com.liang.newbaseproject.R
import com.liang.newbaseproject.databinding.ActivityCityPickerDialogBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CityPickerDialogActivity : BaseActivity<ActivityCityPickerDialogBinding>() {
    override fun getLayoutId(): Int = R.layout.activity_city_picker_dialog

    private val viewModel: CityViewModel by viewModel()

    override fun initView(savedInstanceState: Bundle?) {
        loadCityData()

        mBinding.apply {
            btnSelectCity.setOnClickListener {
                val dialogFragment = CityPickerDialogFragment.newInstance()
                if (!dialogFragment.isVisible) {
                    // 防止多次点击打开多个
                    dialogFragment.show(supportFragmentManager, "custom_dialog")
                }
            }
        }
    }

    private fun loadCityData() {
        // 初始设置不选中省份
        viewModel.setCheckProvinceInfo(null)
        // 获取省市数据
        viewModel.getProvinceData()
    }
}