package com.liang.module_picture_selector

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.liang.module_base.base.BaseActivity
import com.liang.module_picture_selector.databinding.ActivityTestBinding

class TestActivity : BaseActivity<ActivityTestBinding>() {

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, TestActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_test
    }

    override fun initView(savedInstanceState: Bundle?) {

    }
}