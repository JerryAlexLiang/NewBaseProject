package com.liang.newbaseproject.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.luck.picture.lib.entity.LocalMedia
import kotlinx.parcelize.Parcelize

//import kotlinx.android.parcel.Parcelize

// Parcelize annotations from package 'kotlinx.android.parcel' are deprecated. Change package to 'kotlinx.parcelize'
/**
 * - Time: 2023/6/8/0008 on 15:50
 * - User: Jerry
 * - Description: Room实体类Entity
 * - 1、@Entity(tableName = "picture_media_table") 每个 @Entity 类代表一个 SQLite 表。
 * - 2、@PrimaryKey 每个实体都需要主键。为简便起见，每个字词都可充当自己的主键。
 * - 3、@ColumnInfo(name = "localMedia") 如果希望该表中列的名称与成员变量的名称不同，可以指定表中列的名称，此处的列名为“localMedia”。
 * - 4、存储在数据库中的每个属性均需公开，这是 Kotlin 的默认设置。
 * - 5、@PrimaryKey(autoGenerate = true) val id: Int -> 自动生成唯一的键，方法是使用以下代码注解主键：
 */
// Parcelize annotations from package 'kotlinx.android.parcel' are deprecated. Change package to 'kotlinx.parcelize'
@Parcelize
@Entity(tableName = "picture_media_table")
data class MediaBean(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fileName: String = "",
    @ColumnInfo(name = "localMedia")
    val localMedia: LocalMedia
) : Parcelable
