package com.liang.newbaseproject.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import com.liang.module_base.base.BaseActivity
import com.liang.module_base.extension.setGridLayoutManager
import com.liang.module_base.extension.showShortToast
import com.liang.module_base.utils.ClickAnimationUtils
import com.liang.module_base.utils.LanguageUtilKt
import com.liang.module_base.utils.LogUtils
import com.liang.module_base.utils.decoration.SpaceItemDecorationKt
import com.liang.newbaseproject.R
import com.liang.newbaseproject.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName

        const val REQUEST_CODE_ALBUM = 101
    }

    private val mainViewModel by viewModel<MainViewModel>()

    /** 功能列表适配器 */
    private val mainFunRvAdapter by inject<MainFunRvAdapter>()

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.rvFunction.apply {
            //setGridLayoutManager
            setGridLayoutManager(mainFunRvAdapter, 4)
//            setLinearLayoutManager(mainFunRvAdapter)
            if (itemDecorationCount == 0) {
                val decoration = SpaceItemDecorationKt()
                    .setLeft(12)
                    .setRight(12)
                    .setTop(12)
                    .setBottom(12)
                addItemDecoration(decoration)
            }
        }
    }

    override fun initData() {
        super.initData()
    }

    override fun initListener() {
        super.initListener()
        mBinding.ivBtnSetting.setOnClickListener {
            "Setting".showShortToast()
            startActivity(intent.putExtra("isShowButton", true))
        }

        mainFunRvAdapter.setOnItemClickListener { adapter, view, position ->
            ClickAnimationUtils.clickAnimation(view, object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    val mainFunBean = adapter.getItem(position)
                    if (adapter.items[position].activity != null) {
                        startActivity(Intent(this@MainActivity, adapter.items[position].activity))
                    } else {
                        val funNameResId = mainFunBean?.funNameResId
                        if (funNameResId == R.string.func_day_night) {
                            // 切换暗黑模式
                            changeDayNightMode()
                        } else if (funNameResId == R.string.func_change_language) {
                            if (LanguageUtilKt.isChinese) {
                                changeLanguage(false)
                            } else {
                                changeLanguage(true)
                            }
                        } else {
                            LanguageUtilKt.getStrByLanguage(
                                this@MainActivity,
                                mainFunBean?.funNameResId ?: R.string.func_default_text
                            ).showShortToast()
                        }
                    }
                }
            })
        }
    }

    override fun startObserver() {
        super.startObserver()
        mainViewModel.funBeanListLiveData.observe(this) {
            LogUtils.d(msg = "=============> ${it.size}")
            // 设置数据集合
            mainFunRvAdapter.submitList(it)
        }
    }

    private val waitTime: Long = 2000
    private var touchTime: Long = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - touchTime >= waitTime) {
                getString(R.string.exit_application).showShortToast()
                touchTime = currentTime
            } else {
                finish()
            }
            return true
        } else if (KeyEvent.KEYCODE_HOME == keyCode) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}