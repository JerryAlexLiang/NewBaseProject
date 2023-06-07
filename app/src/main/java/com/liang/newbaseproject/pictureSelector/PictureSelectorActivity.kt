package com.liang.newbaseproject.pictureSelector

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.liang.module_base.base.BaseActivity
import com.liang.module_base.utils.GlideEngineKt
import com.liang.module_base.utils.LogUtils
import com.liang.module_base.utils.PictureSelectorUtils
import com.liang.module_base.utils.decoration.FullyGridLayoutManagerKt
import com.liang.newbaseproject.R
import com.liang.newbaseproject.databinding.ActivityPictureSelectorBinding
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.luck.picture.lib.language.LanguageConfig
import com.luck.picture.lib.utils.DensityUtil
import com.yalantis.ucrop.decoration.GridSpacingItemDecoration
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * - Time: 2023/5/25/0025 on 15:32
 * - User: Jerry
 * - Description: PictureSelector
 */
class PictureSelectorActivity : BaseActivity<ActivityPictureSelectorBinding>() {

    companion object {
        private val TAG = PictureSelectorActivity::class.java.simpleName
    }

    private val pictureSelectorViewModel by viewModel<PictureSelectorViewModel>()

    private val chooseMode = SelectMimeType.ofAll()
    private val language = LanguageConfig.ENGLISH

    // 默认多选
    var multipleSelectionMode: Boolean = true

    override fun getLayoutId(): Int {
        return R.layout.activity_picture_selector
    }

    /** 功能列表适配器 */
    private val galleryRvAdapter by inject<GalleryRvAdapter>()

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.rvGallery.apply {
            layoutManager = FullyGridLayoutManagerKt(
                this@PictureSelectorActivity,
                6,
                GridLayoutManager.VERTICAL,
                false
            )
            if (itemAnimator != null) {
                (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            }
            addItemDecoration(
                GridSpacingItemDecoration(
                    6,
                    DensityUtil.dip2px(this@PictureSelectorActivity, 2f), false
                )
            )
            adapter = galleryRvAdapter
        }
    }



    override fun initListener() {
        super.initListener()
        mBinding.apply {
            switchMultipleSelectionMode.setOnCheckedChangeListener { buttonView, isChecked ->
//                pictureSelectorViewModel.multipleSelectionMode = isChecked
                multipleSelectionMode = isChecked
            }

            btnSelectorPictureSystem.setOnClickListener {
//            choosePicture()
                PictureSelectorUtils
                    .openSystemGallery(this@PictureSelectorActivity)
                    .forSystemResult(pictureSelectorViewModel.onResultCallbackListener())
            }

            btnSelectorPicture.setOnClickListener {
                PictureSelectorUtils
                    .openGallery(
                        this@PictureSelectorActivity,
                        multipleSelectionMode = multipleSelectionMode,
                        uiStyle = PictureSelectorUtils.NUM_STYLE,
                        selectedDataList = pictureSelectorViewModel.galleryList
                    )
                    .forResult(pictureSelectorViewModel.onResultCallbackListener())
            }

            btnSelectorPictureWeChat.setOnClickListener {
                PictureSelectorUtils.openGallery(
                    this@PictureSelectorActivity,
                    language = LanguageConfig.ENGLISH,
                    uiStyle = PictureSelectorUtils.CHAT_STYLE,
                    multipleSelectionMode = multipleSelectionMode,
                    isMaxSelectEnabledMask = true,
                    isCustomCameraEvent = true,
                    selectedDataList = pictureSelectorViewModel.galleryList
                )
                    .forResult(pictureSelectorViewModel.onResultCallbackListener())
            }

            btnOnlyGallery.setOnClickListener {
                PictureSelectorUtils
                    .openGallery(
                        this@PictureSelectorActivity,
                        multipleSelectionMode = multipleSelectionMode,
                        isDisplayCamera = false,
                        isDirectReturnSingle = true,
                        selectedDataList = pictureSelectorViewModel.galleryList
                    )
                    .forResult(pictureSelectorViewModel.onResultCallbackListener())
            }

            btnOnlyCamera.setOnClickListener {
                PictureSelectorUtils.openCamera(
                    this@PictureSelectorActivity,
                    selectedDataList = pictureSelectorViewModel.galleryList
                )
                    .forResult(pictureSelectorViewModel.onResultCallbackListener())
            }

            btnCaptureAudio.setOnClickListener {
                PictureSelectorUtils
                    .openGallery(
                        this@PictureSelectorActivity,
                        chooseMode = PictureSelectorUtils.chooseModeAudio,
                        multipleSelectionMode = multipleSelectionMode,
                        isDisplayCamera = true,
                        selectedDataList = pictureSelectorViewModel.galleryList
                    )
                    .forResult(pictureSelectorViewModel.onResultCallbackListener())
            }
        }

        // 预览图片、视频、音频
//        galleryRvAdapter.setOnItemClickListener { adapter, view, position ->
        galleryRvAdapter.setOnItemClickListener { _, _, position ->
            PictureSelectorUtils.openPreview(
                this@PictureSelectorActivity,
                recyclerview = mBinding.rvGallery,
                position = position,
                dataList = pictureSelectorViewModel.galleryList
            )
        }
    }

    override fun startObserver() {
        super.startObserver()
        pictureSelectorViewModel.mediaListLiveData.observe(this) {
            analyticalSelectResults(it)
        }
    }

    private fun choosePicture() {
        PictureSelector.create(this)
            .openGallery(chooseMode)
            .setDefaultLanguage(LanguageConfig.ENGLISH)
            .setLanguage(LanguageConfig.ENGLISH)
//            .setImageEngine(CoilEngine())
            .setImageEngine(GlideEngineKt.createGlideEngine())
            // 预览点击全屏效果
            .isPreviewFullScreenMode(true)
            .isPreviewVideo(true)
            // 视频支持暂停与播放
            .isVideoPauseResumePlay(true)
            .isAutoVideoPlay(false)
            .isLoopAutoVideoPlay(true)
            // 使用系统播放器
            .isUseSystemVideoPlayer(false)
            .forResult(onResultCallbackListener())

    }

    private fun onResultCallbackListener() = object : OnResultCallbackListener<LocalMedia> {
        override fun onResult(result: ArrayList<LocalMedia>) {
            analyticalSelectResults(result)
        }

        override fun onCancel() {
            LogUtils.d(msg = "onSelectFinish PictureSelector Cancel")
        }

    }

    private fun analyticalSelectResults(result: ArrayList<LocalMedia>) {
//        for (media in result) {
//            if (media.width == 0 || media.height == 0) {
//                if (PictureMimeType.isHasImage(media.mimeType)) {
//                    val imageExtraInfo =
//                        MediaUtils.getImageSize(
//                            this@PictureSelectorActivity,
//                            media.path
//                        )
//                    media.width = imageExtraInfo.width
//                    media.height = imageExtraInfo.height
//                } else if (PictureMimeType.isHasVideo(media.mimeType)) {
//                    val videoExtraInfo =
//                        MediaUtils.getVideoSize(
//                            this@PictureSelectorActivity,
//                            media.path
//                        )
//                    media.width = videoExtraInfo.width
//                    media.height = videoExtraInfo.height
//                }
//            }
//
//            LogUtils.d(tag = TAG, msg = "文件名: " + media.fileName)
//            LogUtils.d(tag = TAG, msg = "是否压缩:" + media.isCompressed)
//            LogUtils.d(tag = TAG, msg = "压缩:" + media.compressPath)
//            LogUtils.d(tag = TAG, msg = "初始路径:" + media.path)
//            LogUtils.d(tag = TAG, msg = "绝对路径:" + media.realPath)
//            LogUtils.d(tag = TAG, msg = "是否裁剪:" + media.isCut)
//            LogUtils.d(tag = TAG, msg = "裁剪路径:" + media.cutPath)
//            LogUtils.d(tag = TAG, msg = "是否开启原图:" + media.isOriginal)
//            LogUtils.d(tag = TAG, msg = "原图路径:" + media.originalPath)
//            LogUtils.d(tag = TAG, msg = "沙盒路径:" + media.sandboxPath)
//            LogUtils.d(tag = TAG, msg = "水印路径:" + media.watermarkPath)
//            LogUtils.d(tag = TAG, msg = "视频缩略图:" + media.videoThumbnailPath)
//            LogUtils.d(tag = TAG, msg = "原始宽高: " + media.width + "x" + media.height)
//            LogUtils.d(
//                tag = "",
//                msg = "裁剪宽高: " + media.cropImageWidth + "x" + media.cropImageHeight
//            )
//            LogUtils.d(
//                tag = "",
//                msg = "文件大小: " + PictureFileUtils.formatAccurateUnitFileSize(media.size)
//            )
//            LogUtils.d(tag = "", msg = "文件时长: " + media.duration)
//        }

        PictureSelectorUtils.analyticalSelectResults(result)

//        lifecycleScope.launch(Dispatchers.Main) {
//            // Coil图片加载
//            val media = result[0]
//            val path: String = media.availablePath
//            mBinding.ivGallery.load(
//                if (PictureMimeType.isContent(path) && !media.isCut && !media.isCompressed) Uri.parse(
//                    path
//                ) else path
//            ) {
//                crossfade(true)
//                placeholder(R.drawable.app_vector_image)
//                error(R.drawable.app_vector_broken_image)
//                transformations(CircleCropTransformation())
//            }
//        }

        runOnUiThread {
            // Coil图片加载
            val media = result[0]
            val path: String = media.availablePath
//            mBinding.ivGallery.load(
//                if (PictureMimeType.isContent(path) && !media.isCut && !media.isCompressed) Uri.parse(
//                    path
//                ) else path
//            ) {
//                crossfade(true)
//                placeholder(com.liang.module_base.R.drawable.app_vector_image)
//                error(com.liang.module_base.R.drawable.app_vector_broken_image)
//                transformations(CircleCropTransformation())
//            }
            pictureSelectorViewModel.galleryList = result

            galleryRvAdapter.submitList(pictureSelectorViewModel.galleryList)
        }
    }

}