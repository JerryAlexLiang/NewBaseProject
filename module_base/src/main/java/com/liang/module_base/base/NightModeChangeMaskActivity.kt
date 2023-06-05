package com.liang.module_base.base

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.liang.module_base.R
import com.liang.module_base.databinding.ActivityNightModeChangeMaskBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NightModeChangeMaskActivity : BaseActivity<ActivityNightModeChangeMaskBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_night_mode_change_mask

    override fun initView(savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            delay(1000)
            finish()
        }
    }
}