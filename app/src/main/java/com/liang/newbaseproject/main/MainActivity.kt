package com.liang.newbaseproject.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.KeyguardManager
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.os.PowerManager
import android.view.KeyEvent
import androidx.lifecycle.lifecycleScope
import com.liang.module_base.base.BaseActivity
import com.liang.module_base.extension.mirrorViewByXForPositive
import com.liang.module_base.extension.mirrorViewByXForReverse
import com.liang.module_base.extension.setGridLayoutManager
import com.liang.module_base.extension.showShortToast
import com.liang.module_base.extension.showShortToastMirrorX
import com.liang.module_base.utils.ClickAnimationUtils
import com.liang.module_base.utils.LanguageUtilKt
import com.liang.module_base.utils.decoration.SpaceItemDecorationKt
import com.liang.newbaseproject.R
import com.liang.newbaseproject.databinding.ActivityMainBinding
import com.liang.newbaseproject.screenlight.ScreenOffAdminReceiver
import com.liang.newbaseproject.splash.SplashActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName

        const val REQUEST_CODE_ALBUM = 101
    }

    private lateinit var adminReceiver: ComponentName
    private lateinit var mPowerManager: PowerManager
    private lateinit var policyManager: DevicePolicyManager
    private var mWakeLock: PowerManager.WakeLock? = null


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

        adminReceiver = ComponentName(this, ScreenOffAdminReceiver::class.java)
        mPowerManager = getSystemService(POWER_SERVICE) as PowerManager
        policyManager = getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager
        checkAndTurnOnDeviceManager();


    }

    /**
     * 检测并去激活设备管理器权限
     */
    fun checkAndTurnOnDeviceManager() {
        val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminReceiver)
        intent.putExtra(
            DevicePolicyManager.EXTRA_ADD_EXPLANATION,
            "开启后就可以使用锁屏功能了..."
        )
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        isOpen()
    }

    /**
     * 判断超级管理员是否激活
     */
    private fun isOpen() {
        if (policyManager.isAdminActive(adminReceiver)) {
            //判断超级管理员是否激活
            "设备已被激活".showShortToast()
        } else {
            "设备没有被激活".showShortToast()
        }
    }

    /**
     * @param view 检测屏幕状态
     */
    fun checkScreen() {
        val pm = getSystemService(POWER_SERVICE) as PowerManager
        val screenOn = pm.isScreenOn
        if (!screenOn) { //如果灭屏
            //相关操作
            "屏幕是息屏".showShortToast()
            // 亮屏操作
            checkScreenOn()
        } else {
            "屏幕是亮屏".showShortToast()
            // 息屏操作
//            checkScreenOff()
            checkScreenOffAndDelayOn()
        }
    }

    /**
     * 亮屏
     */
    fun checkScreenOn() {
        mWakeLock = mPowerManager.newWakeLock(
            PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
            "tag:WakeLock"
        )
        mWakeLock?.acquire(10 * 60 * 1000L /*10 minutes*/)
        mWakeLock?.release()
    }

    /**
     * 熄屏
     */
    fun checkScreenOff() {
        val admin = policyManager.isAdminActive(adminReceiver)
        if (admin) {
            ScreenLockNow()
        } else {
            "没有设备管理权限".showShortToast()
        }
    }

    /**
     * 熄屏并延时亮屏
     */
    fun checkScreenOffAndDelayOn() {
        val admin = policyManager.isAdminActive(adminReceiver)
        if (admin) {
            ScreenLockNow()
            lifecycleScope.launch {
                delay(3000)
                checkScreenOn()
            }
        } else {
            "没有设备管理权限".showShortToast()
        }
    }

    /**
     * 息屏API,取消掉设备的屏保
     */
    private fun ScreenLockNow() {
        // 取消掉设备的屏保
        val km = getSystemService(KEYGUARD_SERVICE) as KeyguardManager
        val kl = km.newKeyguardLock("name")
        kl.disableKeyguard()
        // 息屏
        policyManager.lockNow()
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
                        } else if (funNameResId == R.string.func_breathing_plate) {
                            checkScreen()
                        } else {
                            LanguageUtilKt.getStrByLanguage(
                                this@MainActivity,
                                mainFunBean?.funNameResId ?: R.string.func_default_text
//                            ).showShortToast()
                            ).showShortToastMirrorX(mirrorUI)
                        }
                    }
                }
            })
        }
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