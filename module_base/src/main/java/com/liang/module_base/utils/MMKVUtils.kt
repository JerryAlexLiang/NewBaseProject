package com.liang.module_base.utils

import android.os.Parcelable
import com.tencent.mmkv.MMKV
import java.util.*

object MMKVUtils {

    private val mKv by lazy {
        MMKV.defaultMMKV()
    }

    fun put(key: String?, value: Any) {
        when (value) {
            is Int -> mKv.encode(key, value)
            is String -> mKv.encode(key, value)
            is Boolean -> mKv.encode(key, value)
            is Float -> mKv.encode(key, value)
            is Long -> mKv.encode(key, value)
            is Double -> mKv.encode(key, value)
            is Parcelable -> mKv.encode(key, value)
        }
    }

    /** * 这里使用安卓自带的Parcelable序列化，它比java支持的Serializer序列化性能好些 */
    fun <T : Parcelable> put(key: String, t: T?): Boolean {
        if (t == null) {

            return false
        }
        return mKv?.encode(key, t)!!
    }

    fun put(key: String, sets: Set<String>?): Boolean {
        if (sets == null) {

            return false
        }
        return mKv?.encode(key, sets)!!
    }

    fun getString(k: String?): String? {
        return getStringDefault(k, "")
    }

    fun getStringDefault(k: String?, def: String?): String? {
        return mKv.decodeString(k, def)
    }

    fun getInt(k: String?): Int {
        return mKv.decodeInt(k, 0)
    }

    fun getInt(k: String?, v: Int): Int {
        return mKv.decodeInt(k, v)
    }

    fun getBoolean(k: String?): Boolean {
        return mKv.decodeBool(k, false)
    }

    fun getBoolean(k: String?, defaultValue: Boolean): Boolean {
        return mKv.decodeBool(k, defaultValue)
    }

    fun getFloat(k: String?): Float {
        return mKv.decodeFloat(k, 0.0f)
    }

    fun getLong(k: String?): Long {
        return mKv.decodeLong(k, 0L)
    }

    fun getDouble(k: String?): Double {
        return mKv.decodeDouble(k)
    }

    fun <T : Parcelable> getParcelable(k: String?, clz: Class<T>): T? {
        return mKv.decodeParcelable(k, clz)
    }

    fun getStringSet(key: String): Set<String>? {
        return mKv?.decodeStringSet(key, Collections.emptySet())
    }

    fun removeKey(k: String?) {
        mKv.removeValueForKey(k)
    }

    fun clearAllData() {
        mKv.clearAll()
    }

    fun ifContainKey(k: String?): Boolean {
        return mKv.containsKey(k)
    }
}