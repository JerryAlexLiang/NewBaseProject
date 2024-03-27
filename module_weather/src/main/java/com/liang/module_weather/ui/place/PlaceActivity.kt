package com.liang.module_weather.ui.place

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.liang.module_base.base.BaseActivity
import com.liang.module_weather.R
import com.liang.module_weather.databinding.ActivityPlaceBinding

/**
 * - Time: 2024/3/22/0022 13:10
 * - User: Jerry
 * - Description: 城市搜索主界面
 * - Android使用toolbar设置了fitSystemWindows = “true”的时候当edittext弹出了软键盘时toolbar被拉伸的问题
 * - 在manifest下给问题activity添加一个属性问题
 * - android:windowSoftInputMode = “adjustPan”
 */
class PlaceActivity : BaseActivity<ActivityPlaceBinding>() {

    companion object{
        fun actionStart(context: Context){
            val intent = Intent(context, PlaceActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun getLayoutId(): Int = R.layout.activity_place

    override fun initView(savedInstanceState: Bundle?) {

    }
}