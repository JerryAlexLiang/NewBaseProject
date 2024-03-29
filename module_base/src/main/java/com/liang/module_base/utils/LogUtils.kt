@file:Suppress("UNUSED")

package com.liang.module_base.utils

import android.util.Log
import com.liang.module_base.BuildConfig

/**
 * App 日志类
 */
object LogUtils {

    private const val DEFAULT_TAG = "RxHttp"

    /**
     * Debug 下开启
     */
    private val isDebug
        get() = BuildConfig.DEBUG

    private const val tag = DEFAULT_TAG

    /**
     * [Log.VERBOSE]
     */
    fun v(tag: String = DEFAULT_TAG, msg: String) {
        if (isDebug) {
            Log.v(tag, msg)
        }
    }

    /**
     * [Log.DEBUG]
     */
    fun d(tag: String = DEFAULT_TAG, msg: String) {
        if (isDebug) {
            Log.d(tag, msg)
        }
    }

    /**
     * [Log.INFO]
     */
    fun i(tag: String = DEFAULT_TAG, msg: String) {
        if (isDebug) {
            Log.i(tag, msg)
        }
    }

    /**
     * [Log.WARN]
     */
    fun w(tag: String = DEFAULT_TAG, msg: String) {
        if (isDebug) {
            Log.w(tag, msg)
        }
    }

    /**
     * [Log.ERROR]
     */
    fun e(tag: String = DEFAULT_TAG, msg: String) {
        if (isDebug) {
            Log.e(tag, msg)
        }
    }

    /**
     * [Log.ERROR]
     */
    fun e(tag: String = DEFAULT_TAG, msg: String = "", throwable: Throwable) {
        if (isDebug) {
            Log.e(tag, msg, throwable)
        }
    }
}