package com.liang.module_base.utils

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import androidx.core.graphics.scaleMatrix

/**
 * @Time: 2023/4/17/0017 on 14:54
 * @User: Jerry
 * @Description: Toast封装工具类
 */
object ToastUtil {

    /**
     * 显示短时间的Toast
     *
     * @param context Context
     * @param msg 显示的消息
     */
    fun showShort(context: Context, msg: String) {
        Toast.makeText(context.applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 显示长时间的Toast
     *
     * @param context Context
     * @param msg 显示的消息
     */
    fun showLong(context: Context, msg: String) {
        Toast.makeText(context.applicationContext, msg, Toast.LENGTH_LONG).show()
    }

    fun showShort(
        context: Context,
        msg: String,
        mirrorX: Float = 1F,
        mirrorY: Float = 1F,
    ) {
        Toast.makeText(context.applicationContext, msg, Toast.LENGTH_SHORT).apply {
            view!!.scaleX = mirrorX
            view!!.scaleY = mirrorY
            show()
        }
    }

    fun showLong(
        context: Context,
        msg: String,
        mirrorX: Float = 1F,
        mirrorY: Float = 1F,
    ) {
        Toast.makeText(context.applicationContext, msg, Toast.LENGTH_LONG).apply {
            view!!.scaleX = mirrorX
            view!!.scaleY = mirrorY
            show()
        }
    }

    /**
     * 居中显示短时间的Toast
     *
     * @param context Context
     * @param msg 显示的消息
     */
    fun showShortInCenter(
        context: Context,
        msg: String,
        mirrorX: Float = 1F,
        mirrorY: Float = 1F,
    ) {
        Toast.makeText(context.applicationContext, msg, Toast.LENGTH_SHORT).apply {
            view!!.scaleX = mirrorX
            view!!.scaleY = mirrorY
            setGravity(Gravity.CENTER, 0, 0)
            show()
        }
    }

    /**
     * 居中显示短时间的Toast
     *
     * @param context Context
     * @param msg 显示的消息
     */
    fun showLongInCenter(
        context: Context,
        msg: String,
        mirrorX: Float = 1F,
        mirrorY: Float = 1F,
    ) {
        Toast.makeText(context.applicationContext, msg, Toast.LENGTH_LONG).apply {
            view!!.scaleX = mirrorX
            view!!.scaleY = mirrorY
            setGravity(Gravity.CENTER, 0, 0)
            show()
        }
    }

    /**
     * 根据条件设置是否镜像X轴显示Toast
     */
    fun showShortMirrorX(context: Context, msg: String, mirrorX: Boolean) {
        if (mirrorX) {
            showShort(context, msg, mirrorX = -1F)
        } else {
            showShort(context, msg, mirrorX = 1F)
        }
    }

    fun showLongMirrorX(context: Context, msg: String, mirrorX: Boolean) {
        if (mirrorX) {
            showLong(context, msg, mirrorX = -1F)
        } else {
            showLong(context, msg, mirrorX = 1F)
        }
    }

    fun showShortInCenterMirrorX(context: Context, msg: String,mirrorX: Boolean) {
        if (mirrorX) {
            showShortInCenter(context, msg, mirrorX = -1F)
        } else {
            showShortInCenter(context, msg, mirrorX = 1F)
        }
    }

    fun showLongInCenterMirrorX(context: Context, msg: String,mirrorX: Boolean) {
        if (mirrorX) {
            showLongInCenter(context, msg, mirrorX = -1F)
        } else {
            showLongInCenter(context, msg, mirrorX = 1F)
        }
    }

    /**
     * X轴镜像显示短时间的Toast
     */
    fun showShortMirrorX(context: Context, msg: String) {
        showShort(context, msg, mirrorX = -1F)
    }

    /**
     * X轴镜像显示长时间的Toast
     */
    fun showLongMirrorX(context: Context, msg: String) {
        showLong(context, msg, mirrorX = -1F)
    }

    /**
     * X轴镜像居中显示短时间的Toast
     */
    fun showShortInCenterMirrorX(context: Context, msg: String) {
        showShortInCenter(context, msg, mirrorX = -1F)
    }

    /**
     * X轴镜像居中显示长时间的Toast
     */
    fun showLongInCenterMirrorX(context: Context, msg: String) {
        showLongInCenter(context, msg, mirrorX = -1F)
    }
}