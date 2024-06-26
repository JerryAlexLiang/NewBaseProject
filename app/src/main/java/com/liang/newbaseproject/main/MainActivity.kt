package com.liang.newbaseproject.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import com.baselib.datapicker.DatePickerActivity
import com.liang.module_base.base.BaseActivity
import com.liang.module_base.extension.mirrorViewByXForPositive
import com.liang.module_base.extension.mirrorViewByXForReverse
import com.liang.module_base.extension.setGridLayoutManager
import com.liang.module_base.extension.showShortToast
import com.liang.module_base.extension.showShortToastMirrorX
import com.liang.module_base.utils.ClickAnimationUtils
import com.liang.module_base.utils.GsonUtils
import com.liang.module_base.utils.LanguageUtilKt
import com.liang.module_base.utils.ToastUtil
import com.liang.module_base.utils.decoration.SpaceItemDecorationKt
import com.liang.module_route.impl.PictureSelectorServiceProvider
import com.liang.module_route.impl.WeatherServiceProvider
import com.liang.newbaseproject.R
import com.liang.newbaseproject.databinding.ActivityMainBinding
import com.liang.newbaseproject.koin.KoinActivity
import com.liang.newbaseproject.musicService.MusicServiceActivity
import com.liang.newbaseproject.ratingBar.RatingBarActivity
import com.liang.newbaseproject.splash.SplashActivity
import com.liang.newbaseproject.widget.CustomViewActivity
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

    private var mirrorUI: Boolean = false

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
        mBinding.apply {
            ivBtnSetting.setOnClickListener {
                "Setting".showShortToast()
            }

            switchMirror.setOnCheckedChangeListener { _, isChecked ->
                mirrorUI = isChecked
                // 获取需要翻转的父布局
                mBinding.root.apply {
//                    scaleX = (if (isChecked) -1F else 1F)
                    if (isChecked) {
                        mirrorViewByXForReverse(this)
                    } else {
                        mirrorViewByXForPositive(this)
                    }
                }
            }
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
                        } else if (funNameResId == R.string.func_typewriter) {
                            val intent = Intent()
                            intent.putExtra("isShowButton", true)
                            intent.setClass(this@MainActivity, SplashActivity::class.java)
                            startActivity(intent)
                        } else if (funNameResId == R.string.func_SDK_aar) {
                            val intent = Intent()
                            intent.setClass(this@MainActivity, DatePickerActivity::class.java)
                            startActivity(intent)
                        } else if (funNameResId == R.string.func_breathing_plate) {
//                            checkScreenOffOrOn()
                        } else if (funNameResId == R.string.func_rating_bar) {
                            RatingBarActivity.actionStart(this@MainActivity)
                        } else if (funNameResId == R.string.func_retrofit_koin) {
                            KoinActivity.actionStart(this@MainActivity)
                        } else if (funNameResId == R.string.func_custom_view) {
                            CustomViewActivity.actionStart(this@MainActivity)
                        } else if (funNameResId == R.string.func_music_service) {
                            MusicServiceActivity.actionStart(this@MainActivity)
                        } else if (funNameResId == R.string.func_choose_image) {
                            PictureSelectorServiceProvider.startPictureSelectorActivity(this@MainActivity)
                        } else if (funNameResId == R.string.func_weather) {
                            WeatherServiceProvider.startWeatherMainActivity(this@MainActivity)
                        } else if (funNameResId == R.string.func_crash_test) {
                            testAppCrashCatch()
                        } else {
                            val strByLanguage = LanguageUtilKt.getStrByLanguage(
                                this@MainActivity,
                                mainFunBean?.funNameResId ?: R.string.func_default_text
//                            ).showShortToast()
                            )
//                            LanguageUtilKt.getStrByLanguage(
//                                this@MainActivity,
//                                mainFunBean?.funNameResId ?: R.string.func_default_text
////                            ).showShortToast()
//                            ).showShortToastMirrorX(mirrorUI)

                            ToastUtil.showSuccessRectangleToast(
                                this@MainActivity,
                                true,
                                strByLanguage
                            )
                        }
                    }
                }
            })
        }
    }

    /**
     * 程序崩溃框架测试
     */
    private fun testAppCrashCatch() {
        val list: MutableList<String> = mutableListOf()
        list.add("肖战")
        list.add("贺峻霖")
        list.add("肖宇梁")
        list.add("丁程鑫")
        list.add("马天宇")
        list.clear()

        ToastUtil.showFailToast(this, true, "List列表第index=0项为：${GsonUtils.toJson(list[0])}")
    }

    override fun startObserver() {
        super.startObserver()
        mainViewModel.funBeanListLiveData.observe(this) {
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
//                getString(R.string.exit_application).showShortToast()
                getString(R.string.exit_application).showShortToastMirrorX(mirrorUI)
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