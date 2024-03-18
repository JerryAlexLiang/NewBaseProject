package com.liang.newbaseproject.ratingBar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.liang.module_base.base.BaseActivity
import com.liang.module_base.utils.LogUtils
import com.liang.module_base.utils.MoshiUtil
import com.liang.module_base.utils.ToastUtil
import com.liang.newbaseproject.R
import com.liang.newbaseproject.bean.WanDataBean
import com.liang.newbaseproject.databinding.ActivityRatingBarBinding
import com.liang.newbaseproject.livedata.MainTestViewModel
import com.liang.newbaseproject.livedata.WanApiRepository
import com.liang.newbaseproject.livedata.WanBaseResBean

/**
 * - Time: 2023/6/30/0030 on 17:32
 * - User: Jerry
 * - Description: RatingBar星级评分控件
 */
class RatingBarActivity : BaseActivity<ActivityRatingBarBinding>() {

    companion object {

        fun actionStart(context: Context) {
            val intent = Intent(context, RatingBarActivity::class.java)
            context.startActivity(intent)
        }

    }

    private lateinit var mainTestViewModel: MainTestViewModel

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

//    private val mainTestViewModel: MainTestViewModel by viewModels {
////        val factory = mainTestViewModelFactory(BaseApp.application, wanApiRepository = WanApiRepository)
////        mainTestViewModelFactory(BaseApp.application, wanApiRepository = WanApiRepository)
////        mainTestViewModelFactory(BaseApp.application, WanApiRepository)
//
//        // ViewModelHelpers
//        MainTestViewModel.FACTORY(BaseApp.application, WanApiRepository)
//    }

    override fun getLayoutId(): Int {
        return R.layout.activity_rating_bar
    }

    override fun initView(savedInstanceState: Bundle?) {
//        val factory = MainTestViewModelFactory(application, WanApiRepository)
//        mainTestViewModel = ViewModelProvider(this, factory)[MainTestViewModel::class.java]

        val repository = WanApiRepository
        mainTestViewModel =
            ViewModelProvider(
                this,
                MainTestViewModel.FACTORY(application, repository)
            )[MainTestViewModel::class.java]
    }

    override fun initListener() {
        super.initListener()
        mBinding.ratingBar.setOnRatingChangeListener { ratingBar, rating ->
            ToastUtil.showSuccessToast(this, true, "当前星级：$rating")
        }
    }

    override fun startObserver() {
        super.startObserver()
        mainTestViewModel.apply {
            loadingLiveData.observe(this@RatingBarActivity) {
                mBinding.apply {
                    if (it) showLoading() else dismissLoading()
                }
            }

            // Banner
            bannerLiveData.observe(this@RatingBarActivity) {
                // 异步获取数据（例如从服务器或数据库获取数据），可以调用不带参数的create()方法,
                // 当成功拿到数据后再调用refreshData()方法刷新数据：

                LogUtils.d(
                    tag = "RatingBar",
                    msg = "initObserve: bannerLiveData1 Moshi : ${
                        MoshiUtil.fromJson<WanBaseResBean<List<WanDataBean>>>(MoshiUtil.toJson(it))
                    }"
                )

                LogUtils.d(
                    tag = "RatingBar",
                    msg = "initObserve: bannerLiveData2 Moshi : ${
                        MoshiUtil.fromJson<List<WanDataBean>>(MoshiUtil.toJson(it.data))
                    }"
                )

                LogUtils.d(
                    tag = "RatingBar",
                    msg = "initObserve: bannerLiveData3 Moshi : ${
                        MoshiUtil.formatToJson(it.data[0])
                    }"
                )
            }
        }
    }
}