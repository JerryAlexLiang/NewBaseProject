package com.liang.newbaseproject.citypicker

import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.liang.module_base.base.BaseFragment
import com.liang.module_base.utils.GsonUtils
import com.liang.module_base.utils.LogUtils
import com.liang.module_base.utils.ToastUtil
import com.liang.newbaseproject.R
import com.liang.newbaseproject.databinding.FragmentProvinceListBinding


class ProvinceListFragment : BaseFragment<FragmentProvinceListBinding>() {

    companion object {
        @JvmStatic
        fun newInstance(): ProvinceListFragment = ProvinceListFragment()
    }

    private val viewModel: CityViewModel by lazy {
        ViewModelProvider(requireActivity())[CityViewModel::class.java]
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_province_list
    }

    override fun initView(view: View) {

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
        LogUtils.d(tag = "CityJson2", msg = json)

        for ((index, provinceInfo) in result.withIndex()) {
            if (index < 8) {
                viewModel.provinceListAG.add(provinceInfo)
                mBinding.tvProvince.append(" " + provinceInfo.name + " ")

                setProvinceUI(mBinding.rgAG, viewModel.provinceListAG)

            } else if (index < 17) {
                viewModel.provinceListHK.add(provinceInfo)
                setProvinceUI(mBinding.rgHK, viewModel.provinceListHK)
            } else if (index < 26) {
                viewModel.provinceListLS.add(provinceInfo)
                setProvinceUI(mBinding.rgLS, viewModel.provinceListLS)
            } else {
                viewModel.provinceListTZ.add(provinceInfo)
                setProvinceUI(mBinding.rgTZ, viewModel.provinceListTZ)
            }
        }

//        //清空父布局中的子布局
//        mBinding.rgAG.removeAllViews()
//        for (provinceInfo in viewModel.provinceListAG) {
//            val radioButton = RadioButton(context)
//            radioButton.isChecked = false
//
//            // 设置id
//            radioButton.id = viewModel.provinceListAG.indexOf(provinceInfo)
//
//            // 设置RadioButton的文本字体颜色
////            radioButton.setTextColor(ContextCompat.getColor(requireContext(),R.color.selector_radio_button_text))  // 无效
//            // 获取颜色资源使用getResources().getColorStateList(），而不是getResources().getColor(）
//            radioButton.setTextColor(
//                ContextCompat.getColorStateList(
//                    requireContext(),
//                    R.color.selector_radio_button_text
//                )
//            )
//            //设置按钮的样式
//            radioButton.buttonDrawable = null
//            // 去除点击水波纹效果
//            radioButton.setBackgroundColor(
//                ContextCompat.getColor(
//                    requireContext(),
//                    com.liang.module_ui.R.color.transparent
//                )
//            )
//            // 设置文字距离按钮四周的距离
//            radioButton.setPadding(15, 0, 15, 0)
//            radioButton.textSize = 26f
//            radioButton.text = provinceInfo.name
//
//            //设置RadioButton间距margin
//            val params = RadioGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//            params.setMargins(10, 0, 10, 0)
//
////            // 设置默认值
////            if (radioButton.id == 1) {
////                radioButton.isChecked = true
////            }
//
//            radioButton.setOnClickListener {
//                ToastUtil.showShort(requireContext(), "选中: ${provinceInfo.name}")
//                // 设置选中省份
//                viewModel.setCheckProvinceInfo(provinceInfo)
//
////                // 不使用LiveData的话不符合需求
////                viewModel.checkedProvince = provinceInfo
//
//                // 切换ViewPager-CityFragment
//                (activity as CityPickerViewPagerActivity).mBinding.viewPager.currentItem = 1
//                // 更新UI
//                (activity as CityPickerViewPagerActivity).setCheckCityUI()
//            }
//
//            mBinding.rgAG.addView(radioButton, params)
//        }
//
////        // 设置默认值
////        mBinding.rgAG.check(3)
    }

    private fun setProvinceUI(
        rgContainer: RadioGroup,
        provinceListAG: ArrayList<ProvinceInfo>,
    ) {
        //清空父布局中的子布局
        rgContainer.removeAllViews()
        for (provinceInfo in provinceListAG) {
            val radioButton = RadioButton(context)
            radioButton.isChecked = false

            // 设置id
            radioButton.id = provinceListAG.indexOf(provinceInfo)

            // 设置RadioButton的文本字体颜色
//            radioButton.setTextColor(ContextCompat.getColor(requireContext(),R.color.selector_radio_button_text))  // 无效
            // 获取颜色资源使用getResources().getColorStateList(），而不是getResources().getColor(）
            radioButton.setTextColor(
                ContextCompat.getColorStateList(
                    requireContext(),
                    R.color.selector_radio_button_text
                )
            )
            //设置按钮的样式
            radioButton.buttonDrawable = null
            // 去除点击水波纹效果
            radioButton.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    com.liang.module_ui.R.color.transparent
                )
            )
            // 设置文字距离按钮四周的距离
            radioButton.setPadding(15, 0, 15, 0)
            radioButton.textSize = 26f
            radioButton.text = provinceInfo.name

            //设置RadioButton间距margin
            val params = RadioGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(10, 0, 10, 0)

//            // 设置默认值
//            if (radioButton.id == 1) {
//                radioButton.isChecked = true
//            }

            mBinding.apply {
                radioButton.setOnClickListener {
                    ToastUtil.showShort(requireContext(), "选中: ${provinceInfo.name}")

                    // 选中当前RadioGroup中的RadioButton时，清除其他RadioGroup中的选中项
                    when (rgContainer) {
                        rgAG -> {
                            mBinding.rgHK.clearCheck()
                            mBinding.rgLS.clearCheck()
                            mBinding.rgTZ.clearCheck()
                        }

                        rgHK -> {
                            mBinding.rgAG.clearCheck()
                            mBinding.rgLS.clearCheck()
                            mBinding.rgTZ.clearCheck()
                        }

                        rgLS -> {
                            mBinding.rgAG.clearCheck()
                            mBinding.rgHK.clearCheck()
                            mBinding.rgTZ.clearCheck()
                        }

                        rgTZ -> {
                            mBinding.rgAG.clearCheck()
                            mBinding.rgHK.clearCheck()
                            mBinding.rgLS.clearCheck()
                        }
                    }


                    // 设置选中省份
                    viewModel.setCheckProvinceInfo(provinceInfo)

//                // 不使用LiveData的话不符合需求
//                viewModel.checkedProvince = provinceInfo

                    // 切换ViewPager-CityFragment
                    (activity as CityPickerViewPagerActivity).mBinding.viewPager.currentItem = 1
                    // 更新UI
                    (activity as CityPickerViewPagerActivity).setCheckTabUI()
                }
            }


            rgContainer.addView(radioButton, params)
        }

//        // 设置默认值
//        rgContainer.check(3)
    }


    private fun onLoad() {
        showLoading()
    }

    private fun onError(uiState: CityUiState.Error) {
        dismissLoading()
        LogUtils.e("$uiState.message")
        context?.let { ToastUtil.showFailRectangleToast(it, true, uiState.message) }
    }


}