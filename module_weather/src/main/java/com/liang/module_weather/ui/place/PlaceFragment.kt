package com.liang.module_weather.ui.place

import android.annotation.SuppressLint
import android.text.Editable
import android.view.View
import androidx.core.view.isVisible
import com.liang.module_base.base.BaseFragment
import com.liang.module_base.extension.invisible
import com.liang.module_base.extension.setLinearLayoutManager
import com.liang.module_base.extension.visible
import com.liang.module_base.utils.GsonUtils
import com.liang.module_base.utils.LogUtils
import com.liang.module_base.utils.ToastUtil
import com.liang.module_weather.R
import com.liang.module_weather.databinding.FragmentPlaceBinding
import com.liang.module_weather.logic.model.Place
import com.liang.module_weather.ui.weather.WeatherActivity
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
    private val viewModel: PlaceViewModel by viewModel()

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

    private fun initStatus() {
        if (activity is PlaceActivity && viewModel.isPlaceSaved()) {
            mBinding.baseActionbar.baseActionbarLeftIcon.visibility = View.VISIBLE

            val savedPlace = viewModel.getSavedPlace()
            context?.let {
                WeatherActivity.actionStart(
                    it,
                    savedPlace.name,
                    savedPlace.location.lng,
                    savedPlace.location.lat,
                )
            }
            activity?.finish()
            return
        } else if (activity is WeatherActivity) {
            mBinding.baseActionbar.baseActionbarLeftIcon.visibility = View.GONE
            mBinding.baseActionbar.editSearchView.apply {
                if (text?.isEmpty() == true){
                    hint = (activity as WeatherActivity).getCurrentCity()
                    LogUtils.d(tag="XXX", msg = hint.toString())
                    viewModel.searchPlace(hint.toString())
                }
            }
        }
    }

    private fun initListener() {
        placeAdapter.setOnItemClickListener { bean ->
            context?.let { ToastUtil.showShort(it, bean?.name ?: "") }

            if (activity is WeatherActivity) {
                // 如果当前是WeatherActivity，则不需要再次跳转，关闭DrawerLayout,并请求新数据并刷新界面UI
                bean?.let { (activity as WeatherActivity).currentActivityRefresh(it) }
            } else {
                context?.let {
                    WeatherActivity.actionStart(
                        it,
                        bean?.name ?: "",
                        bean?.location?.lng ?: "",
                        bean?.location?.lat ?: ""
                    )
                }
                activity?.finish()
            }

            // 存储城市名
            bean?.let { viewModel.savePlace(it) }
        }
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

//                if (viewModel.editText.isNotEmpty()){
//                    editSearchView.hint = viewModel.editText
//                    viewModel.searchPlace(viewModel.editText)
//                }

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

        initStatus()

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