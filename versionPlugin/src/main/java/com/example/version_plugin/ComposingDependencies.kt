package com.example.version_plugin

object DepsVersions {

    //---------------网络相关------------------
    /** Retrofit */
    const val retrofit = "2.9.0"

    /** GSON */
    const val gson = "2.8.8"
    const val converterGson = "2.9.0"

    /** Moshi */
    const val moshi = "1.15.0"
    const val converterMoshi = retrofit

    // okhttp3 日志拦截器
    const val loggingInterceptor = "4.8.0"

    // okhttp3 缓存
    const val persistentCookieJar = "v1.0.1"

    /** 加载反馈页管理框架 https://github.com/KingJA/LoadSir */
    const val loadSir = "1.3.8"

    /**
     * 腾讯MMKV 基于 mmap 的高性能通用 key-value 组件
     */
    const val mmkv = "1.2.15"

    //---------------图片相关------------------
    /**
     * Glide
     */
    const val glide = "4.15.1"

    /**
     * Coil 图片加载
     * - Github: [https://github.com/coil-kt/coil]
     */
    const val coil_version = "2.3.0"

    //---------------UI相关-------------
    /**
     * Androidx ViewPager2
     * - implementation("androidx.viewpager2:viewpager2:1.0.0")
     */
    const val viewpager2 = "1.0.0"

    /**
     * Recyclerview
     */
    const val recyclerview = "1.2.1"

    /**
     * CardView
     * - implementation 'androidx.cardview:cardview:1.0.0'
     */
    const val cardView = "1.0.0"

    /**
     * BaseQuickAdapter
     * - https://github.com/CymChad/BaseRecyclerViewAdapterHelper
     */
    //implementation "io.github.cymchad:BaseRecyclerViewAdapterHelper:4.0.0-beta10"
    const val baseQuickAdapter = "4.0.0-beta10"

    /**
     * SmartRefreshLayout
     */
    const val smartRefreshLayout = "2.0.6"

    /**
     * SwipeRefreshLayout
     */
    const val swipeRefreshLayout = "1.1.0"

    /**
     * Google官方的flex布局
     */
    const val flexbox = "3.0.0"

    /**
     * Material风格功能强大的Dialog
     */
    const val materialDialogsCore = "3.3.0"
    const val materialDialogsLifecycle = "3.3.0"
    const val materialDialogsBottomSheets = "3.3.0"

    /**
     * 状态栏工具
     * - Github: [https://github.com/gyf-dev/ImmersionBar]
     */
    const val immersionBar = "3.2.2"

    /**
     * 换肤支持
     * - GitHub: [https://github.com/ximsfei/Android-skin-support]
     */
    const val skinSupport = "4.0.5"

    /**
     * 工具类utilcodex
     */
    const val utilCodex = "1.30.6"

    /**
     * LiveEventBus
     * 1.8及以上版本全面迁移至maven，同时groupID变为io.github.jeremyliao，1.8以下版本保留JCenter
     * 1.8及以上版本由于拆分了GsonProcessor，需要引入lebx-processor-gson
     * - Github：[https://github.com/JeremyLiao/LiveEventBus]
     *
     */
    const val liveEventBus = "1.8.0"
    const val lebxProcessorGson = "1.8.0"

    /**
     * ARouter 路由
     * - Github: [https://github.com/alibaba/ARouter]
     */
    const val ARouterVersion = "1.5.2"

    /**
     * 权限请求
     * com.permissionx.guolindev
     * - implementation 'com.guolindev.permissionx:permissionx:'
     */
    const val permissionX = "1.7.1"

    /**
     * PictureSelector
     * - https://github.com/LuckSiege/PictureSelector
     */
    const val pictureSelector = "v3.11.1"

    /**
     * BannerViewPager
     * - https://github.com/zhpanvip/BannerViewPager
     */
    //com.github.zhpanvip:bannerviewpager:latestVersion
    const val bannerViewPager = "3.5.11"

    /**
     * - Exoplayer视频播放器
     * - implementation "com.google.android.exoplayer:exoplayer:${2.17.1}"
     */
    const val exoplayer = "2.17.1"

    /**
     * 今日头条的适配方案
     */
    const val autoSize = "1.2.1"

    /**
     * multidex
     */
    const val multidex = "2.0.1"

}

object ComposingDependencies {

    //---------------网络相关------------------
    /**
     * Retrofit
     */
    const val retrofit = "com.squareup.retrofit2:retrofit:${DepsVersions.retrofit}"

    /** GSON */
    const val gson = "com.google.code.gson:gson:${DepsVersions.gson}"
    const val converterGson =
        "com.squareup.retrofit2:converter-gson:${DepsVersions.converterGson}"

    /** Moshi */
    const val moshi = "com.squareup.moshi:moshi-kotlin:${DepsVersions.moshi}"
    const val moshi_kt = "com.squareup.moshi:moshi-kotlin:${DepsVersions.moshi}"
    const val converterMoshi =
        "com.squareup.retrofit2:converter-moshi:${DepsVersions.converterMoshi}"

    /**
     * Okhttp3 日志拦截器
     */
    const val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${DepsVersions.loggingInterceptor}"

    /**
     * Okhttp3 缓存
     */
    const val persistentCookieJar =
        "com.github.franmontiel:PersistentCookieJar:${DepsVersions.persistentCookieJar}"

    /**
     * 加载反馈页管理框架 https://github.com/KingJA/LoadSir
     */
    const val loadSir = "com.kingja.loadsir:loadsir:${DepsVersions.loadSir}"

    /**
     * 腾讯MMKV 基于 mmap 的高性能通用 key-value 组件
     */
    const val mmkv = "com.tencent:mmkv:${DepsVersions.mmkv}"

    //---------------图片相关------------------
    /**
     * Glide
     */
    const val glide = "com.github.bumptech.glide:glide:${DepsVersions.glide}"

    /**
     * Coil 图片加载
     * - Github: [https://github.com/coil-kt/coil]
     */
    const val coil = "io.coil-kt:coil:${DepsVersions.coil_version}"
    const val coil_base = "io.coil-kt:coil-base:${DepsVersions.coil_version}"
    const val coil_gif = "io.coil-kt:coil-gif:${DepsVersions.coil_version}"
    const val coil_svg = "io.coil-kt:coil-svg:${DepsVersions.coil_version}"
    const val coil_video = "io.coil-kt:coil-video:${DepsVersions.coil_version}"

    //---------------UI相关-------------
    /**
     * Androidx ViewPager2
     * - implementation("androidx.viewpager2:viewpager2:1.0.0")
     */
    const val viewpager2 = "androidx.viewpager2:viewpager2:${DepsVersions.viewpager2}"

    /**
     * Recyclerview
     */
    const val recyclerview = "androidx.recyclerview:recyclerview:${DepsVersions.recyclerview}"

    /**
     * CardView
     * - implementation ''
     */
    const val cardView = "androidx.cardview:cardview:${DepsVersions.cardView}"

    /**
     * BaseQuickAdapter
     * - https://github.com/CymChad/BaseRecyclerViewAdapterHelper
     */
    const val baseQuickAdapter =
        "io.github.cymchad:BaseRecyclerViewAdapterHelper:${DepsVersions.baseQuickAdapter}"

    /**
     * SmartRefreshLayout
     * - Github：[https://github.com/scwang90/SmartRefreshLayout]
     * - 核心必须依赖
     * - implementation  'io.github.scwang90:refresh-layout-kernel:2.0.6'
     * - 经典刷新头
     * - implementation  'io.github.scwang90:refresh-header-classics:2.0.6'
     * - 雷达刷新头
     * - implementation  'io.github.scwang90:refresh-header-radar:2.0.6'
     * - 虚拟刷新头
     * - implementation  'io.github.scwang90:refresh-header-falsify:2.0.6'
     * - 谷歌刷新头
     * - implementation  'io.github.scwang90:refresh-header-material:2.0.6'
     * - 二级刷新头
     * - implementation  'io.github.scwang90:refresh-header-two-level:2.0.6'
     * - 球脉冲加载
     * - implementation  'io.github.scwang90:refresh-footer-ball:2.0.6'
     * - 经典加载
     * - implementation  'io.github.scwang90:refresh-footer-classics:2.0.6'
     */
    const val smartRefreshLayout =
        "io.github.scwang90:refresh-layout-kernel:${DepsVersions.smartRefreshLayout}"
    const val smartRefreshHeaderClassics =
        "io.github.scwang90:refresh-header-classics:${DepsVersions.smartRefreshLayout}"
    const val smartRefreshFooterClassics =
        "io.github.scwang90:refresh-footer-classics:${DepsVersions.smartRefreshLayout}"


    /**
     * SwipeRefreshLayout
     */
    const val swipeRefreshLayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${DepsVersions.swipeRefreshLayout}"

    /**
     * Google官方的flex布局
     */
    const val flexbox = "com.google.android.flexbox:flexbox:${DepsVersions.flexbox}"

    /**
     * Material风格功能强大的Dialog
     */
    const val materialDialogsCore =
        "com.afollestad.material-dialogs:core:${DepsVersions.materialDialogsCore}"
    const val materialDialogsLifecycle =
        "com.afollestad.material-dialogs:lifecycle:${DepsVersions.materialDialogsLifecycle}"
    const val materialDialogsbottomsheets =
        "com.afollestad.material-dialogs:bottomsheets:${DepsVersions.materialDialogsBottomSheets}"

    /**
     * 状态栏工具
     * - Github: [https://github.com/gyf-dev/ImmersionBar]
     */
    const val immersionBar =
        "com.geyifeng.immersionbar:immersionbar:${DepsVersions.immersionBar}" // 基础依赖包，必须要依赖
    const val immersionBarKtx =
        "com.geyifeng.immersionbar:immersionbar-ktx:${DepsVersions.immersionBar}" // kotlin扩展（可选）

    /**
     * 换肤支持
     * - GitHub: [https://github.com/ximsfei/Android-skin-support]
     */
    // skin-support 基础控件支持
    const val skinSupport = "skin.support:skin-support:${DepsVersions.skinSupport}"
    const val skinSupportAppcompat =
        "skin.support:skin-support-appcompat:${DepsVersions.skinSupport}"   // skin-support 基础控件支持(从3.x.x迁移至4.0.5+, 解耦了换肤库对appcompat包的依赖)
    const val skinSupportMaterial =
        "skin.support:skin-support-design:${DepsVersions.skinSupport}"  // skin-support-design material design 控件支持[可选]
    const val skinSupportCardView =
        "skin.support:skin-support-cardview:${DepsVersions.skinSupport}"  // skin-support-cardview CardView 控件支持[可选]
    const val skinSupportConstraint =
        "skin.support:skin-support-constraint-layout:${DepsVersions.skinSupport}" // skin-support-constraint-layout ConstraintLayout 控件支持[可选]

    /**
     * 工具类utilcodex
     */
    const val utilCodex = "com.blankj:utilcodex:${DepsVersions.utilCodex}"

    /**
     * LiveEventBus
     * 1.8及以上版本全面迁移至maven，同时groupID变为io.github.jeremyliao，1.8以下版本保留JCenter
     * 1.8及以上版本由于拆分了GsonProcessor，需要引入lebx-processor-gson
     * - Github：[https://github.com/JeremyLiao/LiveEventBus]
     *
     */
    const val liveEventBus = "io.github.jeremyliao:live-event-bus-x:${DepsVersions.liveEventBus}"
    const val lebxProcessorGson =
        "io.github.jeremyliao:lebx-processor-gson:${DepsVersions.lebxProcessorGson}"

    /**
     * ARouter 路由
     * - Github: [https://github.com/alibaba/ARouter]
     */
    const val aRouterApi = "com.alibaba:arouter-api:${DepsVersions.ARouterVersion}"
    const val aRouterCompiler = "com.alibaba:arouter-compiler:${DepsVersions.ARouterVersion}"

    /**
     * 权限请求
     * com.permissionx.guolindev
     */
    const val permissionX = "com.guolindev.permissionx:permissionx:${DepsVersions.permissionX}"

    /**
     * PictureSelector
     * - https://github.com/LuckSiege/PictureSelector
     */
    // PictureSelector basic (Necessary)
    const val pictureSelectorBasic =
        "io.github.lucksiege:pictureselector:${DepsVersions.pictureSelector}"

    // image compress library (Not necessary)
    const val pictureSelectorCompress =
        "io.github.lucksiege:compress:${DepsVersions.pictureSelector}"

    // uCrop library (Not necessary)
    const val pictureSelectorUCrop = "io.github.lucksiege:ucrop:${DepsVersions.pictureSelector}"

    // simple camerax library (Not necessary)
    const val pictureSelectorCameraX = "io.github.lucksiege:camerax:${DepsVersions.pictureSelector}"

    /**
     * BannerViewPager
     * - https://github.com/zhpanvip/BannerViewPager
     */
    const val bannerViewPager =
        "com.github.zhpanvip:bannerviewpager:${DepsVersions.bannerViewPager}"

    /**
     * Exoplayer视频播放器
     * implementation "com.google.android.exoplayer:exoplayer:${2.17.1}"
     */
//    const val exoplayer = "com.google.android.exoplayer:exoplayer:${DepsVersions.exoplayer}"

    /**
     * 今日头条的适配方案
     */
    const val autoSize = "me.jessyan:autosize:${DepsVersions.autoSize}"

    /**
     * multidex
     */
    const val multidex = "androidx.multidex:multidex:${DepsVersions.multidex}"
}