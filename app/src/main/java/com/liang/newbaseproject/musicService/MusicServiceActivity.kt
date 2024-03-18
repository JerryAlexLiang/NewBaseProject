package com.liang.newbaseproject.musicService

import android.R.attr.description
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.liang.module_base.base.BaseActivity
import com.liang.newbaseproject.R
import com.liang.newbaseproject.databinding.ActivityMusicServiceBinding
import jp.wasabeef.glide.transformations.BlurTransformation


class MusicServiceActivity : BaseActivity<ActivityMusicServiceBinding>() {

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, MusicServiceActivity::class.java)
            context.startActivity(intent)
        }
    }

    private var isPlaying: Boolean = false;

    override fun getLayoutId(): Int = R.layout.activity_music_service

    @SuppressLint("Recycle")
    override fun initView(savedInstanceState: Bundle?) {
        Glide.with(this)
            .load(R.drawable.ic_avatar)
//            .load(R.drawable.ic_default_avator)
            .circleCrop()
            .into(mBinding.ivMusicAvatar)

        // 动画
        val animator = ObjectAnimator.ofFloat(mBinding.ivMusicAvatar, "rotation", 0.0f, 360.0f)
        animator.apply {
            duration = 16 * 1000
            repeatCount = Animation.INFINITE         // 无限循环
            repeatMode = ObjectAnimator.RESTART      // 循环模式
            interpolator = LinearInterpolator()      // 匀速
        }

        isPlaying = true
        animator.start()
        mBinding.btnStartPause.background = ContextCompat.getDrawable(this, R.drawable.ic_pause)

        //shapeableImageView
        Glide.with(this)
            .load(R.drawable.ic_avatar)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(50,3)))
            .into(mBinding.shapeableImageView)

        mBinding.btnStartPause.setOnClickListener {
            if (isPlaying){
                // 如果当前正在播放，则则暂停播放
                animator.pause()
                mBinding.btnStartPause.background = ContextCompat.getDrawable(this, R.drawable.ic_start)
            }else{
                // 如果当前暂停播放，则切换为开始播放
                animator.resume()
                mBinding.btnStartPause.background = ContextCompat.getDrawable(this, R.drawable.ic_pause)
            }
            // 更改播放状态
            isPlaying = !isPlaying
        }

    }
}