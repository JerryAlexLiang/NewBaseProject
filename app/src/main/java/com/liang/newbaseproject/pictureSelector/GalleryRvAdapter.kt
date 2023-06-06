package com.liang.newbaseproject.pictureSelector

import android.content.Context
import android.net.Uri
import android.view.ViewGroup
import android.widget.ImageView
import coil.load
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.DataBindingHolder
import com.liang.module_base.extension.gone
import com.liang.module_base.extension.visible
import com.liang.newbaseproject.R
import com.liang.newbaseproject.databinding.RvGvFilterImageBinding
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.utils.DateUtils

class GalleryRvAdapter : BaseQuickAdapter<LocalMedia, DataBindingHolder<RvGvFilterImageBinding>>() {

    override fun onBindViewHolder(
        holder: DataBindingHolder<RvGvFilterImageBinding>,
        position: Int,
        item: LocalMedia?
    ) {
        if (item == null) return

        // 获取Binding
        val binding = holder.binding
        val chooseModel = item.chooseModel
        val path = item.availablePath
        val duration = item.duration
        val mimeType = item.mimeType

//        PictureSelectorUtils.extracted(item)

        binding.apply {
            tvDuration.apply {
                if (PictureMimeType.isHasVideo(mimeType)) {
                    visible()
                } else {
                    gone()
                }
                text = DateUtils.formatDurationTime(duration)

                if (chooseModel == SelectMimeType.ofAudio()) {
                    visible()
                    ivFiv.load(R.drawable.iv_ic_audio_placeholder)
                    setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_audio, 0, 0, 0)
                } else {
                    setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_video, 0, 0, 0)
                    // Coil (Autodetects the scale type)
                    ivFiv.apply {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        load(
                            if (PictureMimeType.isContent(path) && !item.isCut && !item.isCompressed) {
                                Uri.parse(path)
                            } else {
                                path
                            }
                        ) {
                            crossfade(true)
                            placeholder(R.drawable.app_vector_image)
                            error(R.drawable.app_vector_broken_image)
                        }
                    }
                }
            }

            if (chooseModel == SelectMimeType.ofAudio()) {
                ivFiv.load(R.drawable.iv_ic_audio_placeholder)
            } else {
                // Coil (Autodetects the scale type)
                ivFiv.apply {
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    load(
                        if (PictureMimeType.isContent(path) && !item.isCut && !item.isCompressed) {
                            Uri.parse(path)
                        } else {
                            path
                        }
                    ) {
                        crossfade(true)
                        placeholder(R.drawable.app_vector_image)
                        error(R.drawable.app_vector_broken_image)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): DataBindingHolder<RvGvFilterImageBinding> {
//        return DataBindingHolder<RvGvFilterImageBinding>(R.layout.rv_gv_filter_image, parent)
        return DataBindingHolder(R.layout.rv_gv_filter_image, parent)
    }
}