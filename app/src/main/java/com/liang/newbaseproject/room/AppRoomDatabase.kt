package com.liang.newbaseproject.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * 实现Room数据库（添加Room数据库）
 * - 1、Room数据库类必须是abstract抽象类且必须扩展RoomDatabase。整个应用通常只需要一个Room数据库实例。
 * - 2、将类注释为带有MediaBean类的表(实体)的Room Database.
 * - 3、后续的数据库升级是根据这个version来比较的，exportSchema导出架构.
 * - 4、数据库会通过每个@Dao的抽象“getter”方法公开 DAO。
 * - 5、定义了一个单例AppRoomDatabase,以防出现同时打开数据库的多个实例的情况。
 */

@Database(entities = [MediaBean::class], version = 1, exportSchema = false)
@TypeConverters(LocalMediaConverter::class)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun getMediaBeanDao(): MediaBeanDao

//    companion object {
//        // Singleton防止同时打开多个数据库实例。
//        @Volatile
//        private var INSTANCE: AppRoomDatabase? = null
//
//        /**
//         * 6、getDatabase会返回该单例。首次使用时，它会创建数据库，
//         * - 具体方法是：使用Room的数据库构建器在AppRoomDatabase类的应用上下文中创建RoomDatabase对象，并将其命名为 "app_database"。
//         */
//        fun getDataBase(context: Context): AppRoomDatabase {
//            // if the INSTANCE is not null, then return it,if it is, then create the database
//            // 如果INSTANCE不为空，则返回它，如果为空，则创建数据库
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppRoomDatabase::class.java,
//                    "app_database"
//                ).build()
//                INSTANCE = instance
////                return instance
//                instance
//            }
//        }
//    }
}
