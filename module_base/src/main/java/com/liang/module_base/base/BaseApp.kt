package com.liang.module_base.base

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.liang.module_base.extension.getNightMode
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tencent.mmkv.MMKV
import kotlin.properties.Delegates

/**
 * @Time: 2023/4/3/0003 on 17:29
 * @User: Jerry
 * @Description: Application基类
 */
open class BaseApp : Application(), ViewModelStoreOwner {

    private lateinit var mAppViewModelStore: ViewModelStore

    private var mFactory: ViewModelProvider.Factory? = null

    companion object {
        lateinit var application: BaseApp
        var appContext: Context by Delegates.notNull()
        lateinit var globalViewModel: GlobalViewModel
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        appContext = applicationContext
        mAppViewModelStore = ViewModelStore()
        globalViewModel = getAppViewModelProvider()[GlobalViewModel::class.java]

        // MMKV初始化
        initMMKV()
        // 获取当前的主题
        initDefaultNightMode()
        // 初始化LiveEventBus
        initLiveEventBus()
    }

    /**
     * 初始化LiveEventBus
     */
    private fun initLiveEventBus() {
        LiveEventBus
            .config()
            // 配置LifecycleObserver（如Activity）接收消息的模式（默认值true）
            // true：整个生命周期（从onCreate到onDestroy）都可以实时收到消息
            // false：激活状态（Started）可以实时收到消息，非激活状态（Stoped）无法实时收到消息，需等到Activity重新变成激活状态，方可收到消息
            .lifecycleObserverAlwaysActive(true)
            // 配置在没有Observer关联的时候是否自动清除LiveEvent以释放内存（默认值false）
            .autoClear(false)
    }

    /**
     * MMKV初始化
     */
    private fun initMMKV() {
        // MMKV初始化
        MMKV.initialize(this)
    }

    /**
     * 获取当前的主题
     */
    private fun initDefaultNightMode() {
        AppCompatDelegate.setDefaultNightMode(
            if (getNightMode()) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

    /**
     * 获取一个全局的ViewModel
     */
    fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, getAppFactory())
    }

    private fun getAppFactory(): ViewModelProvider.Factory {
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return mFactory as ViewModelProvider.Factory
    }

//    override fun getViewModelStore(): ViewModelStore {
//        return mAppViewModelStore
//    }

    override val viewModelStore: ViewModelStore
        get() = mAppViewModelStore
}
