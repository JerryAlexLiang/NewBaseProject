package com.liang.newbaseproject.room

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

/**
 * - Time: 2023/6/9/0009 on 11:28
 * - User: Jerry
 * - Description: 创建存储库
 * - 1、将DAO声明为构造函数中的私有属性。传入DAO而不是整个数据库，因为您只需要访问DAO.
 * - 2、DAO会被传递到存储库Repository构造函数中，而非整个数据库中。
 * DAO包含数据库的所有读取/写入方法，因此它只需要访问 DAO，无需向存储库公开整个数据库。
 * - 3、
 */
class RoomRepository(private val mediaBeanDao: MediaBeanDao) {

    /**
     * 通过Flow观察数据库的变化
     * - 1、MediaBean列表具有公开属性。它通过从Room获取Flow-MediaBean列表来进行初始化；
     * 之所以能够实现该操作，是因为在“观察数据库变化”步骤中定义 getAllMediaBeanByFlow方法以返回Flow的方式。
     * - 2、Room在一个单独的线程上执行所有查询。当数据发生变化时，Observed Flow将通知观察者。
     */
    val allMediaBeansFlow: Flow<MutableList<MediaBean>> = mediaBeanDao.getAllMediaBeanByFlow()

    /**
     * 通过LiveData观察数据库的变化
     */
    val allMediaBeansLiveData: LiveData<MutableList<MediaBean>> = mediaBeanDao.getAllMediaBeanByLiveData()
    /**
     * - 1、suspend修饰符会告知编译器需要从协程或其他挂起函数进行调用
     * - 2、Room在主线程之外执行挂起查询
     */
    suspend fun getAllMediaBean(): MutableList<MediaBean> {
        return mediaBeanDao.getAllMediaBean()
    }

    /**
     * 默认情况下，Room在主线程外运行挂起查询，因此，我们不需要实现任何其他东西来确保我们不做长时间运行的数据库工作
     */
    suspend fun insertMediaBean(mediaBean: MediaBean) {
        mediaBeanDao.insertMediaBean(mediaBean)
    }

    suspend fun insertMediaBeans(mediaBeanList: MutableList<MediaBean>) {
        mediaBeanDao.insertMediaBeans(mediaBeanList)
    }

    suspend fun deleteMediaBean(mediaBean: MediaBean) {
        mediaBeanDao.deleteMediaBean(mediaBean)
    }

    suspend fun deleteMediaBeans(mediaBeanList: MutableList<MediaBean>) {
        mediaBeanDao.deleteMediaBeans(mediaBeanList)
    }

    suspend fun deleteAllMediaBean() {
        mediaBeanDao.deleteAllMediaBean()
    }

}
