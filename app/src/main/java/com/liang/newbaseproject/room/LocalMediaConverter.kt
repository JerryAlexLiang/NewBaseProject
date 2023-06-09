package com.liang.newbaseproject.room

import androidx.room.TypeConverter
import com.liang.module_base.utils.MoshiUtil
import com.luck.picture.lib.entity.LocalMedia

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
