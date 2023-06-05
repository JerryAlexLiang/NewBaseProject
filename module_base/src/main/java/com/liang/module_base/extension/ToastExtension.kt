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