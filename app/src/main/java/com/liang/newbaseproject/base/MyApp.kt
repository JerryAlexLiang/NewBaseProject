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
        // 将数据库实例化
        initRoomDataBase()
        initAppViewModel()
        initKoin()
    }

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
