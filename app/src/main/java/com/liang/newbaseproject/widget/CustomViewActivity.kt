package com.liang.newbaseproject.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.liang.module_base.base.BaseActivity
import com.liang.newbaseproject.R
import com.liang.newbaseproject.databinding.ActivityCustomViewBinding

class CustomViewActivity : BaseActivity<ActivityCustomViewBinding>() {

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, CustomViewActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_custom_view
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initView(savedInstanceState: Bundle?) {
        val starView = StarViewJava(this)
//        // 为StarView添加触摸事件监听
//        starView.setOnTouchListener { _, event ->
//            // 设置StarView显示位置的X坐标
//            starView.bitmapX = event.x
//            // 设置StarView显示位置的Y坐标
//            starView.bitmapY = event.y
//            // 重绘StarView组件
//            starView.invalidate()
//            true
//        }
        // 在布局中添加StarView
        mBinding.containerLayout.addView(starView)

        val starViewKt = StarViewKt(this)
//        starViewKt.setOnTouchListener { _, event ->
//            starViewKt.bitmapX = event.x
//            starViewKt.bitmapY = event.y
//            starViewKt.invalidate()
//            true
//        }
        mBinding.containerLayout2.addView(starViewKt)

    }
}