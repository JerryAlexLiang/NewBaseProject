package com.liang.module_base.extension

import android.view.Gravity
import android.widget.Toast
import com.liang.module_base.base.BaseApp

/**
 * 显示短时间的Toast
 */
fun String?.showShortToast() = takeIf { it.isNullOrBlank().not() }?.also {
    Toast.makeText(BaseApp.appContext, it, Toast.LENGTH_SHORT).show()
}

/**
 * 显示长时间的Toast
 */
fun String?.showLongToast() = takeIf { it.isNullOrBlank().not() }?.also {
    Toast.makeText(BaseApp.appContext, this, Toast.LENGTH_LONG).show()
}

/**
 * 居中显示短时间的Toast
 */
fun String?.showShortToastInCenter() = takeIf { it.isNullOrBlank().not() }?.also {
    Toast.makeText(BaseApp.appContext, this, Toast.LENGTH_SHORT).apply {
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }
}

/**
 * 居中显示短时间的Toast
 */
fun String?.showLongToastInCenter() = takeIf { it.isNullOrBlank().not() }?.also {
    Toast.makeText(BaseApp.appContext, this, Toast.LENGTH_LONG).apply {
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }
}

/**
 * 根据条件设置是否镜像X轴显示Toast
 */
fun String?.showShortToastMirrorX(mirrorX: Boolean) = takeIf { it.isNullOrBlank().not() }?.also {
    Toast.makeText(BaseApp.appContext, it, Toast.LENGTH_SHORT).apply {
        if (mirrorX) view!!.scaleX = -1F else view!!.scaleX = 1F
        show()
    }
}

fun String?.showLongToastMirrorX(mirrorX: Boolean) = takeIf { it.isNullOrBlank().not() }?.also {
    Toast.makeText(BaseApp.appContext, it, Toast.LENGTH_LONG).apply {
        if (mirrorX) view!!.scaleX = -1F else view!!.scaleX = 1F
        show()
    }
}

fun String?.showShortToastInCenterMirrorX(mirrorX: Boolean) = takeIf { it.isNullOrBlank().not() }?.also {
    Toast.makeText(BaseApp.appContext, this, Toast.LENGTH_SHORT).apply {
        if (mirrorX) view!!.scaleX = -1F else view!!.scaleX = 1F
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }
}

fun String?.showLongToastInCenterMirrorX(mirrorX: Boolean) = takeIf { it.isNullOrBlank().not() }?.also {
    Toast.makeText(BaseApp.appContext, this, Toast.LENGTH_SHORT).apply {
        if (mirrorX) view!!.scaleX = -1F else view!!.scaleX = 1F
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }
}

/**
 * X轴镜像显示短时间的Toast
 */
fun String?.showShortToastMirrorX() = takeIf { it.isNullOrBlank().not() }?.also {
    Toast.makeText(BaseApp.appContext, it, Toast.LENGTH_SHORT).apply {
        view!!.scaleX = -1F
        show()
    }
}

/**
 * X轴镜像显示长时间的Toast
 */
fun String?.showLongToastMirrorX() = takeIf { it.isNullOrBlank().not() }?.also {
    Toast.makeText(BaseApp.appContext, it, Toast.LENGTH_LONG).apply {
        view!!.scaleX = -1F
        show()
    }
}

/**
 * X轴镜像居中显示短时间的Toast
 */
fun String?.showShortToastInCenterMirrorX() = takeIf { it.isNullOrBlank().not() }?.also {
    Toast.makeText(BaseApp.appContext, this, Toast.LENGTH_SHORT).apply {
        view!!.scaleX = -1F
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }
}

/**
 * X轴镜像居中显示长时间的Toast
 */
fun String?.showLongToastInCenterMirrorX() = takeIf { it.isNullOrBlank().not() }?.also {
    Toast.makeText(BaseApp.appContext, this, Toast.LENGTH_LONG).apply {
        view!!.scaleX = -1F
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }
}