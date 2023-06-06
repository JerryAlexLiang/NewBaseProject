package com.liang.newbaseproject.koin

import com.liang.module_base.http.net.RetrofitManager
import com.liang.newbaseproject.livedata.WanApiRepositoryKoin
import com.liang.newbaseproject.livedata.WanApiService
import com.liang.newbaseproject.main.MainFunRvAdapter
import com.liang.newbaseproject.main.MainViewModel
import com.liang.newbaseproject.pictureSelector.GalleryRvAdapter
import com.liang.newbaseproject.pictureSelector.PictureSelectorViewModel
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

/** 数据仓库 Module */
val repositoryModule: Module = module {
    single {
        WanApiRepositoryKoin(get())
    }

    single {
        MxnzpApiRepository(get())
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
        PictureSelectorViewModel(androidApplication())
    }
}

/** 适配器 Module */
val adapterModule: Module = module {
    factory {
        MainFunRvAdapter()
    }
    factory {
        GalleryRvAdapter()
    }
}