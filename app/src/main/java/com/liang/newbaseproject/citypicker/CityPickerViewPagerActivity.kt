package com.liang.newbaseproject.citypicker

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.liang.module_base.base.BaseActivity
import com.liang.module_base.utils.DateUtilJava
import com.liang.module_base.utils.GsonUtils
import com.liang.module_base.utils.LogUtils
import com.liang.module_base.utils.ToastUtil
import com.liang.module_base.utils.setOnClickListener
import com.liang.newbaseproject.R
import com.liang.newbaseproject.databinding.ActivityCityPickerViewPagerBinding


/**
 * - Time: 2024/05/07 14:21
 * - User: Jerry
 * - Description: ViewPager2和TabLayout协同使用，实现多Fragment页面切换类似于QQ音乐，bilibili效果
 * - https://blog.csdn.net/m0_72983118/article/details/134407403
 */
class CityPickerViewPagerActivity : BaseActivity<ActivityCityPickerViewPagerBinding>() {

    private val stringList: ArrayList<String> = ArrayList()
    private val fragmentList: ArrayList<Fragment> = ArrayList()

    // 初始化ViewModel-使用Koin依赖注入的方法
    private val viewModel: CityViewModel by viewModels()

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, CityPickerViewPagerActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_city_picker_view_pager
    }

    private fun setTabLayoutCanClick(provinceInfo: ProvinceInfo?) {
        if (provinceInfo == null) {
            setTabLayoutCanClick(false)
            ToastUtil.showShort(this, "请选择省份")
        } else {
            setTabLayoutCanClick(true)
        }
    }

    override fun initListener() {
        super.initListener()
        mBinding.apply {
            setOnClickListener(rbProvince, rbCity, btnOk) {
                when (this) {
                    rbProvince -> {
                        rbProvince.isChecked = true
                        rbCity.isChecked = false
//                        rbProvince.height = ScreenUtil.dp2px(this@CityPickerViewPagerActivity, 80f)
//                        rbCity.height = ScreenUtil.dp2px(this@CityPickerViewPagerActivity, 60f)
                        viewPager.currentItem = 0
                    }

                    rbCity -> {
                        viewModel.checkedProvinceLiveData.observe(this@CityPickerViewPagerActivity) {
                            LogUtils.d(tag = "checkedProvince", msg = GsonUtils.toJson(it))

                            if (it == null) {
                                rbProvince.isChecked = true
                                rbCity.isChecked = false
//                                rbProvince.height =
//                                    ScreenUtil.dp2px(this@CityPickerViewPagerActivity, 80f)
//                                rbCity.height =
//                                    ScreenUtil.dp2px(this@CityPickerViewPagerActivity, 60f)
                                ToastUtil.showShort(this@CityPickerViewPagerActivity, "请选择省份")
                            } else {
                                rbProvince.isChecked = false
                                rbCity.isChecked = true
//                                rbProvince.height =
//                                    ScreenUtil.dp2px(this@CityPickerViewPagerActivity, 60f)
//                                rbCity.height =
//                                    ScreenUtil.dp2px(this@CityPickerViewPagerActivity, 80f)
                                viewPager.currentItem = 1
                            }
                        }
                    }

                    btnOk -> {
                        val checkedCity = viewModel.checkedCity
                        if (checkedCity == null) {
                            ToastUtil.showFailRectangleToast(
                                    this@CityPickerViewPagerActivity,
                                    true,
                                    "请选择城市"
                            )
                        } else {
                            LogUtils.d(tag = "CityTagAdapter3", msg = "选中: ${checkedCity.name}")
                            ToastUtil.showSuccessRectangleToast(
                                    this@CityPickerViewPagerActivity, true,
                                    checkedCity.name
                            )
                        }
                    }
                }
            }
        }
    }

    fun setCheckProvinceUI() {
        mBinding.apply {
            rbProvince.isChecked = true
            rbCity.isChecked = false
//            rbProvince.height = ScreenUtil.dp2px(this@CityPickerViewPagerActivity, 80f)
//            rbCity.height = ScreenUtil.dp2px(this@CityPickerViewPagerActivity, 60f)


//            val lpProvince = rlProvince.layoutParams
//            lpProvince.height = ScreenUtil.dp2px(this@CityPickerViewPagerActivity, 80f)
//            rlProvince.layoutParams = lpProvince
//
//
//            val lpCity = rlCity.layoutParams
//            lpCity.height = ScreenUtil.dp2px(this@CityPickerViewPagerActivity, 60f)
//            rlCity.layoutParams = lpCity
//            rlCity.gravity = Gravity.BOTTOM


        }
    }

    fun setCheckTabUI() {
        mBinding.apply {
            rbProvince.isChecked = false
            rbCity.isChecked = true

//            rbProvince.height = ScreenUtil.dp2px(this@CityPickerViewPagerActivity, 60f)
//            rbCity.height = ScreenUtil.dp2px(this@CityPickerViewPagerActivity, 80f)

//            val lpProvince = rlProvince.layoutParams
//            lpProvince.height = ScreenUtil.dp2px(this@CityPickerViewPagerActivity, 60f)
//            rlProvince.layoutParams = lpProvince
//            rlProvince.gravity = Gravity.BOTTOM
//
//            val lpCity = rlCity.layoutParams
//            lpCity.height = ScreenUtil.dp2px(this@CityPickerViewPagerActivity, 80f)
//            rlCity.layoutParams = lpCity


        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        mBinding.apply {
//            val weekByData = DateUtilJava.getWeekByData("2024-5-14")
//            text = "$weekByData 2024-5-14"

            val theYearMonthAndDayMiddleLine = DateUtilJava.getTheYearMonthAndDayMiddleLine()
            val theMonthAndDayMiddleLine = DateUtilJava.getTheMonthAndDayMiddleLine()

            // Today星期
            val todayOfWeek = DateUtilJava.getTodayOfWeek()

            // 日期转星期
            val weekByData = DateUtilJava.getWeek2(theYearMonthAndDayMiddleLine)


            tvTime.text = theYearMonthAndDayMiddleLine + " " + todayOfWeek + " " + theMonthAndDayMiddleLine + " " + weekByData

            // 前几天 如获取前7天日期则传-7即可；如果后7天则传7
            val futureDate = DateUtilJava.getOldDate(7)
            val week2 = DateUtilJava.getWeek(futureDate)
            val weekByData2 = DateUtilJava.getWeek2(futureDate)

            tvTimeEnd.text = futureDate + " " + week2 + " " + weekByData2

            val currentYear = DateUtilJava.getCurrentYear()
            val futureDateMontyDay = DateUtilJava.getOldDateMontyDay(7)
            tvTimeEnd2.text = "$currentYear-$futureDateMontyDay"
        }


//        mBinding.rgTabLayout.minimumHeight = ScreenUtil.dp2px(this@CityPickerViewPagerActivity, 80f)

        setCheckProvinceUI()

        mBinding.rbProvince.isChecked = true
        mBinding.rbCity.isChecked = false

        loadCityData()

        initViewPager()

//        viewModel.checkedProvinceLiveData.observe(this) {
//            LogUtils.d(tag = "checkedProvince", msg = GsonUtils.toJson(it))
//
//            setTabLayoutCanClick(it)
//
//        }
    }

    private fun loadCityData() {
        // 初始设置不选中省份
        viewModel.setCheckProvinceInfo(null)
        // 获取省市数据
        viewModel.getProvinceData()
    }

    private fun initViewPager() {
        setData()

        // 禁用预加载
//        mBinding.viewPager.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        val fragmentViewPagerAdapter = FragmentViewPagerAdapter(this)
        mBinding.viewPager.adapter = fragmentViewPagerAdapter

        // TabLayout同步Fragment
        // 一定要使用.attach()进行启动
        TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager) { tab, position ->
            val tabView: TextView = TextView(this)
            tabView.text = stringList[position]
            // 将TabView绑定到TabLayout上
            tab.setCustomView(tabView)
        }.attach()

        // false表示禁止，true表示允许
        mBinding.viewPager.setUserInputEnabled(false);

        // TabLayout的监听事件
        mBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                //未选中的变化
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
                //重复选中的变化，当用户再次点击已经选中的Tab时，这个方法就会被调用。
            }
        })

        // Viewpager2的滑动监听事件
        mBinding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            // 该方法开始滑动到滑动结束前不断调用
            // 参数一：position表示当前页面的位置（当前页面在翻页适配器中的下标，索引），因为不断调用，position也可能表示目标页面的下标
            // 参数二：positionOffset表示页面偏移的百分比，翻页时不断增大（不断趋近1），最后翻页完成时突变成0
            // 这个参数在左滑时由1趋近0，右滑由0趋近1
            // 参数三：positionOffsetPixel表示页面完成滑动的像素数，变化趋势和positionOffset一样
            override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int,
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            // 该方法在页面切换成功后调用（滑动前与滑动后不是同一页面）
            // position表示当前页面在适配器中的下标，索引
            // 用于回调ViewPager2的当前页面，当页面发送变化就会调用这个方法onPageSelected

            // 使用动态方法实现字体大小颜色变化
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                //获取总的TabLayout的个数
                val tabCount: Int = mBinding.tabLayout.tabCount

                //遍历选择所有的Tab,如果判断是当前的Tab则进行相关操作，不是则还原操作
                for (i in 0 until tabCount) {
                    val tab: TabLayout.Tab? = mBinding.tabLayout.getTabAt(i) //取得tab
                    //通过tab获取tabView
                    val tabView = tab?.customView as TextView?
                    if (tab!!.position == position) {
                        tabView!!.textSize = 30f
                        tabView.setTypeface(Typeface.DEFAULT_BOLD)
                        tabView.setTextColor(Color.parseColor("#B22222"))
                    } else {
                        tabView!!.textSize = 20f
                        tabView.setTypeface(Typeface.MONOSPACE)
                        tabView.setTextColor(Color.parseColor("#000000"))
                    }
                }
            }

            // 该方法在页面滑动状态改变时调用
            // ViewPager2.SCROLL_STATE_IDLE表示空闲状态，当前页面处于静止状态，没有要翻页的趋势 == 0
            // ViewPager2.SCROLL_STATE_DRAGGING表示拖动状态，用户正在拖动页面，准备进行翻页滚动 == 1
            // ViewPager2.SCROLL_STATE_SETTLING表示滚动状态，页面正在自动滚动到目标页面 == 2
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })
    }

    private fun setData() {
        stringList.add("省份")
        stringList.add("城市")

        fragmentList.add(ProvinceListFragment.newInstance())
        fragmentList.add(CityListFragment.newInstance())
    }

    private fun setTabLayoutCanClick(canClick: Boolean) {
        val tabStrip = mBinding.tabLayout.getChildAt(0) as LinearLayout
        for (i in 0 until tabStrip.childCount) {
            val tabView = tabStrip.getChildAt(i)
            if (tabView != null) {
                tabView.isClickable = canClick
            }
        }
    }
}