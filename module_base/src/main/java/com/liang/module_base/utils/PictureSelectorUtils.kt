package com.liang.module_base.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.liang.module_base.R
import com.liang.module_base.base.BaseApp
import com.liang.module_base.extension.getNightMode
import com.liang.module_base.extension.showShortToast
import com.luck.lib.camerax.SimpleCameraX
import com.luck.picture.lib.basic.PictureSelectionCameraModel
import com.luck.picture.lib.basic.PictureSelectionModel
import com.luck.picture.lib.basic.PictureSelectionSystemModel
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.config.SelectLimitType
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.config.SelectModeConfig
import com.luck.picture.lib.config.SelectorConfig
import com.luck.picture.lib.dialog.PictureLoadingDialog
import com.luck.picture.lib.engine.CompressFileEngine
import com.luck.picture.lib.engine.CropFileEngine
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnCameraInterceptListener
import com.luck.picture.lib.interfaces.OnCustomLoadingListener
import com.luck.picture.lib.interfaces.OnGridItemSelectAnimListener
import com.luck.picture.lib.interfaces.OnKeyValueResultCallbackListener
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.luck.picture.lib.interfaces.OnSelectAnimListener
import com.luck.picture.lib.interfaces.OnSelectLimitTipsListener
import com.luck.picture.lib.interfaces.OnVideoThumbnailEventListener
import com.luck.picture.lib.language.LanguageConfig
import com.luck.picture.lib.style.BottomNavBarStyle
import com.luck.picture.lib.style.PictureSelectorStyle
import com.luck.picture.lib.style.SelectMainStyle
import com.luck.picture.lib.style.TitleBarStyle
import com.luck.picture.lib.utils.DateUtils
import com.luck.picture.lib.utils.DensityUtil
import com.luck.picture.lib.utils.MediaUtils
import com.luck.picture.lib.utils.PictureFileUtils
import com.luck.picture.lib.utils.StyleUtils
import com.luck.picture.lib.utils.ToastUtils
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.UCropImageEngine
import top.zibin.luban.Luban
import top.zibin.luban.OnNewCompressListener
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object PictureSelectorUtils {

    private val TAG = PictureSelectorUtils::class.java.simpleName

    private lateinit var selectorStyle: PictureSelectorStyle

    private var languageConfig: Int =
        if (LanguageUtilKt.isChineseVersion) LanguageConfig.CHINESE else LanguageConfig.ENGLISH

    const val DEFAULT_STYLE = 0;
    const val WHITE_STYLE = 1;
    const val NUM_STYLE = 2;
    const val CHAT_STYLE = 3;

    private val chooseModeAll = SelectMimeType.ofAll()
    private val chooseModeImage = SelectMimeType.ofImage()
    private val chooseModeVideo = SelectMimeType.ofVideo()
    private val chooseModeAudio = SelectMimeType.ofAudio()

    /**
     * @param multipleSelectionMode  单选or多选   false:单选，true:多选
     * @param chooseMode     模式 0: all 1:image 2:video 3:音频
     * @param enableCrop     是否裁剪
     * @param enableCompress 是否压缩
     * @param isCamera       是否显示拍照按钮
     * @param maxSelectNum   最大图片选择数量
     */
    fun openSystemGallery(
        context: Context,
        multipleSelectionMode: Boolean = true,
        chooseMode: Int = chooseModeAll,
        enableCrop: Boolean = false,
        enableCompress: Boolean = false,
        openOriginal: Boolean = false
    ): PictureSelectionSystemModel {
        // 进入系统相册
        return PictureSelector.create(context)
            .openSystemGallery(chooseMode)
            .setSelectionMode(if (multipleSelectionMode) SelectModeConfig.MULTIPLE else SelectModeConfig.SINGLE)
            .setCompressEngine(getCompressFileEngine(enableCompress))
            .setCropEngine(getCropFileEngine(enableCrop))
            .setSkipCropMimeType(*getNotSupportCrop())
            .setSelectLimitTipsListener(MeOnSelectLimitTipsListener())
//                    .setAddBitmapWatermarkListener(getAddBitmapWatermarkListener())
            .setVideoThumbnailListener(getVideoThumbnailEventListener())
            .setCustomLoadingListener(getCustomLoadingListener())
            .isOriginalControl(openOriginal)  // 是否开启原图功能
//                    .setSandboxFileEngine(MeSandboxFileEngine())

    }

    /**
     * @param multipleSelectionMode  单选or多选   false:单选，true:多选
     * @param isCustomCameraEvent    自定义相机
     * @param imageSpanCount         相册列表每行显示个数
     * @param chooseMode     模式 0: all 1:image 2:video 3:音频
     * @param isDisplayCamera    是否打开摄像头按钮
     * @param isOpenClickSound    是否打开点击语音
     * @param isWithSelectVideoImage    是否打开点击语音
     * @param isWithSelectVideoImage    是否可以同时选择图片和视频
     * @param isPreviewZoomEffect    预览缩放效果模式
     * @param enableCrop     是否裁剪
     * @param enableCompress 是否压缩
     * @param maxSelectPicNum   最大图片选择数量
     * @param maxSelectVideoNum   最大视频选择数量
     * @param isMaxSelectEnabledMask   当达到最大选择数时，列表是否启用掩码效果(蒙层)
     * @param isDirectReturnSingle   单选模式直接返回
     * @param recordVideoMaxSecond   视频录制最大时长
     * @param recordVideoMinSecond   视频录制最小时长
     */
    fun openGallery(
        context: Context,
        isCustomCameraEvent: Boolean = false,
//        language: Int = LanguageConfig.CHINESE,
        language: Int = languageConfig,
        imageSpanCount: Int = 6,
        multipleSelectionMode: Boolean = true,
        isDisplayCamera: Boolean = true,
        isOpenClickSound: Boolean = false,
        isWithSelectVideoImage: Boolean = true,
        isPreviewZoomEffect: Boolean = false,
        chooseMode: Int = chooseModeAll,
        uiStyle: Int? = null,
        enableCrop: Boolean = false,
        enableCompress: Boolean = false,
        maxSelectPicNum: Int = 9,
        maxSelectVideoNum: Int = 1,
        openOriginal: Boolean = false,
        useSystemVideoPlayer: Boolean = false,
        setGridItemSelectAnimListener: Boolean = false,
        isMaxSelectEnabledMask: Boolean = false,
        isDirectReturnSingle: Boolean = false,
        recordVideoMaxSecond: Int = 10,
        recordVideoMinSecond: Int = 0,
    ): PictureSelectionModel {
        // 进入相册
        return PictureSelector.create(context)
            .openGallery(chooseMode)
            .setLanguage(language)
            .setCameraInterceptListener(getCustomCameraEvent(isCustomCameraEvent))   // 自定义相机
            .setImageSpanCount(imageSpanCount) // 相册列表每行显示个数
            .isDisplayCamera(isDisplayCamera)  //是否打开摄像头按钮
            .isOpenClickSound(isOpenClickSound) // 是否打开点击语音
            .isWithSelectVideoImage(isWithSelectVideoImage) // 是否可以同时选择图片和视频
            .setSelectorUIStyle(setSelectorUIStyle(uiStyle))
            .setImageEngine(GlideEngineKt.createGlideEngine())
            .setSelectionMode(if (multipleSelectionMode) SelectModeConfig.MULTIPLE else SelectModeConfig.SINGLE)
            .setCompressEngine(getCompressFileEngine(enableCompress))
            .setCropEngine(getCropFileEngine(enableCrop))
            .setSkipCropMimeType(*getNotSupportCrop())
            .setSelectLimitTipsListener(MeOnSelectLimitTipsListener())
//                    .setAddBitmapWatermarkListener(getAddBitmapWatermarkListener())
            .setVideoThumbnailListener(getVideoThumbnailEventListener())
            .setCustomLoadingListener(getCustomLoadingListener())
            .isOriginalControl(openOriginal)  // 是否开启原图功能
//                    .setSandboxFileEngine(MeSandboxFileEngine())
            .isDisplayTimeAxis(true)  // 显示资源创建时间线
            .isPageStrategy(true) // 是否开启分页模式
            // 预览点击全屏效果
            .isPreviewFullScreenMode(true)
            .isPreviewZoomEffect(isPreviewZoomEffect) //预览缩放效果模式
            .isPreviewImage(true)
            .isPreviewVideo(true)
            .isPreviewAudio(true)
            // 视频支持暂停与播放
            .isVideoPauseResumePlay(true)
            .isAutoVideoPlay(false)
            .isLoopAutoVideoPlay(true)
            // 使用系统播放器
            .isUseSystemVideoPlayer(false)
            .isPageSyncAlbumCount(useSystemVideoPlayer) // 是否以带过滤条件的分页方式同步最新专辑下的资源数
            .setGridItemSelectAnimListener(if (setGridItemSelectAnimListener) OnGridItemSelectAnimListener { view, isSelected ->
                val set = AnimatorSet()
                set.playTogether(
                    ObjectAnimator.ofFloat(
                        view,
                        "scaleX",
                        if (isSelected) 1f else 1.12f,
                        if (isSelected) 1.12f else 1.0f
                    ),
                    ObjectAnimator.ofFloat(
                        view,
                        "scaleY",
                        if (isSelected) 1f else 1.12f,
                        if (isSelected) 1.12f else 1.0f
                    )
                )
                set.duration = 350
                set.start()
            } else null)
            .setSelectAnimListener(if (setGridItemSelectAnimListener) OnSelectAnimListener { view ->
                val animation = AnimationUtils.loadAnimation(context, R.anim.ps_anim_modal_in)
                view.startAnimation(animation)
                animation.duration
            } else null)
            .isMaxSelectEnabledMask(isMaxSelectEnabledMask)  // 当达到最大选择数时，列表是否启用掩码效果(蒙层)
            .isDirectReturnSingle(isDirectReturnSingle)  // 单选模式直接返回
            .setMaxSelectNum(maxSelectPicNum)  // 图片选择器的最大选择
            .setMaxVideoSelectNum(maxSelectVideoNum)  // 选择最大视频数
            .isGif(true)  // 是否打开gif
            .setRecordVideoMaxSecond(recordVideoMaxSecond)  // 视频录制最大时长
            .setRecordVideoMinSecond(recordVideoMinSecond)  // 视频录制最小时长
//            .setSelectedData(mAdapter.getData())  // 选择选定的图片集
    }

    /**
     * @param chooseMode     模式 0: all 1:image 2:video 3:音频
     * @param enableCrop     是否裁剪
     * @param enableCompress 是否压缩
     * @param recordVideoMaxSecond   视频录制最大时长
     * @param recordVideoMinSecond   视频录制最小时长
     */
    fun openCamera(
        context: Context,
//        language: Int = LanguageConfig.CHINESE,
        language: Int = languageConfig,
        chooseMode: Int = chooseModeAll,
        enableCrop: Boolean = false,
        enableCompress: Boolean = false,
        openOriginal: Boolean = false,
        recordVideoMaxSecond: Int = 10,
        recordVideoMinSecond: Int = 0,
    ): PictureSelectionCameraModel {
        // 进入相册
        return PictureSelector.create(context)
            .openCamera(chooseMode)
            .setLanguage(language)
            .setCompressEngine(getCompressFileEngine(enableCompress))
            .setCropEngine(getCropFileEngine(enableCrop))
            .setSelectLimitTipsListener(MeOnSelectLimitTipsListener())
//                    .setAddBitmapWatermarkListener(getAddBitmapWatermarkListener())
            .setVideoThumbnailListener(getVideoThumbnailEventListener())
            .setCustomLoadingListener(getCustomLoadingListener())
            .isOriginalControl(openOriginal)  // 是否开启原图功能
            .setRecordVideoMaxSecond(recordVideoMaxSecond)  // 视频录制最大时长
            .setRecordVideoMinSecond(recordVideoMinSecond)  // 视频录制最小时长
    }

    private fun getCustomCameraEvent(isCustomCameraEvent: Boolean): OnCameraInterceptListener? {
        return if (isCustomCameraEvent) {
            MeOnCameraInterceptListener()
        } else {
            null
        }
    }

    /**
     * 自定义相机
     */
    private class MeOnCameraInterceptListener : OnCameraInterceptListener {
        override fun openCamera(fragment: Fragment, cameraMode: Int, requestCode: Int) {
            val camera = SimpleCameraX.of()
            camera.isAutoRotation(true)
            camera.setCameraMode(cameraMode)
            camera.setVideoFrameRate(25)
            camera.setVideoBitRate(3 * 1024 * 1024)
            camera.setRecordVideoMinSecond(1)
            camera.setRecordVideoMaxSecond(15)
            camera.isDisplayRecordChangeTime(true)
            camera.isManualFocusCameraPreview(true)   // 是否手动点击对焦
            camera.isZoomCameraPreview(true)           // 是否可缩放相机
//            camera.setOutputPathDir(getSandboxCameraOutputPath())            // 拍照自定义输出路径
//            camera.setPermissionDeniedListener(getSimpleXPermissionDeniedListener())   // 没有权限
//            camera.setPermissionDescriptionListener(getSimpleXPermissionDescriptionListener())
            camera.setImageEngine { context, url, imageView ->
                Glide.with(context).load(url).into(imageView)
            }
            camera.start(fragment.requireActivity(), fragment, requestCode)
        }
    }

    /**
     * 自定义loading
     */
    private fun getCustomLoadingListener(): OnCustomLoadingListener {
        return OnCustomLoadingListener { context -> PictureLoadingDialog(context) }
    }

    private fun forSystemResult(model: PictureSelectionSystemModel) {
        model.forSystemResult(MeOnResultCallbackListener())
    }

    /**
     * 处理视频缩略图
     */
    private fun getVideoThumbnailEventListener(): OnVideoThumbnailEventListener {
        return MeOnVideoThumbnailEventListener(getCustomVideoThumbnailDir())
    }

    /**
     * 处理视频缩略图
     */
    private class MeOnVideoThumbnailEventListener(private val targetPath: String) :
        OnVideoThumbnailEventListener {
        override fun onVideoThumbnail(
            context: Context,
            videoPath: String,
            call: OnKeyValueResultCallbackListener
        ) {
            Glide.with(context).asBitmap().sizeMultiplier(0.6f).load(videoPath)
                .into(object : CustomTarget<Bitmap?>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap?>?
                    ) {
                        val stream = ByteArrayOutputStream()
                        resource.compress(Bitmap.CompressFormat.JPEG, 60, stream)
                        var fos: FileOutputStream? = null
                        var result: String? = null
                        try {
                            val targetFile = File(
                                targetPath,
                                "thumbnails_" + System.currentTimeMillis() + ".jpg"
                            )
                            fos = FileOutputStream(targetFile)
                            fos.write(stream.toByteArray())
                            fos.flush()
                            result = targetFile.absolutePath
                        } catch (e: IOException) {
                            e.printStackTrace()
                        } finally {
                            PictureFileUtils.close(fos)
                            PictureFileUtils.close(stream)
                        }
                        call.onCallback(videoPath, result)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        call.onCallback(videoPath, "")
                    }
                })
        }
    }

    /**
     * 创建自定义输出目录
     *
     * @return
     */
    private fun getCustomVideoThumbnailDir(): String {
        val externalFilesDir: File? = BaseApp.appContext.getExternalFilesDir("")
        val customFile = File(externalFilesDir?.absolutePath ?: "", "Thumbnail")
        if (!customFile.exists()) {
            customFile.mkdirs()
        }
        return customFile.absolutePath + File.separator
    }

    /**
     * 拦截自定义提示
     */
    private class MeOnSelectLimitTipsListener : OnSelectLimitTipsListener {
        override fun onSelectLimitTips(
            context: Context,
            media: LocalMedia?,
            config: SelectorConfig,
            limitType: Int
        ): Boolean {
            return when (limitType) {
                SelectLimitType.SELECT_MIN_SELECT_LIMIT -> {
                    ToastUtils.showToast(context, "图片最少不能低于" + config.minSelectNum + "张")
                    true
                }

                SelectLimitType.SELECT_MIN_VIDEO_SELECT_LIMIT -> {
                    ToastUtils.showToast(
                        context,
                        "视频最少不能低于" + config.minVideoSelectNum + "个"
                    )
                    true
                }

                SelectLimitType.SELECT_MIN_AUDIO_SELECT_LIMIT -> {
                    ToastUtils.showToast(
                        context,
                        "音频最少不能低于" + config.minAudioSelectNum + "个"
                    )
                    true
                }

                else -> false
            }
        }
    }

    /**
     * 裁剪引擎
     */
    private fun getCropFileEngine(enableCrop: Boolean): ImageFileCropEngine? {
        return if (enableCrop) {
            ImageFileCropEngine()
        } else {
            null
        }
    }

    /**
     * 压缩引擎
     */
    private fun getCompressFileEngine(enableCompress: Boolean): ImageFileCompressEngine? {
        return if (enableCompress) {
            ImageFileCompressEngine()
        } else {
            null
        }
    }

    /**
     * 自定义裁剪
     */
    private class ImageFileCropEngine : CropFileEngine {
        override fun onStartCrop(
            fragment: Fragment,
            srcUri: Uri,
            destinationUri: Uri,
            dataSource: java.util.ArrayList<String>,
            requestCode: Int
        ) {
            val options: UCrop.Options = buildOptions()
            val uCrop = UCrop.of(srcUri, destinationUri, dataSource)
            uCrop.withOptions(options)
            uCrop.setImageEngine(object : UCropImageEngine {
                override fun loadImage(context: Context, url: String, imageView: ImageView) {
                    if (!ImageLoaderUtilsKt.assertValidRequest(context)) {
                        return
                    }
                    Glide.with(context).load(url).override(180, 180).into(imageView)
                }

                override fun loadImage(
                    context: Context,
                    url: Uri,
                    maxWidth: Int,
                    maxHeight: Int,
                    call: UCropImageEngine.OnCallbackListener<Bitmap>
                ) {
                    Glide.with(context).asBitmap().load(url).override(maxWidth, maxHeight)
                        .into(object : CustomTarget<Bitmap?>() {
                            override fun onResourceReady(
                                resource: Bitmap,
                                transition: Transition<in Bitmap?>?
                            ) {
                                call.onCall(resource)
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {
                                call.onCall(null)
                            }
                        })
                }
            })
            uCrop.start(fragment.requireActivity(), fragment, requestCode)
        }
    }

    /**
     * 配制UCrop，可根据需求自我扩展
     */
    private fun buildOptions(): UCrop.Options {
        val options = UCrop.Options()
        options.setHideBottomControls(true)
        options.setFreeStyleCropEnabled(true)
        options.setShowCropFrame(true)
        options.setShowCropGrid(true)
        options.setCircleDimmedLayer(true)
        options.withAspectRatio((-1).toFloat(), (-1).toFloat())
        options.setCropOutputPathDir(getSandboxPath())
        options.isCropDragSmoothToCenter(false)
        options.setSkipCropMimeType(*getNotSupportCrop())
        options.isForbidCropGifWebp(true)  // 禁止裁剪gif
        options.isForbidSkipMultipleCrop(true)
        options.setMaxScaleMultiplier(100f)
        if (selectorStyle.selectMainStyle.statusBarColor != 0) {
            val mainStyle = selectorStyle.selectMainStyle
            val isDarkStatusBarBlack = mainStyle.isDarkStatusBarBlack
            val statusBarColor = mainStyle.statusBarColor
            options.isDarkStatusBarBlack(isDarkStatusBarBlack)
            if (StyleUtils.checkStyleValidity(statusBarColor)) {
                options.setStatusBarColor(statusBarColor)
                options.setToolbarColor(statusBarColor)
            } else {
                options.setStatusBarColor(
                    ContextCompat.getColor(
                        BaseApp.appContext,
                        R.color.ps_color_grey
                    )
                )
                options.setToolbarColor(
                    ContextCompat.getColor(
                        BaseApp.appContext,
                        R.color.ps_color_grey
                    )
                )
            }
            val titleBarStyle = selectorStyle.titleBarStyle
            if (StyleUtils.checkStyleValidity(titleBarStyle.titleTextColor)) {
                options.setToolbarWidgetColor(titleBarStyle.titleTextColor)
            } else {
                options.setToolbarWidgetColor(
                    ContextCompat.getColor(
                        BaseApp.appContext,
                        R.color.ps_color_white
                    )
                )
            }
        } else {
            options.setStatusBarColor(
                ContextCompat.getColor(
                    BaseApp.appContext,
                    R.color.ps_color_grey
                )
            )
            options.setToolbarColor(
                ContextCompat.getColor(
                    BaseApp.appContext,
                    R.color.ps_color_grey
                )
            )
            options.setToolbarWidgetColor(
                ContextCompat.getColor(
                    BaseApp.appContext,
                    R.color.ps_color_white
                )
            )
        }
        return options
    }

    /**
     * 跳过裁剪gif图
     */
    private fun getNotSupportCrop(): Array<String> {
        return arrayOf(PictureMimeType.ofGIF(), PictureMimeType.ofWEBP())
    }

    /**
     * 创建自定义输出目录
     *
     * @return
     */
    private fun getSandboxPath(): String {
        val externalFilesDir: File? = BaseApp.appContext.getExternalFilesDir("")
        val customFile = File(externalFilesDir?.absolutePath ?: "", "Sandbox")
        if (!customFile.exists()) {
            customFile.mkdirs()
        }
        return customFile.absolutePath + File.separator
    }

    /**
     * 自定义压缩
     */
    private class ImageFileCompressEngine : CompressFileEngine {
        override fun onStartCompress(
            context: Context,
            source: ArrayList<Uri>,
            call: OnKeyValueResultCallbackListener
        ) {
            Luban.with(context).load(source).ignoreBy(100).setRenameListener { filePath ->
                val indexOf = filePath.lastIndexOf(".")
                val postfix = if (indexOf != -1) filePath.substring(indexOf) else ".jpg"
                DateUtils.getCreateFileName("CMP_") + postfix
            }.filter { path ->
                if (PictureMimeType.isUrlHasImage(path) && !PictureMimeType.isHasHttp(path)) {
                    true
                } else !PictureMimeType.isUrlHasGif(path)
            }.setCompressListener(object : OnNewCompressListener {
                override fun onStart() {}
                override fun onSuccess(source: String, compressFile: File) {
                    call.onCallback(source, compressFile.absolutePath)
                }

                override fun onError(source: String, e: Throwable) {
                    call.onCallback(source, null)
                }
            }).launch()
        }
    }

    private fun setSelectorUIStyleWithDayNight(): PictureSelectorStyle = if (getNightMode()) {
        "Night".showShortToast()
        setSelectorUIStyle(DEFAULT_STYLE)
    } else {
        "Light".showShortToast()
        setSelectorUIStyle(WHITE_STYLE)
    }

    private fun setSelectorUIStyle(style: Int?): PictureSelectorStyle {
        var setStyle = PictureSelectorStyle()
        when (style) {
            DEFAULT_STYLE -> {
                setStyle = PictureSelectorStyle()
            }

            WHITE_STYLE -> {
                setStyle = setWhiteStyle()
            }

            NUM_STYLE -> {
                setStyle = setNumStyle()
            }

            CHAT_STYLE -> {
                setStyle = setChatStyle()
            }

            null -> {
                setStyle = setSelectorUIStyleWithDayNight()
            }
        }
        return setStyle
    }

    private fun setChatStyle(): PictureSelectorStyle {
        // 主体风格
        val numberSelectMainStyle = SelectMainStyle()
        numberSelectMainStyle.isSelectNumberStyle = true
        numberSelectMainStyle.isPreviewSelectNumberStyle = false
        numberSelectMainStyle.isPreviewDisplaySelectGallery = true
        numberSelectMainStyle.selectBackground = R.drawable.ps_default_num_selector
        numberSelectMainStyle.previewSelectBackground = R.drawable.ps_preview_checkbox_selector
        numberSelectMainStyle.selectNormalBackgroundResources =
            R.drawable.ps_select_complete_normal_bg
        numberSelectMainStyle.selectNormalTextColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_53575e)
        numberSelectMainStyle.setSelectNormalText(R.string.ps_send)
        numberSelectMainStyle.adapterPreviewGalleryBackgroundResource =
            R.drawable.ps_preview_gallery_bg
        numberSelectMainStyle.adapterPreviewGalleryItemSize =
            DensityUtil.dip2px(BaseApp.appContext, 52f)
        numberSelectMainStyle.setPreviewSelectText(R.string.ps_select)
        numberSelectMainStyle.previewSelectTextSize = 14
        numberSelectMainStyle.previewSelectTextColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_white)
        numberSelectMainStyle.previewSelectMarginRight =
            DensityUtil.dip2px(BaseApp.appContext, 6f)
        numberSelectMainStyle.selectBackgroundResources = R.drawable.ps_select_complete_bg
        numberSelectMainStyle.setSelectText(R.string.ps_send_num)
        numberSelectMainStyle.selectTextColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_white)
        numberSelectMainStyle.mainListBackgroundColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_black)
        numberSelectMainStyle.isCompleteSelectRelativeTop = true
        numberSelectMainStyle.isPreviewSelectRelativeBottom = true
        numberSelectMainStyle.isAdapterItemIncludeEdge = false

        // 头部TitleBar 风格
        val numberTitleBarStyle = TitleBarStyle()
        numberTitleBarStyle.isHideCancelButton = true
        numberTitleBarStyle.isAlbumTitleRelativeLeft = true
        numberTitleBarStyle.titleAlbumBackgroundResource = R.drawable.ps_demo_only_album_bg
        numberTitleBarStyle.titleDrawableRightResource = R.drawable.ps_ic_grey_arrow
        numberTitleBarStyle.previewTitleLeftBackResource = R.drawable.ps_ic_normal_back

        // 底部NavBar 风格
        val numberBottomNavBarStyle = BottomNavBarStyle()
        numberBottomNavBarStyle.bottomPreviewNarBarBackgroundColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_half_grey)
        numberBottomNavBarStyle.setBottomPreviewNormalText(R.string.ps_preview)
        numberBottomNavBarStyle.bottomPreviewNormalTextColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_9b)
        numberBottomNavBarStyle.bottomPreviewNormalTextSize = 16
        numberBottomNavBarStyle.isCompleteCountTips = false
        numberBottomNavBarStyle.setBottomPreviewSelectText(R.string.ps_preview_num)
        numberBottomNavBarStyle.bottomPreviewSelectTextColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_white)

        selectorStyle = PictureSelectorStyle()
        selectorStyle.titleBarStyle = numberTitleBarStyle
        selectorStyle.bottomBarStyle = numberBottomNavBarStyle
        selectorStyle.selectMainStyle = numberSelectMainStyle

        return selectorStyle
    }

    private fun setNumStyle(): PictureSelectorStyle {
        val blueTitleBarStyle = TitleBarStyle()
        blueTitleBarStyle.titleBackgroundColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_blue)

        val numberBlueBottomNavBarStyle = BottomNavBarStyle()
        numberBlueBottomNavBarStyle.bottomPreviewNormalTextColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_9b)
        numberBlueBottomNavBarStyle.bottomPreviewSelectTextColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_blue)
        numberBlueBottomNavBarStyle.bottomNarBarBackgroundColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_white)
        numberBlueBottomNavBarStyle.bottomSelectNumResources = R.drawable.ps_demo_blue_num_selected
        numberBlueBottomNavBarStyle.bottomEditorTextColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_53575e)
        numberBlueBottomNavBarStyle.bottomOriginalTextColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_53575e)


        val numberBlueSelectMainStyle = SelectMainStyle()
        numberBlueSelectMainStyle.statusBarColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_blue)
        numberBlueSelectMainStyle.isSelectNumberStyle = true
        numberBlueSelectMainStyle.isPreviewSelectNumberStyle = true
        numberBlueSelectMainStyle.selectBackground = R.drawable.ps_demo_blue_num_selector
        numberBlueSelectMainStyle.mainListBackgroundColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_white)
        numberBlueSelectMainStyle.previewSelectBackground =
            R.drawable.ps_demo_preview_blue_num_selector

        numberBlueSelectMainStyle.selectNormalTextColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_9b)
        numberBlueSelectMainStyle.selectTextColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_blue)
        numberBlueSelectMainStyle.setSelectText(R.string.ps_completed)

        selectorStyle = PictureSelectorStyle()
        selectorStyle.titleBarStyle = blueTitleBarStyle
        selectorStyle.bottomBarStyle = numberBlueBottomNavBarStyle
        selectorStyle.selectMainStyle = numberBlueSelectMainStyle

        return selectorStyle
    }

    private fun setWhiteStyle(): PictureSelectorStyle {
        val whiteTitleBarStyle = TitleBarStyle()
        whiteTitleBarStyle.titleBackgroundColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_white)
        whiteTitleBarStyle.titleDrawableRightResource = R.drawable.ic_orange_arrow_down
        whiteTitleBarStyle.titleLeftBackResource = R.drawable.ps_ic_black_back
        whiteTitleBarStyle.titleTextColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_black)
        whiteTitleBarStyle.titleCancelTextColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_53575e)
        whiteTitleBarStyle.isDisplayTitleBarLine = true

        val whiteBottomNavBarStyle = BottomNavBarStyle()
        whiteBottomNavBarStyle.bottomNarBarBackgroundColor = Color.parseColor("#EEEEEE")
        whiteBottomNavBarStyle.bottomPreviewSelectTextColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_53575e)

        whiteBottomNavBarStyle.bottomPreviewNormalTextColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_9b)
        whiteBottomNavBarStyle.bottomPreviewSelectTextColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_fa632d)
        whiteBottomNavBarStyle.isCompleteCountTips = false
        whiteBottomNavBarStyle.bottomEditorTextColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_53575e)
        whiteBottomNavBarStyle.bottomOriginalTextColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_53575e)

        val selectMainStyle = SelectMainStyle()
        selectMainStyle.statusBarColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_white)
        selectMainStyle.isDarkStatusBarBlack = true
        selectMainStyle.selectNormalTextColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_9b)
        selectMainStyle.selectTextColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_fa632d)
        selectMainStyle.previewSelectBackground = R.drawable.ps_demo_white_preview_selector
        selectMainStyle.selectBackground = R.drawable.ps_checkbox_selector
        selectMainStyle.setSelectText(R.string.ps_done_front_num)
        selectMainStyle.mainListBackgroundColor =
            ContextCompat.getColor(BaseApp.appContext, R.color.ps_color_white)

        selectorStyle = PictureSelectorStyle()
        selectorStyle.titleBarStyle = whiteTitleBarStyle
        selectorStyle.bottomBarStyle = whiteBottomNavBarStyle
        selectorStyle.selectMainStyle = selectMainStyle

        return selectorStyle
    }

    /**
     * 选择结果
     */
    private class MeOnResultCallbackListener : OnResultCallbackListener<LocalMedia> {
        override fun onResult(result: ArrayList<LocalMedia>) {
            analyticalSelectResults(result)
        }

        override fun onCancel() {
            LogUtils.d(TAG, "PictureSelector Cancel")
        }
    }


    private fun analyticalSelectResults(result: ArrayList<LocalMedia>) {
        for (media in result) {
            if (media.width == 0 || media.height == 0) {
                if (PictureMimeType.isHasImage(media.mimeType)) {
                    val imageExtraInfo = MediaUtils.getImageSize(BaseApp.appContext, media.path)
                    media.width = imageExtraInfo.width
                    media.height = imageExtraInfo.height
                } else if (PictureMimeType.isHasVideo(media.mimeType)) {
                    val videoExtraInfo = MediaUtils.getVideoSize(BaseApp.appContext, media.path)
                    media.width = videoExtraInfo.width
                    media.height = videoExtraInfo.height
                }
            }
            LogUtils.d(tag = TAG, msg = "文件名: " + media.fileName)
            LogUtils.d(tag = TAG, msg = "是否压缩:" + media.isCompressed)
            LogUtils.d(tag = TAG, msg = "压缩:" + media.compressPath)
            LogUtils.d(tag = TAG, msg = "初始路径:" + media.path)
            LogUtils.d(tag = TAG, msg = "绝对路径:" + media.realPath)
            LogUtils.d(tag = TAG, msg = "是否裁剪:" + media.isCut)
            LogUtils.d(tag = TAG, msg = "裁剪路径:" + media.cutPath)
            LogUtils.d(tag = TAG, msg = "是否开启原图:" + media.isOriginal)
            LogUtils.d(tag = TAG, msg = "原图路径:" + media.originalPath)
            LogUtils.d(tag = TAG, msg = "沙盒路径:" + media.sandboxPath)
            LogUtils.d(tag = TAG, msg = "水印路径:" + media.watermarkPath)
            LogUtils.d(tag = TAG, msg = "视频缩略图:" + media.videoThumbnailPath)
            LogUtils.d(tag = TAG, msg = "原始宽高: " + media.width + "x" + media.height)
            LogUtils.d(
                tag = "",
                msg = "裁剪宽高: " + media.cropImageWidth + "x" + media.cropImageHeight
            )
            LogUtils.d(
                tag = "",
                msg = "文件大小: " + PictureFileUtils.formatAccurateUnitFileSize(media.size)
            )
            LogUtils.d(tag = "", msg = "文件时长: " + media.duration)
        }
    }
}