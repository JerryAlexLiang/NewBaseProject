package com.liang.newbaseproject.ratingBar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.liang.module_base.base.BaseActivity
import com.liang.module_base.utils.ToastUtil
import com.liang.newbaseproject.R
import com.liang.newbaseproject.databinding.ActivityRatingBarBinding
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

    override fun getLayoutId(): Int {
        return R.layout.activity_rating_bar
    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun initListener() {
        super.initListener()
        mBinding.ratingBar.setOnRatingChangeListener { ratingBar, rating ->
            ToastUtil.onShowSuccessToast(this, true, "当前星级：$rating")
        }
    }
}