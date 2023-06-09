package com.liang.newbaseproject.pictureSelector

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.liang.module_base.base.BaseViewModel
import com.liang.module_base.utils.LogUtils
import com.liang.newbaseproject.room.MediaBean
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnExternalPreviewEventListener
import com.luck.picture.lib.interfaces.OnResultCallbackListener

class PictureSelectorViewModel(
    application: Application,
) : BaseViewModel(application) {

    // 数据列表
//    var galleryList: ArrayList<LocalMedia> = arrayListOf()

    var galleryList: List<MediaBean> = arrayListOf()
    var gallerySelectedList: ArrayList<LocalMedia> = arrayListOf()

//    private val _mediaListLiveData = MutableLiveData<ArrayList<LocalMedia>>()
//    val mediaListLiveData: LiveData<ArrayList<LocalMedia>>
//        get() = _mediaListLiveData

    private val _mediaListLiveData = MutableLiveData<List<MediaBean>>()
    val mediaListLiveData: LiveData<List<MediaBean>>
        get() = _mediaListLiveData

    /**
     * 回调侦听器
     */
    fun onResultCallbackListener() = object : OnResultCallbackListener<LocalMedia> {
        override fun onResult(result: ArrayList<LocalMedia>) {
//            _mediaListLiveData.value = result
            gallerySelectedList.clear()
            gallerySelectedList.addAll(result)

            val dataList: ArrayList<MediaBean> = arrayListOf()
            result.forEach {
                dataList.add(MediaBean(it))
                _mediaListLiveData.value = dataList
            }
        }

        override fun onCancel() {
            LogUtils.d(msg = "onSelectFinish PictureSelector Cancel")
        }
    }


}