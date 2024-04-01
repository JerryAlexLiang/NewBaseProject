package com.liang.module_weather.ui.weather

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import com.liang.module_base.base.BaseActivity
import com.liang.module_base.extension.visible
import com.liang.module_base.utils.GsonUtils
import com.liang.module_base.utils.LogUtils
import com.liang.module_base.utils.ToastUtil
import com.liang.module_weather.R
import com.liang.module_weather.WeatherConstant
import com.liang.module_weather.databinding.ActivityWeatherBinding
import com.liang.module_weather.logic.model.Place
import com.liang.module_weather.logic.model.WeatherBean
import com.liang.module_weather.logic.model.getSky
import com.scwang.smart.refresh.header.FalsifyHeader
import com.scwang.smart.refresh.header.MaterialHeader
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale

class WeatherActivity : BaseActivity<ActivityWeatherBinding>() {

    companion object {
        fun actionStart(context: Context, cityName: String, longitude: String, latitude: String) {
            val intent = Intent(context, WeatherActivity::class.java).apply {
                putExtra(WeatherConstant.LOCATION_LNG, longitude)
                putExtra(WeatherConstant.LOCATION_LAT, latitude)
                putExtra(WeatherConstant.PLACE_NAME, cityName)
            }
            context.startActivity(intent)
        }
    }

    // 初始化ViewModel-使用Koin依赖注入的方法
    private val viewModel by viewModel<WeatherViewModel>()

    override fun getLayoutId(): Int = R.layout.activity_weather

    override fun initView(savedInstanceState: Bundle?) {
        // 初始化View
//        mBinding.smartRefreshLayout.setRefreshHeader(ClassicsHeader(this))
        //falsify
        mBinding.smartRefreshLayout.setRefreshHeader(MaterialHeader(this))
        // 初始化数据
        getIntentData()
        // 刷新天气数据
        refreshWeather()
    }

    fun getCurrentCity(): String {
        if (viewModel.cityName.isEmpty()) {
            viewModel.cityName = intent.getStringExtra(WeatherConstant.PLACE_NAME) ?: ""
        }
        return viewModel.cityName
    }

    fun currentActivityRefresh(place: Place) {
        //如果当前是WeatherContainerActivity，则不需要再次跳转，只需关闭DrawerLayout，并请求新数据并刷新界面即可
        mBinding.drawerLayout.closeDrawers()
        viewModel.locationLng = place.location.lng
        viewModel.locationLat = place.location.lat
        viewModel.cityName = place.name
        refreshWeather()
    }

    private fun refreshWeather() {
        viewModel.refreshWeather(viewModel.locationLng, viewModel.locationLat)
    }

    private fun getIntentData() {
        if (viewModel.locationLng.isEmpty()) {
            viewModel.locationLng = intent.getStringExtra(WeatherConstant.LOCATION_LNG) ?: ""
        }
        if (viewModel.locationLat.isEmpty()) {
            viewModel.locationLat = intent.getStringExtra(WeatherConstant.LOCATION_LAT) ?: ""
        }
        if (viewModel.cityName.isEmpty()) {
            viewModel.cityName = intent.getStringExtra(WeatherConstant.PLACE_NAME) ?: ""
        }
    }

    override fun initListener() {
        super.initListener()
        // 初始化Refresh组件
        initRefreshListener()

        mBinding.apply {
            layoutWeatherItemNow.btnNav.setOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)
            }

            drawerLayout.addDrawerListener(object : DrawerListener {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {

                }

                override fun onDrawerOpened(drawerView: View) {
                    drawerView.isClickable = true
                }

                override fun onDrawerClosed(drawerView: View) {
                    val im = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    im.hideSoftInputFromWindow(
                        drawerView.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS
                    )
                }

                override fun onDrawerStateChanged(newState: Int) {

                }

            })
        }
    }

    private fun initRefreshListener() {
        mBinding.smartRefreshLayout.setOnRefreshListener {
            viewModel.isFirstRefresh = false
            refreshWeather()
        }
    }

    override fun startObserver() {
        super.startObserver()
        viewModel.weatherLiveData.observe(this) {
            if (it != null) {
                render(it)
            }
        }
    }

    private fun render(uiState: WeatherUiState) {
        when (uiState) {
            is WeatherUiState.Error -> {
                onError(uiState)
            }

            WeatherUiState.Loading -> {
                onLoad()
            }

            is WeatherUiState.Success -> {
                onSuccess(uiState)
            }
        }

    }

    private fun onSuccess(uiState: WeatherUiState.Success) {
        if (viewModel.isFirstRefresh) {
            dismissLoading()
            viewModel.isFirstRefresh = false
        }
        mBinding.smartRefreshLayout.finishRefresh()
        val weather = uiState.result

        LogUtils.d(tag = "refreshWeather", msg = GsonUtils.toJson(weather))

        if (weather != null) {
            showWeatherInfo(weather)
        } else {
            ToastUtil.showFailRectangleToast(this, true, "无法成功获取天气信息")
        }
    }

    private fun onLoad() {
        if (viewModel.isFirstRefresh) {
            showLoading()
        }
    }

    private fun onError(uiState: WeatherUiState.Error) {
        if (viewModel.isFirstRefresh) {
            dismissLoading()
            viewModel.isFirstRefresh = false
        }
        mBinding.smartRefreshLayout.finishRefresh()
        ToastUtil.showFailRectangleToast(this, true, uiState.message)
        LogUtils.e(uiState.message)
    }

    private fun showWeatherInfo(weather: WeatherBean) {
        // 城市名称
        mBinding.apply {
            layoutWeatherItemNow.tvPlaceName.text = viewModel.cityName
            // 实时天气
            setRealTimeInfo(weather)
            // 未来天气
            setForecastInfo(weather)
            // 生活指数（当天）
            setRealTimeLifeInfo(weather)
            // 显示布局
            scrollWeatherLayout.visible()
        }
    }

    /**
     * 生活指数（当天）
     */
    private fun setRealTimeLifeInfo(weather: WeatherBean) {
        mBinding.layoutWeatherLifeIndex.apply {
            // 填充layout_life_index.xml布局中的数据
            // 生活指数
            val lifeIndex = weather.daily.life_index
            // 感冒指数
            val coldRiskIndex = lifeIndex.coldRisk[0].desc
            // 紫外线指数
            val ultravioletIndex = lifeIndex.ultraviolet[0].desc
            // 穿衣指数
            val dressingIndex = lifeIndex.dressing[0].desc
            // 洗车指数
            val carWashingIndex = lifeIndex.carWashing[0].desc
            tvColdRisk.text = coldRiskIndex
            tvUltraviolet.text = ultravioletIndex
            tvDressing.text = dressingIndex
            tvCarWashing.text = carWashingIndex
        }
    }

    /**
     * 未来天气
     * 使用一个for-in循环来处理每天的天气信息
     * 在循环中动态加载layout_weather_forecast.xml布局并设置相应的数据，然后添加到父布局中
     */
    private fun setForecastInfo(weather: WeatherBean) {
        mBinding.layoutWeatherForecast.apply {
            //未来天气
            val daily = weather.daily
            // 填充layout_weather_forecast.xml布局中的数据
            //清空父布局中的子布局
            llForecastLayout.removeAllViews()
            val days = daily.skycon.size
            for (i in 0 until days) {
                val skycon = daily.skycon[i]
                val temperature = daily.temperature[i]

                val forecastItemView =
                    layoutInflater.inflate(R.layout.weather_item_forecast, llForecastLayout, false)
                val tvDateInfo = forecastItemView.findViewById<TextView>(R.id.tvDateInfo)
                val ivSkyIcon = forecastItemView.findViewById<ImageView>(R.id.ivSkyIcon)
                val tvSkyInfo = forecastItemView.findViewById<TextView>(R.id.tvSkyInfo)
                val tvTemperatureInfo =
                    forecastItemView.findViewById<TextView>(R.id.tvTemperatureInfo)

                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                // 日期
                tvDateInfo.text = simpleDateFormat.format(skycon.date)
                // 天气
                val sky = getSky(skycon.value)
                ivSkyIcon.setImageResource(sky.icon)
                tvSkyInfo.text = sky.info
                // 气温范围
                val tempScope = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"
                tvTemperatureInfo.text = tempScope
                // 添加View到父布局中
                llForecastLayout.addView(forecastItemView)
            }
        }
    }

    /**
     * 实时天气
     */
    private fun setRealTimeInfo(weather: WeatherBean) {
        mBinding.layoutWeatherItemNow.apply {
            // 实时天气
            val realtime = weather.realtime
            // 填充layout_weather_now.xml布局中的数据
            // 实时气温
            val currentTemp = "${realtime.temperature.toInt()} ℃"
            tvCurrentTemp.text = currentTemp
            // 天气状况
            tvCurrentSky.text = getSky(realtime.skycon).info
            ivRealSkyIcon.setImageResource(getSky(realtime.skycon).icon)
            // 空气指数
            val currentPM25 =
                "空气指数 ${realtime.air_quality.aqi.chn.toInt()}  ${realtime.air_quality.description.chn}"
            tvCurrentAQI.text = currentPM25
            //气温范围
            val tempRealScope =
                "${weather.daily.temperature[0].min.toInt()} ~ ${weather.daily.temperature[0].max.toInt()} ℃"
            tvRealTempScope.text = tempRealScope
            //风速
            val windSpeed = "${realtime.wind.speed} 级"
            tvWind.text = windSpeed
            //实时天气背景图
            //rlNowLayout
            rlNowLayout.setBackgroundResource(getSky(realtime.skycon).bg)
        }
    }
}
