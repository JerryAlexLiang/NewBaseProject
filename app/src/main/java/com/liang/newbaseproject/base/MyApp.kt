package com.liang.newbaseproject.base

import com.liang.module_base.base.BaseApp
import com.liang.newbaseproject.koin.appModules
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
