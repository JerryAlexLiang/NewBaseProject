package com.liang.newbaseproject.room

import androidx.room.TypeConverter
import com.liang.module_base.utils.MoshiUtil
import com.luck.picture.lib.entity.LocalMedia
/**
 * - Time: 2023/6/12/0012 on 13:34
 * - User: Jerry
 * - Description: 使用Room引用复杂数据,使用类型转换器
 * - 有时，需要应用将自定义数据类型存储在单个数据库列中。
 *   可以通过提供类型转换器来支持自定义类型，类型转换器是Room的方法，用于告知Room如何将自定义类型与Room可以保留的已知类型相互转换。
 *   可以使用 @TypeConverter注解来识别类型转换器
 */
class LocalMediaConverter {

    @TypeConverter
    fun localMediaToString(localMedia: LocalMedia): String {
        return MoshiUtil.toJson(localMedia)
    }

    @TypeConverter
    fun stringToLocalMedia(json: String): LocalMedia? {
        return MoshiUtil.fromJson<LocalMedia>(json)
    }
}
