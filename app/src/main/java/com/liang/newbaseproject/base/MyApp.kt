package com.liang.newbaseproject.base

import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.baidu.location.LocationClient
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.liang.module_base.BuildConfig
import com.liang.module_base.base.BaseApp
import com.liang.module_base.utils.LogUtils
import com.liang.newbaseproject.R
import com.liang.newbaseproject.koin.appModules
import com.liang.newbaseproject.main.MainActivity
import com.tencent.bugly.crashreport.CrashReport
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MyApp : BaseApp() {

//    private lateinit var appViewModel: AppViewModel

    companion object {
        lateinit var appViewModel: AppViewModel
    }


    override fun onCreate() {
        super.onCreate()
//        initLanguageAppName()

        // 初始化模块配置
//        initModuleConfig()
        // 将数据库实例化
        initRoomDataBase()
        initAppViewModel()
        initKoin()
        // 腾讯Bugly
        initBugly()
        // Android程序崩溃框架—CustomActivityOnCrash
        initCrashActivity()
        initBaiduMap()
    }

    private fun initBaiduMap() {
        SDKInitializer.setAgreePrivacy(this, true);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this)
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL)
        LocationClient.setAgreePrivacy(true)
    }

    /**
     * Android程序崩溃框架—CustomActivityOnCrash
     */
    private fun initCrashActivity() {
        //整个配置属性，可以设置一个或多个，也可以一个都不设置
        CaocConfig.Builder.create() //程序在后台时，发生崩溃的三种处理方式
            // BackgroundMode.BACKGROUND_MODE_SHOW_CUSTOM: //当应用程序处于后台时崩溃，也会启动错误页面，
            // BackgroundMode.BACKGROUND_MODE_CRASH:      //当应用程序处于后台崩溃时显示默认系统错误（一个系统提示的错误对话框），
            // BackgroundMode.BACKGROUND_MODE_SILENT:     //当应用程序处于后台时崩溃，默默地关闭程序！
            .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT)
            .enabled(true) //false表示对崩溃的拦截阻止,用它来禁用customActivityonCrash框架
            .showErrorDetails(true) //这将隐藏错误活动中的“错误详细信息”按钮，从而隐藏堆栈跟踪, —> 针对框架自带程序崩溃后显示的页面有用(DefaultErrorActivity)
            .showRestartButton(true) //是否可以重启页面,针对框架自带程序崩溃后显示的页面有用(DefaultErrorActivity)
            .logErrorOnRestart(false)
            .trackActivities(false) //错误页面中显示错误详细信息；针对框架自带程序崩溃后显示的页面有用(DefaultErrorActivity)
            .minTimeBetweenCrashesMs(2000) //定义应用程序崩溃之间的最短时间，以确定我们不在崩溃循环中。比如：在规定的时间内再次崩溃，框架将不处理，让系统处理
            .restartActivity(MainActivity::class.java) // 重新启动后的页面
            .errorDrawable(R.drawable.icon_star_active) //崩溃页面显示的图标
            .errorActivity(MyCrashActivity::class.java) //程序崩溃后显示的页面
            // .errorActivity(DefaultErrorActivity.class) //程序崩溃后显示的页面(默认程序崩溃时错误页面)
            .eventListener(CustomEventListener()) //设置监听
            .apply()
    }

    /**
     * 程序崩溃时设置监听
     * The event listener cannot be an inner or anonymous class,
     * because it will need to be serialized. Change it to a class of its own, or make it a static inner class.
     */
    private class CustomEventListener : CustomActivityOnCrash.EventListener {
        override fun onLaunchErrorActivity() {
            // 程序崩溃时回调
            LogUtils.d(
                tag = "CrashCustomEventListener",
                msg = "CustomActivityOnCrash =============>   程序崩溃时回调"
            )
        }

        override fun onRestartAppFromErrorActivity() {
            // 重启程序时回调
            LogUtils.d(
                tag = "CrashCustomEventListener",
                msg = "CustomActivityOnCrash =============>   重启程序时回调"
            )
        }

        override fun onCloseAppFromErrorActivity() {
            // 在崩溃提示页面关闭程序时回调
            LogUtils.d(
                tag = "CrashCustomEventListener",
                msg = "CustomActivityOnCrash =============>   在崩溃提示页面关闭程序时回调"
            )
        }
    }


    /**
     * 腾讯bugly
     */
    private fun initBugly() {
        CrashReport.setIsDevelopmentDevice(this, BuildConfig.DEBUG)
        val userStrategy: CrashReport.UserStrategy = CrashReport.UserStrategy(this)
        userStrategy.setUploadProcess(isMainProcess())


        //注册时申请的APP ID
        //第三个参数为SDK调试模式开关，调试模式的行为特性如下：
        //输出详细的Bugly SDK的Log；
        //每一条Crash都会被立即上报；
        //自定义日志将会在Logcat中输出。
        //建议在测试阶段建议设置成true，发布时设置为false。
        CrashReport.initCrashReport(this, "cdb269a031", BuildConfig.DEBUG, userStrategy)
    }

    /**
     * 通过反射拿到需要初始化的各种组件application
     * - 1、创建BaseApplication类，ModuleConfig类以及BaseApplicationImpl类。
     * - 2、BaseApplication类的onCreate()方法中初始化一些全局配置并且初始化模块配置。
     * - 3、BaseApplicationImpl类是一个接口类，需要各模块自己去实现各个模块的配置。
     * - 4、这些配置的类是定义在ModuleConfig中，在初始化的时候会通过反射创建这些类。
     * - 5、使用：
     * 在各个子module中实现BaseApplicationImpl，这个类可以提供模块化的配置以及application context对象。清单文件都设置为BaseApplication即可。
     */
//    private fun initModuleConfig() {
//        for (modules in ModuleConfig.MODULE_LIST) {
//            try {
//                val clz = Class.forName(modules)
//                val obj = clz.newInstance()
//                if (obj is BaseApplicationImpl) {
//                    obj.onCreate(this, BuildConfig.DEBUG)
//                }
//            } catch (e: ClassNotFoundException) {
//                e.printStackTrace()
//            } catch (e: IllegalAccessException) {
//                e.printStackTrace()
//            } catch (e: InstantiationException) {
//                e.printStackTrace()
//            }
//        }
//    }


    /**
     * 将数据库实例化
     */
    private fun initRoomDataBase() {

    }

    private fun initLanguageAppName() {
//        LanguageUtilKt.initAppPackageName("com.liang.newbaseproject")
//        LanguageUtilKt.initAppPackageName(resources.getString(R.string.app_package_name))
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(appModules)
        }
    }

    private fun initAppViewModel() {
        // appViewModel = getAppViewModelProvider().get(AppViewModel::class.java)
        appViewModel = getAppViewModelProvider()[AppViewModel::class.java]
    }
}
