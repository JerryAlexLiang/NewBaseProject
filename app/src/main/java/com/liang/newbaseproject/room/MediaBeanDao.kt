package com.liang.newbaseproject.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


/**
 * - Time: 2023/6/9/0009 on 10:27
 * - User: Jerry
 * - Description: Room-Dao数据访问对象，DAO 必须是一个接口或抽象类。
 * - 1、Room支持Kotlin协程，可使用suspend修饰符对查询进行注解，然后从协程或其他挂起函数对其进行调用。
 * - 2、Room使用DAO向其数据库发出查询请求。
 */
@Dao
interface MediaBeanDao {

    /**
     * suspend fun insertMediaBean 声明挂起函数以插入一个MediaBean对象
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMediaBean(mediaBean: MediaBean)

    /**
     * onConflict = OnConflictStrategy.IGNORE：所选onConflict策略将忽略与列表中的现有Bean完全相同的新Bean.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMediaBeans(vararg mediaBean: MediaBean)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMediaBeans(mediaBeanList: MutableList<MediaBean>)

    @Delete
    suspend fun deleteMediaBean(mediaBean: MediaBean)

    @Delete
    suspend fun deleteMediaBeans(mediaBeanList: MutableList<MediaBean>)

    /**
     * 没有便于删除多个实体的注解，因此实体中要带有通用@Query注解
     */
    @Query("DELETE FROM picture_media_table")
    suspend fun deleteAllMediaBean()

    /**
     * - @Query("SELECT * FROM picture_media_table")：
     * - @Query 要求将SQL查询作为字符串参数提供给注解，以执行复杂的读取查询及其他操作
     */
    @Query("SELECT * FROM picture_media_table")
    suspend fun getAllMediaBean(): MutableList<MediaBean>

    /**
     * 观察数据库的变化
     */
    @Query("SELECT * FROM picture_media_table")
    fun getAllMediaBeanByLiveData(): LiveData<MutableList<MediaBean>>

    /**
     * 观察数据库的变化
     * - 1、为了观察数据变化情况，使用kotlinx-coroutines中的 Flow。在方法说明中使用Flow类型的返回值；当数据库更新时，Room会生成更新Flow所需的所有代码。
     * - 2、Flow 是值的异步序列
     * - Flow 通过异步操作（例如网络请求、数据库调用或其他异步代码），一次生成一个值（而不是一次生成所有值）。它的API支持协程，因此也可以使用协程来完善 Flow。
     * - 3、在 ViewModel中将Flow转换为LiveData.
     * - 4、为避免界面性能不佳，默认情况下，Room不允许在主线程上发出查询请求。当 Room查询返回Flow时，这些查询会在后台线程上自动异步运行。
     */
    @Query("select * from picture_media_table")
    fun getAllMediaBeanByFlow(): Flow<MutableList<MediaBean>>

//    @Query("select * from MediaBean where id=:id")
//    suspend fun getMediaBean(id: Int): MediaBean

}
