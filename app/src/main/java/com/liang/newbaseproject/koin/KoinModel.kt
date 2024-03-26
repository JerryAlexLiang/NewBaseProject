package com.liang.newbaseproject.koin

import com.liang.module_base.http.net.RetrofitManager
import com.liang.newbaseproject.livedata.WanApiRepositoryKoin
import com.liang.newbaseproject.livedata.WanApiService
import com.liang.newbaseproject.main.MainFunRvAdapter
import com.liang.newbaseproject.main.MainViewModel
import com.liang.newbaseproject.pictureSelector.GalleryRvPictureAdapter
import com.liang.newbaseproject.pictureSelector.PictureSelectorViewModel
import com.liang.newbaseproject.room.AppRoomDatabase
import com.liang.newbaseproject.room.RoomRepository
import com.liang.newbaseproject.splash.SplashViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModel = module {
    /** 网络请求 Module */
    single {
        RetrofitManager.getApiService(WanApiService::class.java)
    }

    single {
        RetrofitManager.getApiService(MxnzpApiService::class.java)
    }

    /** Room数据库 */
    // single与factory功能一致，只不过创建的是单例
    single {
//        Room.databaseBuilder(
//            androidApplication(),
//            AppRoomDatabase::class.java,
//            "app_database"
//        ).build()

        //初始化Database
        AppRoomDatabase.getDataBase()
    }

    single {
        val database = get<AppRoomDatabase>()
        // 获取MediaBeanDao数据库操作类
        database.getMediaBeanDao()
    }

    /** 数据仓库 Module */
    // single与factory功能一致，只不过创建的是单例
    single {
        WanApiRepositoryKoin(get())
    }

    single {
        MxnzpApiRepository(get())
    }

    single {
        RoomRepository(get())
    }

    /** ViewModel Module */
    viewModel {
        SplashViewModel(androidApplication())
    }

    viewModel {
        MainViewModel(androidApplication())
    }

    viewModel {
        MxnzpViewModel(androidApplication(), get())
    }

//
//    viewModel {
//        TestBaseViewModel(androidApplication(), get(), get())
//    }
//
    viewModel {
        PictureSelectorViewModel(androidApplication(), get())
    }

    /** 适配器 Module */
    // factory创建注入类的实例
    factory {
        MainFunRvAdapter()
    }
    factory {
        GalleryRvPictureAdapter()
    }
}

/**
 * 注释
 */
//// single->单例式  factory->每次都创建不同实例  viewModel->VM注入
//    // androidApplication()->获取当前Application , androidContext() -> 获取context
//    // 1 . 获取api实例
//    single { RetrofitClient.getInstance().create(ApiService::class.java) }
//    // 2. 创建实例前若构造方法内有参数 则需先注入构造中的参数实例
//    single<HttpDataSource> { HttpDataImpl(get()) }
//    // 3. 获取本地数据调用的实例
//    single<LocalDataSource> { LocalDataImpl() }
//    // 4 .综合以上本地+网络两个数据来源 得到数据仓库
//    single { DataRepository(get(), get()) }
//    // bind 将指定的实例绑定到对应的class  single { AppViewModelFactory(androidApplication(), get()) } bind TestActivity::class
//    single { AppViewModelFactory(get(), get()) }
//    // 维护一个全局单例的登录弹窗组 避免多次弹出
//    single(named("login_map")) { ConcurrentHashMap<Int,BasePopupView>(1) }