package com.liang.newbaseproject.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.liang.module_base.base.BaseActivity
import com.liang.module_base.extension.gone
import com.liang.module_base.extension.startTargetActivity
import com.liang.module_base.extension.visible
import com.liang.module_base.utils.LogUtils
import com.liang.module_base.utils.MoshiUtil
import com.liang.module_base.widget.TypewriterTextView
import com.liang.newbaseproject.R
import com.liang.newbaseproject.databinding.ActivitySplashBinding
import com.liang.newbaseproject.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * - Time: 2023/6/2/0002 on 14:49
 * - User: Jerry
 * - Description: 启动页
 */
@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>(),
    TypewriterTextView.OnTypeViewListener {

    companion object {
        private val TAG = SplashActivity::class.java.simpleName
    }

    private var isShowButton: Boolean = false

    private val splashViewModel by viewModel<SplashViewModel>()

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initView(savedInstanceState: Bundle?) {
        isShowButton = intent.getBooleanExtra("isShowButton", false)
    }

    override fun initListener() {
        super.initListener()
        mBinding.btnGetTypewriterData.apply {
            if (isShowButton) visible() else gone()
            setOnClickListener {
                splashViewModel.setTypeData()
            }
        }
    }

    override fun startObserver() {
        super.startObserver()
        splashViewModel.apply {
            typeLiveData.observe(this@SplashActivity) {
                LogUtils.d(TAG, MoshiUtil.formatToJson(it))

                mBinding.typewriterTvWelcome.run {
                    setPlayVoice(it.isPlayVoice)
                    if (it.isOpenLauncherText) {
                        setOnTypeViewListener(this@SplashActivity)
                        start(it.name, 120)
                    } else {
                        text = it.name
                        startToMain(2000)
//                        if (isShowButton) {
//                            startToMain(2000)
//                        }
                    }
                }
            }
        }

    }

    override fun onTypeStart() {
        mBinding.typewriterTvWelcome.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.typewriter_color
            )
        )
    }

    override fun onTypeOver() {
        mBinding.typewriterTvWelcome.setTextColor(
            ContextCompat.getColor(
                this,
                com.liang.module_base.R.color.item_title
            )
        )
//        if (isShowButton) {
//            startToMain(1000)
//        }
        startToMain(1000)
    }

    private fun startToMain(time: Long) {
        lifecycleScope.launch(Dispatchers.Default) {
            delay(time)
            startTargetActivity<MainActivity>()
            finish()
        }
    }
}