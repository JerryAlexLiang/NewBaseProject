package com.liang.newbaseproject.citypicker

import android.content.Context
import android.view.View
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.liang.module_base.base.BaseFragment
import com.liang.module_base.utils.GsonUtils
import com.liang.module_base.utils.LogUtils
import com.liang.module_base.utils.ToastUtil
import com.liang.newbaseproject.R
import com.liang.newbaseproject.databinding.FragmentCityListBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class CityListFragment2 : BaseFragment<FragmentCityListBinding>() {

    private val cityTagAdapter by lazy {
        CityListAdapter()
    }

    companion object {
        @JvmStatic
        fun newInstance(): CityListFragment2 = CityListFragment2()
    }

    private val viewModel: CityViewModel by activityViewModel()

    override fun getLayoutId(): Int {
        return R.layout.fragment_city_list
    }

    override fun initView(view: View) {

        val flexboxLayoutManager = getFlexboxLayoutManager(context)

        mBinding.rvCity.apply {
            layoutManager = flexboxLayoutManager
            adapter = cityTagAdapter
        }

        cityTagAdapter.setOnMyItemClickListener {
            LogUtils.d(tag = "CityTagAdapter1", msg = "选中: ${it?.name}")
            ToastUtil.showShort(requireContext(),"选中: ${it?.name}")
            viewModel.checkedCity = it
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkedCity = null
    }

    private fun getFlexboxLayoutManager(context: Context?): FlexboxLayoutManager {
        //设置布局管理器
        val flexboxLayoutManager = FlexboxLayoutManager(context)
        //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal:
        // 主轴为水平方向，起点在左端。
        flexboxLayoutManager.flexDirection = FlexDirection.ROW
        //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列:
        // 按正常方向换行
        flexboxLayoutManager.flexWrap = FlexWrap.WRAP
        //justifyContent 属性定义了项目在主轴上的对齐方式:
        // 交叉轴的起点对齐
        flexboxLayoutManager.justifyContent = JustifyContent.FLEX_START
        return flexboxLayoutManager
    }

    override fun startObserver() {
        super.startObserver()

        viewModel.checkedProvinceLiveData.observe(this) {

            LogUtils.d(tag = "checkedProvinceCityList", msg = GsonUtils.toJson(it?.name ?: "***"))

            mBinding.tvCity.text = it?.name + "省"

            val cityList = it?.cityList

            viewModel.checkedCityList.clear()
            cityList?.let { it1 -> viewModel.checkedCityList.addAll(it1) }
            cityTagAdapter.submitList(cityList)
            // 每次重新进入城市列表时清空已选中数据
            cityTagAdapter.setDefaultSelectList(null)

            cityList?.forEach { city ->
                mBinding.tvCity.append(" " + city.name + " ")
            }
        }

//        mBinding.tvCity.setOnClickListener {
//            // 选中城市，切换ViewPager-ProvinceFragment，切换ViewPager-ProvinceFragment-更新父容器DialogFragment中的UI-setCheckProvinceUI
//            viewModel.setProvinceOrCityIsCheck(1)
//        }
    }

}