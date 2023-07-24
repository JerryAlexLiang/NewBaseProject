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
import org.koin.core.module.Module
import org.koin.dsl.module

/** 网络请求 Module */
val netModule = module {
    single {
        RetrofitManager.getApiService(WanApiService::class.java)
    }

    single {
        RetrofitManager.getApiService(MxnzpApiService::class.java)
    }
}

val daoModule = module {

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
}

/** 数据仓库 Module */
val repositoryModule: Module = module {
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
}

/** ViewModel Module */
val viewModelModule: Module = module {

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
}

/** 适配器 Module */
val adapterModule: Module = module {
    // factory创建注入类的实例
    factory {
        MainFunRvAdapter()
    }
    factory {
        GalleryRvPictureAdapter()
    }
}