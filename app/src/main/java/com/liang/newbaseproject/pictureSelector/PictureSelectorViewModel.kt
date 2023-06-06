package com.liang.newbaseproject.pictureSelector

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.liang.module_base.base.BaseViewModel
import com.liang.module_base.utils.LogUtils
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener

class PictureSelectorViewModel(
    application: Application,
) : BaseViewModel(application) {

    // 数据列表
    var galleryList: ArrayList<LocalMedia> = arrayListOf()

    private val _mediaListLiveData = MutableLiveData<ArrayList<LocalMedia>>()
    val mediaListLiveData: LiveData<ArrayList<LocalMedia>>
        get() = _mediaListLiveData

    fun onResultCallbackListener() = object : OnResultCallbackListener<LocalMedia> {
        override fun onResult(result: ArrayList<LocalMedia>) {
            _mediaListLiveData.value = result
        }

        override fun onCancel() {
            LogUtils.d(msg = "onSelectFinish PictureSelector Cancel")
        }

    }
}