package com.liang.module_base.base

import android.app.Application
import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.os.Process
import android.text.TextUtils
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.multidex.MultiDex
import coil.ComponentRegistry
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.VideoFrameDecoder
import com.alibaba.android.arouter.launcher.ARouter
import com.jeremyliao.liveeventbus.LiveEventBus
import com.liang.module_base.BuildConfig
import com.liang.module_base.extension.getNightMode
import com.tencent.mmkv.MMKV
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import kotlin.properties.Delegates

/**
 * @Time: 2023/4/3/0003 on 17:29
 * @User: Jerry
 * @Description: Application基类
 */
open class BaseApp : Application(), ViewModelStoreOwner, ImageLoaderFactory {

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

        MultiDex.install(this)

        //初始化路由SDK
        initARouter()

        // MMKV初始化
        initMMKV()
        // 获取当前的主题
        initDefaultNightMode()
        // 初始化LiveEventBus
        initLiveEventBus()
    }

    /**
     * 初始化路由SDK
     */
    private fun initARouter() {
        if (BuildConfig.DEBUG) {
            // 这两行必须写在init之前，否则这些配置在init过程中将无效
            // 打印日志
            ARouter.openLog()
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug()
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this)
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

    override fun newImageLoader(): ImageLoader {
        val imageLoader = ImageLoader.Builder(baseContext)
        val newBuilder = ComponentRegistry().newBuilder()
        newBuilder.add(VideoFrameDecoder.Factory())
        if (SDK_INT >= 28) {
            newBuilder.add(ImageDecoderDecoder.Factory())
        } else {
            newBuilder.add(GifDecoder.Factory())
        }
        val componentRegistry = newBuilder.build()

        imageLoader.components(componentRegistry)

        return imageLoader.build()
    }

    open fun isMainProcess(): Boolean {
        val mainProcessName: String = appContext.packageName
        val processName: String? = currentProcessName()
        return processName == null || TextUtils.equals(processName, mainProcessName)
    }

    //public static String currentProcessName() {
    //        return getProcessName(Process.myPid());
    //    }

    open fun currentProcessName(): String? {
        return getProcessName(Process.myPid())
    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    open fun getProcessName(pid: Int): String? {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
            var processName = reader.readLine()
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim { it <= ' ' }
            }
            return processName
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            try {
                reader?.close()
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
        return null
    }
}
