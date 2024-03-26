package com.liang.module_weather.ui.place

import android.view.View
import androidx.lifecycle.Observer
import com.liang.module_base.base.BaseFragment
import com.liang.module_base.extension.visible
import com.liang.module_base.utils.GsonUtils
import com.liang.module_base.utils.LogUtils
import com.liang.module_base.utils.ToastUtil
import com.liang.module_weather.R
import com.liang.module_weather.databinding.FragmentPlaceBinding
import com.liang.module_weather.logic.model.Place
import com.liang.module_weather.ui.PlaceUiState
import com.liang.module_weather.ui.WeatherViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaceFragment : BaseFragment<FragmentPlaceBinding>() {

    companion object {
        /**
         * 使用此工厂方法使用提供的参数创建此片段的新实例。
         * @param : param1参数1。
         * @param : param2参数2。
         * 一个新的片段PlaceFragment实例
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) {

        }
    }

    // 初始化ViewModel-使用Koin依赖注入的方法
    private val viewModel: WeatherViewModel by viewModel()

    override fun getLayoutId(): Int {
        return R.layout.fragment_place
    }

    override fun initView(view: View) {
        initTitleBar()
    }

    private fun initTitleBar() {
        mBinding.apply {
            baseActionbar.apply {
                baseActionbarLeftIcon.apply {
                    visible()
                    setOnClickListener {
                        activity?.finish()
                    }
                }

                baseActionbarLeftTv.visible()

                editSearchView.apply {
                    visible()
//                    setOnSearchTextChangedListener {
//                        val content = it.toString()
//                        if (content.isNotEmpty()) {
//                            viewModel.searchPlace(content)
////                            ToastUtil.showShort(context, "搜索$content")
//                        } else {
//                            rvCityPlaces.visibility
//                            viewModel.placeList.clear()
//                        }
//                    }

                    setOnSearchClickListener { _ ->
                        val content = this.getText().toString().trim()
                        LogUtils.d("=========> $content")
                        if (content.isNotEmpty()) {
                            viewModel.searchPlace(content)
                        } else {
                            rvCityPlaces.visibility
                            viewModel.placeList.clear()
                        }
                    }
                }
            }
        }
    }

    override fun startObserver() {
        super.startObserver()
        //viewModel.placeLiveData.observe(viewLifecycleOwner) { result ->
        //            val places = result.getOrNull()
        //            if (places != null) {
        //                rvCityPlaces.visibility = View.VISIBLE
        //                viewModel.placeList.clear()
        //                viewModel.placeList.addAll(places)
        //                placeAdapter.notifyDataSetChanged()
        //                ToastUtil.onShowTrueToast(context, "查找到${places.size}条数据")
        //                LogUtil.d(TAG, viewModel.placeList.toString())
        //            } else {
        //                ToastUtil.onShowErrorToast(context, "未能查询到任何地点")
        //            }
        //        }

        viewModel.placeLiveData.observe(this, Observer {
            if (it != null) {
                render(it)
            }

        })
    }

    private fun render(uiState: PlaceUiState) {
        when (uiState) {
            is PlaceUiState.Error -> onError(uiState)
            PlaceUiState.Loading -> onLoad()
            is PlaceUiState.Success -> onSuccess(uiState)
        }
    }

    private fun onSuccess(uiState: PlaceUiState.Success) {
        dismissLoading()
        val places: List<Place> = uiState.result.places
        LogUtils.d("${GsonUtils.toJson(places)}")
    }

    private fun onLoad() {
        showLoading()
    }

    private fun onError(uiState: PlaceUiState.Error) {
        dismissLoading()
        context?.let { ToastUtil.showFailToast(it, true, "${uiState.message}") }
        LogUtils.e("$uiState.message")

    }
}