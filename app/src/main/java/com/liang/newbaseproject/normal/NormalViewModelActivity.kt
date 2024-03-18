package com.liang.newbaseproject.normal

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.liang.module_base.base.BaseActivity
import com.liang.module_base.utils.LogUtils
import com.liang.module_base.utils.MoshiUtil
import com.liang.module_base.utils.ToastUtil
import com.liang.newbaseproject.R
import com.liang.newbaseproject.bean.WanDataBean
import com.liang.newbaseproject.databinding.ActivityNormalViewModelBinding
import com.liang.newbaseproject.livedata.MainTestViewModel
import com.liang.newbaseproject.livedata.WanApiRepository
import com.liang.newbaseproject.livedata.WanBaseResBean
import com.zhpan.bannerview.BannerViewPager

/**
 * - Time: 2023/6/1/0001 on 13:13
 * - User: Jerry
 * - Description: 不使用注解的开发方式
 */
class NormalViewModelActivity : BaseActivity<ActivityNormalViewModelBinding>() {

    companion object {
        private val TAG = NormalViewModelActivity::class.java.simpleName

        const val REQUEST_CODE_ALBUM = 101
    }

    // 初始化方法1
    private lateinit var mainTestViewModel: MainTestViewModel

//    // 初始化方法2
//    private val mainTestViewModel: MainTestViewModel by lazy {
////        ViewModelProvider(
////            this,
////            MainTestViewModelFactory(BaseApp.application, WanApiRepository)
////        )[MainTestViewModel::class.java]
//        ViewModelProvider(
//            this,
//            MainTestViewModel.FACTORY(application, WanApiRepository)
//        )[MainTestViewModel::class.java]
//    }

    // 初始化方法3
//    private val mainTestViewModel: MainTestViewModel by viewModels {
////        val factory = mainTestViewModelFactory(BaseApp.application, wanApiRepository = WanApiRepository)
////        mainTestViewModelFactory(BaseApp.application, wanApiRepository = WanApiRepository)
////        mainTestViewModelFactory(BaseApp.application, WanApiRepository)
//
//        // ViewModelHelpers
//        MainTestViewModel.FACTORY(BaseApp.application, WanApiRepository)
//    }

//    private lateinit var binding: ActivityMainBinding

    private var mirrorUI: Boolean = false

    override fun getLayoutId(): Int = R.layout.activity_normal_view_model

    override fun initView(savedInstanceState: Bundle?) {
//        setContentView(R.layout.activity_main)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        // 初始化方法1
//        val factory = MainTestViewModelFactory(application, WanApiRepository)
//        mainTestViewModel = ViewModelProvider(this, factory)[MainTestViewModel::class.java]

        val repository = WanApiRepository
        mainTestViewModel =
            ViewModelProvider(
                this,
                MainTestViewModel.FACTORY(application, repository)
            )[MainTestViewModel::class.java]

//        mBinding.btnLoginTest.setOnClickListener {
//            val username = "15044856689"
//            val password = "000000"
//
//            mainTestViewModel.loginTest(username, password)
//        }

        initBannerViewPager()
    }

    private lateinit var mBannerViewPager: BannerViewPager<WanDataBean>

    private fun initBannerViewPager() {
        mBannerViewPager = findViewById(R.id.bannerViewPager)
        mBannerViewPager.apply {
            adapter = BannerAdapter()
            registerLifecycleObserver(lifecycle)
            setOnPageClickListener { _, position ->
                val wanDataBean = mBannerViewPager.data[position]
//                wanDataBean.title.showShortToast()
//                ToastUtil.showShort(
//                    this@NormalViewModelActivity,
//                    wanDataBean.title,
//                    mirrorX = if (mirrorUI) -1F else 1F
//                )
                ToastUtil.showSuccessToast(
                    this@NormalViewModelActivity,
                    true,
                    wanDataBean.title,
                    mirrorX = mirrorUI
                )
            }
            create()
        }
    }

    override fun initListener() {
        super.initListener()
        mBinding.btnGet.setOnClickListener {
            mainTestViewModel.getBanner()
        }

        //switchMultipleSelectionMode.setOnCheckedChangeListener { buttonView, isChecked ->
        ////                pictureSelectorViewModel.multipleSelectionMode = isChecked
        //                multipleSelectionMode = isChecked
        //            }

        mBinding.switchMirror.setOnCheckedChangeListener { buttonView, isChecked ->
            mirrorUI = isChecked
//            if (isChecked) {
//                // 获取需要翻转的父布局
//                mBinding.mirrorContainer.scaleY = -1F
//            } else {
//                mBinding.mirrorContainer.scaleY = 1F
//            }
            // 获取需要翻转的父布局
            mBinding.mirrorContainer.apply {
                scaleX = (if (isChecked) -1F else 1F)
            }
//            StatusBarUtil.setSystemBarMirror(this, isChecked)
        }
    }

    override fun startObserver() {
        super.startObserver()
        mainTestViewModel.apply {
            loadingLiveData.observe(this@NormalViewModelActivity) {
                mBinding.apply {
                    btnGet.text = if (it) "Loading..." else "Button"
//                    groupProgressContainer.apply {
//                        if (it) visible() else gone()
//                    }
                    if (it) showLoading() else dismissLoading()
                }
            }

            // Banner
            bannerLiveData.observe(this@NormalViewModelActivity) {
                // 异步获取数据（例如从服务器或数据库获取数据），可以调用不带参数的create()方法,
                // 当成功拿到数据后再调用refreshData()方法刷新数据：
                mBannerViewPager.refreshData(it.data)

                LogUtils.d(
                    msg = "initObserve: bannerLiveData1 Moshi : ${
                        MoshiUtil.fromJson<WanBaseResBean<List<WanDataBean>>>(MoshiUtil.toJson(it))
                    }"
                )

                LogUtils.d(
                    msg = "initObserve: bannerLiveData2 Moshi : ${
                        MoshiUtil.fromJson<List<WanDataBean>>(MoshiUtil.toJson(it.data))
                    }"
                )

                LogUtils.d(
                    msg = "initObserve: bannerLiveData3 Moshi : ${
                        MoshiUtil.formatToJson(it.data[0])
                    }"
                )
            }
        }

////        mainTestViewModel.loginTestLiveData.observe(this) {
////            Log.d(TAG, "initObserve: loginTestLiveData  +${Gson().toJson(it)}")
////        }
//
//
//        mainTestViewModel.loginTestLiveData.observe(this) {
//            Log.d(TAG, "initObserve: loginTestLiveData  +  ${Gson().toJson(it)}")
//            if (it.status == 200) {
//                Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show()
//            }
//        }
//

//
//        // LiveEventBus以生命周期感知模式订阅消息
//        LiveEventBus
//            .get("LiveEventBusKey", String::class.java)
//            .observe(this@MainTestActivity) {
//                ToastUtil.showShort(this, "receive: $it")
//                LogUtils.d(tag = "LiveEventBus", msg = "LiveEventBus Observe =====> $it")
//                mBinding.textView2.text = it
//            }
//
//        LiveEventBus
//            .get(HomeRecommendBean::class.java)
//            .observe(this@MainTestActivity) {
//                mBinding.textView2.text = it.nickname
//
//                // Coil图片加载
//                mBinding.imageView.load(it.avatar) {
//                    crossfade(true)
//                    placeholder(R.drawable.app_vector_image)
//                    error(R.drawable.app_vector_broken_image)
//                    transformations(CircleCropTransformation())
//                }
//
//                LogUtils.d(
//                    tag = "LiveEventBus",
//                    msg = "LiveEventBus Observe  =====> ${MoshiUtil.toJson(it)}"
//                )
//            }
//

    }


}