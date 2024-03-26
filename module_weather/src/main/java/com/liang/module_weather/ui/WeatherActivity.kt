package com.liang.module_weather.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.liang.module_base.base.BaseActivity
import com.liang.module_weather.R
import com.liang.module_weather.databinding.ActivityWeatherBinding

/**
 * - Time: 2024/3/22/0022 13:10
 * - User: Jerry
 * - Description: 天气主界面
 */
class WeatherActivity : BaseActivity<ActivityWeatherBinding>() {

    companion object{
        fun actionStart(context: Context){
            val intent = Intent(context, WeatherActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun getLayoutId(): Int = R.layout.activity_weather

    override fun initView(savedInstanceState: Bundle?) {

    }
}