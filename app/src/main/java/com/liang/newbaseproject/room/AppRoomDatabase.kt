package com.liang.newbaseproject.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.liang.module_base.base.BaseApp

/**
 * 实现Room数据库（添加Room数据库）
 * - 1、Room数据库类必须是abstract抽象类且必须扩展RoomDatabase。整个应用通常只需要一个Room数据库实例。
 * - 2、将类注释为带有MediaBean类的表(实体)的Room Database.
 * - 3、后续的数据库升级是根据这个version来比较的，exportSchema导出架构.
 * - 4、数据库会通过每个@Dao的抽象“getter”方法公开 DAO。
 * - 5、点击Build编译项目，Room会自动生成对应的_Impl实现类，此处将生成AppRoomDataBase_Impl.java文件
 * - 5、数据库的实例化是很昂贵的，定义了一个单例AppRoomDatabase,以防出现同时打开数据库的多个实例的情况。
 *
 * - 6、在@Entities类里增加了新字段后，重新运行已创建过DB的demo会发生崩溃。
 * Room cannot verify the data integrity. Looks like you've changed schema but forgot to update the version number.
 * 这里的意思是提醒我们数据库对应的实体类发生了变化，但是没有更新数据库的版本号。
 * 将@Database的version升级为2之后再次运行仍然发生崩溃。
 * A migration from 1 to 2 was required but not found. Please provide the necessary Migration path via
 * RoomDatabase.Builder.addMigration(Migration ...) or allow for destructive migrations via one of the
 * RoomDatabase.Builder.fallbackToDestructiveMigration* methods.
 * 根据报错的意思是，当数据库表的版本进行升级时，需要提供自定义的Migration进行处理，如果不提供自定义的Migration，
 * 可以调用fallbackToDestructiveMigration()允许升级失败破坏性地删除DB.
 *
 * way1:自动升级
 * 如需声明两个数据库版本之间的自动迁移，请将 @AutoMigration 注解添加到 @Database 中的 autoMigrations 属性;
 * Room自动迁移依赖于为旧版和新版数据库生成的数据库架构。如果 exportSchema 被设为 false，或者如果尚未使用新版本号编译数据库，自动迁移将会失败(Cannot create auto migrations when export schema is OFF.)
 *
 * way2:手动升级
 */
@Database(
    entities = [MediaBean::class],
    version = 3,
    exportSchema = true, // 默认为true
    autoMigrations = [AutoMigration(from = 2, to = 3)]
)
@TypeConverters(LocalMediaConverter::class)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun getMediaBeanDao(): MediaBeanDao

    companion object {

        // way2:手动升级
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // 对MediaBean表增加一个fileName字段
                database.execSQL("ALTER TABLE picture_media_table ADD COLUMN fileName TEXT NOT NULL DEFAULT ''")
            }

        }

        // Singleton防止同时打开多个数据库实例
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        /**
         * 6、getDatabase会返回该单例。首次使用时，它会创建数据库，
         *   双重检测锁单例初始化StudentDB
         * - 具体方法是：使用Room的数据库构建器在AppRoomDatabase类的应用上下文中创建RoomDatabase对象，并将其命名为 "app_database"。
         */
        fun getDataBase(): AppRoomDatabase {
            // if the INSTANCE is not null, then return it,if it is, then create the database
            // 如果INSTANCE不为空，则返回它，如果为空，则创建数据库
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    BaseApp.appContext,
                    AppRoomDatabase::class.java,
                    "app_database"
                )
//                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
//                return instance
                instance
            }
        }
    }
}
