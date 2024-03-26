@file:Suppress("UNUSED")

package com.liang.module_base.utils

import android.util.Log
import com.liang.module_base.BuildConfig

/**
 * App 日志类
 */
object LogUtils {

    private const val DEFAULT_TAG = "BaseApp"

    /**
     * Debug 下开启
     */
    private val isDebug
        get() = BuildConfig.DEBUG

    private const val tag = DEFAULT_TAG

    /**
     * [Log.VERBOSE]
     */
    fun v(msg: String, tag: String = DEFAULT_TAG) {
        if (isDebug) {
            Log.v(tag, msg)
        }
    }

    /**
     * [Log.DEBUG]
     */
    fun d(msg: String, tag: String = DEFAULT_TAG) {
        if (isDebug) {
            Log.d(tag, msg)
        }
    }

    /**
     * [Log.INFO]
     */
    fun i(msg: String, tag: String = DEFAULT_TAG) {
        if (isDebug) {
            Log.i(tag, msg)
        }
    }

    /**
     * [Log.WARN]
     */
    fun w(msg: String, tag: String = DEFAULT_TAG) {
        if (isDebug) {
            Log.w(tag, msg)
        }
    }

    /**
     * [Log.ERROR]
     */
    fun e(msg: String, tag: String = DEFAULT_TAG) {
        if (isDebug) {
            Log.e(tag, msg)
        }
    }

    /**
     * [Log.ERROR]
     */
    fun e(msg: String = "", throwable: Throwable, tag: String = DEFAULT_TAG) {
        if (isDebug) {
            Log.e(tag, msg, throwable)
        }
    }
}