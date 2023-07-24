package com.liang.newbaseproject.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.liang.module_base.base.BaseActivity
import com.liang.module_base.extension.gone
import com.liang.module_base.extension.startTargetActivity
import com.liang.module_base.extension.visible
import com.liang.module_base.http.model.DataStatus
import com.liang.module_base.utils.LogUtils
import com.liang.module_base.utils.MoshiUtil
import com.liang.module_base.widget.TypewriterTextView
import com.liang.newbaseproject.R
import com.liang.newbaseproject.databinding.ActivitySplashBinding
import com.liang.newbaseproject.koin.DouyinData
import com.liang.newbaseproject.koin.MxnzpBaseBean
import com.liang.newbaseproject.koin.MxnzpViewModel
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

    private val mxnzpViewModel: MxnzpViewModel by viewModel()

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initView(savedInstanceState: Bundle?) {
        isShowButton = intent.getBooleanExtra("isShowButton", false)
        // 使用Koin注解不能实现多个Activity共享ViewModel中的LiveData监听
        mxnzpViewModel.getMxnzpDouyinList()
    }

    override fun initListener() {
        super.initListener()
        mBinding.btnGetTypewriterData.apply {
            if (isShowButton) visible() else gone()
            setOnClickListener {
                splashViewModel.setTypeData()
            }
        }
//        mBinding.root.setOnLongClickListener {
//            if (!isShowButton) {
//                startToMain(0)
//            }
//            false
//        }

        mBinding.root.setOnClickListener {
            if (!isShowButton) {
                startToMain(0)
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
//                        startToMain(2000)
                        if (!isShowButton) {
                            startToMain(2000)
                        }
                    }
                }
            }
        }

        mxnzpViewModel.douyinListLiveData.observe(this) {
            handleResult(it)
        }

    }

    private fun handleResult(it: MxnzpBaseBean<List<DouyinData>>?) {
        Log.d(TAG, "initObserve: douyinListLiveData2 dataStatus  + ${it?.dataStatus}")
        when (it?.dataStatus) {
            DataStatus.STATE_LOADING -> showLoading()
            DataStatus.STATE_ERROR -> dismissLoading()
            DataStatus.STATE_SUCCESS -> {
                dismissLoading()
                if (it.code == 200) {
                    Log.d(TAG, "initObserve: douyinListLiveData2  Success + ${Gson().toJson(it)}")
                } else {
                    Log.d(TAG, "initObserve: douyinListLiveData2  + 请求失败：${it.msg}")
                }
            }

            null -> {}
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
//        startToMain(1000)
        if (!isShowButton) {
            startToMain(1000)
        }
    }

    private fun startToMain(time: Long) {
        lifecycleScope.launch(Dispatchers.Default) {
            delay(time)
            startTargetActivity<MainActivity>()
            finish()
        }
    }
}