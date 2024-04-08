package com.liang.newbaseproject.base

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.core.content.ContextCompat
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.liang.module_base.base.BaseActivity
import com.liang.module_base.utils.CopyUtils
import com.liang.module_base.utils.setOnClickListener
import com.liang.module_base.utils.setVisible
import com.liang.module_ui.R.color
import com.liang.newbaseproject.R
import com.liang.newbaseproject.databinding.ActivityMyCrashBinding

class MyCrashActivity : BaseActivity<ActivityMyCrashBinding>() {

    private var isLogShown = false

    private var configFromIntent: CaocConfig? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_my_crash
    }

    override fun initView(savedInstanceState: Bundle?) {
        configFromIntent = CustomActivityOnCrash.getConfigFromIntent(intent)
        if (configFromIntent == null) {
            finish()
            return
        }
    }

    override fun initListener() {
        super.initListener()
        mBinding.apply {
            setOnClickListener(tvShowLog, tvShowLog2, tvExit, tvRestart) {
                when (this) {
                    tvShowLog -> {
                        showAndCopyCrashLog()
                    }

                    tvShowLog2 -> {
                        showAndCopyCrashLog()
                    }

                    tvExit -> {
                        //退出应用
                        CustomActivityOnCrash.closeApplication(
                            this@MyCrashActivity,
                            configFromIntent!!
                        )
                    }

                    tvRestart -> {
                        //重启应用
                        CustomActivityOnCrash.restartApplication(
                            this@MyCrashActivity,
                            configFromIntent!!
                        )
                    }
                }
            }
        }
    }

    private fun showAndCopyCrashLog() {
        mBinding.apply {
            val crashLog = tvLog.text.toString()

            if (TextUtils.isEmpty(crashLog)) {
                isLogShown = true
                tvShowLog.text = "复制日志"
                tvShowLog2.text = "复制日志"
                tvShowLog.setTextColor(
                    ContextCompat.getColor(
                        this@MyCrashActivity,
                        color.text_surface,
                    )
                )
                ivBug.setVisible(View.GONE)
                scrollViewLog.setVisible(View.VISIBLE)
                tvLog.text =
                    CustomActivityOnCrash.getAllErrorDetailsFromIntent(this@MyCrashActivity, intent)
            } else {
                CopyUtils.copyText(crashLog)
                tvShowLog.text = "日志已复制"
                tvShowLog2.text = "日志已复制"
                tvShowLog.setTextColor(
                    ContextCompat.getColor(
                        this@MyCrashActivity,
                        color.red
                    )
                )
                tvShowLog.isEnabled = false
                tvShowLog2.isEnabled = false
            }
        }
    }
}