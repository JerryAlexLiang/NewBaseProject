package com.liang.module_weather.ui.place

import android.annotation.SuppressLint
import android.view.View
import androidx.core.view.isVisible
import com.liang.module_base.base.BaseFragment
import com.liang.module_base.extension.setLinearLayoutManager
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

/**
 * - Time: 2024/3/27/0027 9:15
 * - User: Jerry
 * - Description: 城市搜索列表
 */
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

    // 城市列表适配器
    private val placeAdapter by lazy {
        PlaceAdapter(viewModel.placeList)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_place
    }

    override fun initView(view: View) {
        initTitleBar()
        initAdapter()
        initListener()
    }

    private fun initListener() {
        placeAdapter.setOnItemClickListener(object : PlaceAdapter.OnItemClickListener {
            override fun onItemClick(bean: Place?) {
                context?.let { ToastUtil.showShort(it, bean?.name ?: "") }
            }
        })
    }

    private fun initAdapter() {
        mBinding.rvCityPlaces.apply {
            setLinearLayoutManager(placeAdapter)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
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
//                        LogUtils.d("=========> $content")
//                        if (content.isNotEmpty()) {
//                            viewModel.searchPlace(content)
////                            ToastUtil.showShort(context, "搜索$content")
//                        } else {
//                            rvCityPlaces.visibility = View.INVISIBLE
//                            viewModel.placeList.clear()
//                            placeAdapter.notifyDataSetChanged()
//                        }
//                    }

                    setOnSearchClickListener { _ ->
                        viewModel.placeList.clear()
                        val content = this.text.toString().trim()
                        LogUtils.d("=========> $content")
                        if (content.isNotEmpty()) {
                            viewModel.searchPlace(content)
                        } else {
                            rvCityPlaces.visibility
                            viewModel.placeList.clear()
                            placeAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }

    override fun startObserver() {
        super.startObserver()
        viewModel.placeLiveData.observe(this) {
            if (it != null) {
                render(it)
            }
        }
    }

    private fun render(uiState: PlaceUiState) {
        when (uiState) {
            is PlaceUiState.Error -> onError(uiState)
            is PlaceUiState.Loading -> onLoad()
            is PlaceUiState.Success -> onSuccess(uiState)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onSuccess(uiState: PlaceUiState.Success) {
        dismissLoading()
        val places = uiState.result?.places
        LogUtils.d(GsonUtils.toJson(places))

        if (places != null) {
            mBinding.apply {
                rvCityPlaces.isVisible
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                placeAdapter.notifyDataSetChanged()

                LogUtils.d(
                    "size: ${placeAdapter.itemCount} data: ${GsonUtils.toJson(viewModel.placeList)}",
                    "KKK"
                )
            }
        } else {
            context?.let { ToastUtil.showFailRectangleToast(it, true, "未能查询到任何地点") }
        }
    }

    private fun onLoad() {
        showLoading()
    }

    private fun onError(uiState: PlaceUiState.Error) {
        dismissLoading()
        context?.let { ToastUtil.showFailToast(it, true, uiState.message) }
        LogUtils.e("$uiState.message")

    }
}