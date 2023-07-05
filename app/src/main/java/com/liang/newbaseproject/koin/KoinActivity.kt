package com.liang.newbaseproject.koin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.liang.module_base.base.BaseActivity
import com.liang.module_base.http.model.DataStatus
import com.liang.newbaseproject.R
import com.liang.newbaseproject.databinding.ActivityKoinBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class KoinActivity : BaseActivity<ActivityKoinBinding>() {

    companion object {
        private val TAG = KoinActivity::class.java.simpleName

        fun actionStart(context: Context) {
            val intent = Intent(context, KoinActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val mxnzpViewModel: MxnzpViewModel by viewModel()

    override fun getLayoutId(): Int {
        return R.layout.activity_koin
    }

    override fun initView(savedInstanceState: Bundle?) {
        mxnzpViewModel.getMxnzpDouyinList()
    }

    override fun startObserver() {
        super.startObserver()
        mxnzpViewModel.douyinListLiveData.observe(this) {
            handleResult(it)
        }
    }

    private fun handleResult(it: MxnzpBaseBean<List<DouyinData>>?) {
        Log.d(TAG, "initObserve: douyinListLiveData2 dataStatus  + ${it?.dataStatus}")
        when (it?.dataStatus) {
            DataStatus.STATE_LOADING -> showLoading()
            DataStatus.STATE_ERROR -> dismissLoading()
            DataStatus.STATE_SUCCESS -> {
                dismissLoading()
                if (it.code == 200) {
                    Log.d(TAG, "initObserve: douyinListLiveData2  Success + ${Gson().toJson(it)}")
                } else {
                    Log.d(TAG, "initObserve: douyinListLiveData2  + 请求失败：${it.msg}")
                }
            }

            null -> {}
        }
    }
}