package com.liang.newbaseproject.baidumap

import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.liang.module_base.base.BaseActivity
import com.liang.newbaseproject.R
import com.liang.newbaseproject.databinding.ActivityBaiduMapBinding

class BaiduMapActivity : BaseActivity<ActivityBaiduMapBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_baidu_map
    }

    override fun initView(savedInstanceState: Bundle?) {
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }

    override fun onResume() {
        super.onResume()
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mBinding.baiduMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mBinding.baiduMapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mBinding.baiduMapView.onDestroy()
    }
}
