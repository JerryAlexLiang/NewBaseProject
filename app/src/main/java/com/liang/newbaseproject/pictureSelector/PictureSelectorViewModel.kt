package com.liang.newbaseproject.pictureSelector

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.liang.module_base.base.BaseViewModel
import com.liang.module_base.utils.LogUtils
import com.liang.newbaseproject.room.MediaBean
import com.liang.newbaseproject.room.RoomRepository
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PictureSelectorViewModel(
    application: Application,
    private val roomRepository: RoomRepository
) : BaseViewModel(application) {

    /**
     * 数据列表-类型MediaBean
     */
    var galleryList: MutableList<MediaBean> = arrayListOf()

    /**
     * 已选列表-类型LocalMedia
     */
    var gallerySelectedList: ArrayList<LocalMedia> = arrayListOf()

    /**
     * 图片选择回调
     */
    private val _mediaListLiveData = MutableLiveData<MutableList<MediaBean>>()

    var mediaListLiveData: LiveData<MutableList<MediaBean>> = _mediaListLiveData

    init {

        // 获取所有列表-way1 获取本地化存储的MediaBean
        viewModelScope.launch(Dispatchers.IO) {
            getAllMediaBean()
        }

        // 取所有列表-way2-通过LiveData观察数据库的变化
//        getAllMediaBeanByLiveData()

        // 取所有列表-way3-通过Flow观察数据库的变化
//        getAllMediaBeanByFlow()
    }

    /**
     * 图片选择回调侦听器
     */
    fun onResultCallbackListener() = object : OnResultCallbackListener<LocalMedia> {
        override fun onResult(result: ArrayList<LocalMedia>) {
//            _mediaListLiveData.value = result
            gallerySelectedList.clear()
            gallerySelectedList.addAll(result)

            val dataList: MutableList<MediaBean> = arrayListOf()
            result.forEach {
//                dataList.add(MediaBean(id = it.id, localMedia = it))
//                dataList.add(MediaBean(id = it.id, fileName = it.fileName, localMedia = it))
                dataList.add(
                    MediaBean(
                        id = it.id,
                        fileName = it.fileName,
                        dateAddedTime = it.dateAddedTime,
                        localMedia = it
                    )
                )
                _mediaListLiveData.value = dataList
                // 删除全部本地化存储
                deleteAllData()
                // 本地化存储选择的图片列表
                saveAllPictures(dataList)
            }
        }

        override fun onCancel() {
            LogUtils.d(msg = "onSelectFinish PictureSelector Cancel")
        }
    }

    /**
     * 删除所有数据
     */
    private fun deleteAllData() {
        viewModelScope.launch {
            deleteAllMediaBean()
        }
    }

    /**
     * 保存所有选中图片
     */
    fun saveAllPictures(dataList: MutableList<MediaBean>) {
        viewModelScope.launch(Dispatchers.Default) {
            //子线程IO处理数据库耗时操作
            insertMediaBeans(dataList)
        }
    }

    /**
     * 启动一个新的协程以非阻塞的方式插入数据
     */
    suspend fun insertMediaBeans(mediaBeanList: MutableList<MediaBean>) {
        roomRepository.insertMediaBeans(mediaBeanList)
    }

    /**
     * 删除单个MediaBean
     */
    suspend fun deleteMediaBean(mediaBean: MediaBean) {
        roomRepository.deleteMediaBean(mediaBean)
    }

    /**
     * 删除多个MediaBeanList
     */
    suspend fun deleteMediaBeans(mediaBeanList: MutableList<MediaBean>) {
        roomRepository.deleteMediaBeans(mediaBeanList)
    }

    /**
     * 删除所有表
     */
    suspend fun deleteAllMediaBean() {
        roomRepository.deleteAllMediaBean()
    }

    /**
     * 获取所有列表-way1
     */
//    suspend fun getAllMediaBean(): List<MediaBean> {
//        return roomRepository.getAllMediaBean()
//    }
    suspend fun getAllMediaBean() {
        val allMediaBean = roomRepository.getAllMediaBean()
//        if (allMediaBean != null && allMediaBean.size > 0) {
        if (allMediaBean.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.Main) {
                _mediaListLiveData.value = allMediaBean
                gallerySelectedList.clear()
                allMediaBean.forEach {
                    gallerySelectedList.add(it.localMedia)
                }
            }
        }
    }

    /**
     * 取所有列表-way2-通过LiveData观察数据库的变化
     */
    fun getAllMediaBeanByLiveData() {
        mediaListLiveData = roomRepository.allMediaBeansLiveData
    }

    /**
     * 取所有列表-way3-通过Flow观察数据库的变化
     * - Flow通过异步操作（例如网络请求、数据库调用或其他异步代码），一次生成一个值（而不是一次生成所有值）。它的 API 支持协程，因此也可以使用协程来完善 Flow
     */
    fun getAllMediaBeanByFlow() {
        val allMediaBeansFlow = roomRepository.allMediaBeansFlow
        mediaListLiveData = allMediaBeansFlow.asLiveData()
    }
}