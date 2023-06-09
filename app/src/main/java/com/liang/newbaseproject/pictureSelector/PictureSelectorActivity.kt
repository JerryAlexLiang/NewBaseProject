package com.liang.newbaseproject.pictureSelector

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.liang.module_base.base.BaseActivity
import com.liang.module_base.utils.GlideEngineKt
import com.liang.module_base.utils.LogUtils
import com.liang.module_base.utils.MoshiUtil
import com.liang.module_base.utils.PictureSelectorUtils
import com.liang.module_base.utils.decoration.FullyGridLayoutManagerKt
import com.liang.newbaseproject.R
import com.liang.newbaseproject.databinding.ActivityPictureSelectorBinding
import com.liang.newbaseproject.room.MediaBean
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnExternalPreviewEventListener
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
                        selectedDataList = pictureSelectorViewModel.gallerySelectedList
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
                    selectedDataList = pictureSelectorViewModel.gallerySelectedList
                )
                    .forResult(pictureSelectorViewModel.onResultCallbackListener())
            }

            btnOnlyGallery.setOnClickListener {
                PictureSelectorUtils
                    .openGallery(
                        this@PictureSelectorActivity,
                        multipleSelectionMode = multipleSelectionMode,
                        isDisplayCamera = false,
                        isDirectReturnSingle = false,
                        selectedDataList = pictureSelectorViewModel.gallerySelectedList
                    )
                    .forResult(pictureSelectorViewModel.onResultCallbackListener())
            }

            btnOnlyCamera.setOnClickListener {
                PictureSelectorUtils.openCamera(
                    this@PictureSelectorActivity,
                    selectedDataList = pictureSelectorViewModel.gallerySelectedList
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
                        selectedDataList = pictureSelectorViewModel.gallerySelectedList
                    )
                    .forResult(pictureSelectorViewModel.onResultCallbackListener())
            }
        }

        // 预览图片、视频、音频
//        galleryRvAdapter.setOnItemClickListener { adapter, view, position ->
        galleryRvAdapter.setOnItemClickListener { _, _, position ->
            PictureSelectorUtils.openPreview(
                this@PictureSelectorActivity,
                uiStyle = PictureSelectorUtils.DEFAULT_STYLE,
                recyclerview = mBinding.rvGallery,
                position = position,
                dataList = pictureSelectorViewModel.gallerySelectedList,
                isDisplayDelete = true,
                externalPreviewEventListener = customExternalPreviewEventListener(),
            )
        }
    }

    /**
     * 外部预览监听事件
     */
    private fun customExternalPreviewEventListener() = object : OnExternalPreviewEventListener {
        /**
         * 删除图片
         * @param position 删除的下标
         */
        override fun onPreviewDelete(position: Int) {
            pictureSelectorViewModel.gallerySelectedList.removeAt(position)
            galleryRvAdapter.removeAt(position)
            galleryRvAdapter.notifyItemRemoved(position)
        }

        /**
         * 长按下载
         * @param media 资源
         * @return false 自己实现下载逻辑；默认true
         */
        override fun onLongPressDownload(context: Context?, media: LocalMedia?): Boolean {
            return false
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
            PictureSelectorUtils.analyticalSelectResults(result)
        }

        override fun onCancel() {
            LogUtils.d(msg = "onSelectFinish PictureSelector Cancel")
        }

    }

    //    private fun analyticalSelectResults(result: ArrayList<LocalMedia>) {
    private fun analyticalSelectResults(result: List<MediaBean>) {

        LogUtils.d(msg = "MediaBeans: ${MoshiUtil.toJson(result)}")

//        PictureSelectorUtils.analyticalSelectResults(result)

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
//            val path: String = media.availablePath
            val path: String = media.localMedia.availablePath
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