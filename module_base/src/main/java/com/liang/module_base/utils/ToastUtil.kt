package com.liang.module_base.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.liang.module_base.R
import com.liang.module_base.base.BaseApp
import com.liang.module_base.widget.toast.BackgroundDrawable
import com.liang.module_base.widget.toast.CustomToast

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
//            view!!.scaleX = mirrorX
//            view!!.scaleY = mirrorY

            // setView禁用
            view?.apply {
                scaleX = mirrorX
                scaleY = mirrorY
            }
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
//            view!!.scaleX = mirrorX
//            view!!.scaleY = mirrorY

            // setView禁用
            view?.apply {
                scaleX = mirrorX
                scaleY = mirrorY
            }
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
//            view!!.scaleX = mirrorX
//            view!!.scaleY = mirrorY

            // setView禁用
            view?.apply {
                scaleX = mirrorX
                scaleY = mirrorY
            }
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
//            view!!.scaleX = mirrorX
//            view!!.scaleY = mirrorY

            // setView禁用
            view?.apply {
                scaleX = mirrorX
                scaleY = mirrorY
            }

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

    fun showShortInCenterMirrorX(context: Context, msg: String, mirrorX: Boolean) {
        if (mirrorX) {
            showShortInCenter(context, msg, mirrorX = -1F)
        } else {
            showShortInCenter(context, msg, mirrorX = 1F)
        }
    }

    fun showLongInCenterMirrorX(context: Context, msg: String, mirrorX: Boolean) {
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

    /**
     * 初始化封装自定义Toast
     */
    fun showCustomToast(
        icon: Bitmap?,
        isShowIcon: Boolean? = false,
        message: String?,
        backgroundColor: Int,
        textColor: Int,
        gravity: Int,
        duration: Int,
        mirrorX: Float = 1F,
        mirrorY: Float = 1F,
    ) {
        //创建Toast
        val toastNormal = CustomToast.Builder(BaseApp.appContext.applicationContext)
            .setLayoutResID(R.layout.core_custom_toast_layout)
            .setMessage(message) //设置提示文字
            .showIcon(isShowIcon) //是否显示图标
            .setIcon(icon) //图标
            .setBackgroundColor(backgroundColor) //设置背景颜色
            .setGravity(gravity) //设置吐司位置,Gravity.CENTER
            .setTextColor(textColor) //设置字体的颜色
            .setDuration(duration)
            .build() //创建吐司

        toastNormal.view.scaleX = mirrorX
        toastNormal.view.scaleY = mirrorY

        toastNormal.message.text = message
        toastNormal.icon.visibility = if (isShowIcon == true) View.VISIBLE else View.GONE
        toastNormal.icon.setImageBitmap(icon)
        toastNormal.message.setBackgroundColor(backgroundColor)
        toastNormal.view.background =
            BackgroundDrawable(backgroundColor, BaseApp.appContext.applicationContext)
        toastNormal.message.gravity = gravity
        toastNormal.message.setTextColor(textColor)
        toastNormal.toast.duration = duration

        toastNormal.show()

    }

    fun showCustomToastRectangle(
        icon: Bitmap?,
        isShowIcon: Boolean? = false,
        message: String?,
        backgroundColor: Int,
        textColor: Int,
        gravity: Int,
        duration: Int,
        mirrorX: Float = 1F,
        mirrorY: Float = 1F,
    ) {

        //创建Toast
        val toastRectangle = CustomToast.Builder(BaseApp.appContext.applicationContext)
            .setLayoutResID(R.layout.core_custom_toast_rectangle_layout)
            .setMessage(message) //设置提示文字
            .showIcon(isShowIcon) //是否显示图标
            .setIcon(icon) //图标
            .setBackgroundColor(backgroundColor) //设置背景颜色
            .setGravity(gravity) //设置吐司位置,Gravity.CENTER
            .setTextColor(textColor) //设置字体的颜色
            .setDuration(duration)
            .build() //创建吐司

        toastRectangle.view.scaleX = mirrorX
        toastRectangle.view.scaleY = mirrorY

        toastRectangle.message.text = message
        toastRectangle.icon.visibility = if (isShowIcon == true) View.VISIBLE else View.GONE
        toastRectangle.icon.setImageBitmap(icon)
        toastRectangle.message.setBackgroundColor(backgroundColor)
        toastRectangle.view.background = BackgroundDrawable(
            backgroundColor,
            BaseApp.appContext.applicationContext
        )
        toastRectangle.message.gravity = gravity
        toastRectangle.message.setTextColor(textColor)
        toastRectangle.toast.duration = duration

        toastRectangle.show()
    }

    fun showSuccessToast(
        context: Context,
        isShowIcon: Boolean = false,
        content: String?,
        mirrorX: Boolean = false,
    ) {
        showCustomToast(
            BitmapFactory.decodeResource(context.resources, R.drawable.core_icon_success),
            isShowIcon,
            content,
            ContextCompat.getColor(context, R.color.toast_bg),
            ContextCompat.getColor(context, R.color.text_invert),
            Gravity.BOTTOM,
            Toast.LENGTH_SHORT,
            mirrorX = if (mirrorX) -1F else 1F
        )
    }

    fun showSuccessToastLong(
        context: Context,
        isShowIcon: Boolean = false,
        content: String?,
        mirrorX: Boolean = false,
    ) {
        showCustomToast(
            BitmapFactory.decodeResource(context.resources, R.drawable.core_icon_success),
            isShowIcon,
            content,
            ContextCompat.getColor(context, R.color.toast_bg),
            ContextCompat.getColor(context, R.color.text_invert),
            Gravity.BOTTOM,
            Toast.LENGTH_LONG,
            mirrorX = if (mirrorX) -1F else 1F
        )
    }

    fun showFailToast(
        context: Context,
        isShowIcon: Boolean = false,
        content: String?,
        mirrorX: Boolean = false,
    ) {
        showCustomToast(
            BitmapFactory.decodeResource(context.resources, R.drawable.core_icon_fail),
            isShowIcon,
            content,
            ContextCompat.getColor(context, R.color.toast_bg),
            ContextCompat.getColor(context, R.color.text_invert),
            Gravity.BOTTOM,
            Toast.LENGTH_SHORT,
            mirrorX = if (mirrorX) -1F else 1F
        )
    }

    fun showFailToastLong(
        context: Context,
        content: String?,
        isShowIcon: Boolean = false,
        mirrorX: Boolean = false,
    ) {
        showCustomToast(
            BitmapFactory.decodeResource(context.resources, R.drawable.core_icon_fail),
            isShowIcon,
            content,
            ContextCompat.getColor(context, R.color.toast_bg),
            ContextCompat.getColor(context, R.color.text_invert),
            Gravity.BOTTOM,
            Toast.LENGTH_LONG,
            mirrorX = if (mirrorX) -1F else 1F
        )
    }

    fun showSuccessRectangleToast(
        context: Context,
        isShowIcon: Boolean = false,
        content: String?,
        mirrorX: Boolean = false,
    ) {
        showCustomToastRectangle(
            BitmapFactory.decodeResource(context.resources, R.drawable.core_icon_success),
            isShowIcon,
            content,
            ContextCompat.getColor(context, R.color.toast_bg),
            ContextCompat.getColor(context, R.color.text_invert),
            Gravity.CENTER,
            Toast.LENGTH_SHORT,
            mirrorX = if (mirrorX) -1F else 1F
        )
    }

    fun showSuccessRectangleToastLong(
        context: Context,
        isShowIcon: Boolean = false,
        content: String?,
        mirrorX: Boolean = false,
    ) {
        showCustomToastRectangle(
            BitmapFactory.decodeResource(context.resources, R.drawable.core_icon_success),
            isShowIcon,
            content,
            ContextCompat.getColor(context, R.color.toast_bg),
            ContextCompat.getColor(context, R.color.text_invert),
            Gravity.CENTER,
            Toast.LENGTH_LONG,
            mirrorX = if (mirrorX) -1F else 1F
        )
    }

    fun showFailRectangleToast(
        context: Context,
        isShowIcon: Boolean = false,
        content: String?,
        mirrorX: Boolean = false,
    ) {
        showCustomToastRectangle(
            BitmapFactory.decodeResource(context.resources, R.drawable.core_icon_fail),
            isShowIcon,
            content,
            ContextCompat.getColor(context, R.color.toast_bg),
            ContextCompat.getColor(context, R.color.text_invert),
            Gravity.CENTER,
            Toast.LENGTH_SHORT,
            mirrorX = if (mirrorX) -1F else 1F
        )
    }

    fun showFailRectangleToastLong(
        context: Context,
        isShowIcon: Boolean = false,
        content: String?,
        mirrorX: Boolean = false,
    ) {
        showCustomToastRectangle(
            BitmapFactory.decodeResource(context.resources, R.drawable.core_icon_fail),
            isShowIcon,
            content,
            ContextCompat.getColor(context, R.color.toast_bg),
            ContextCompat.getColor(context, R.color.text_invert),
            Gravity.CENTER,
            Toast.LENGTH_LONG,
            mirrorX = if (mirrorX) -1F else 1F
        )
    }

    fun showSuccessToastMirrorX(
        context: Context,
        isShowIcon: Boolean = false,
        content: String?
    ) {
        showSuccessToast(context, isShowIcon, content, mirrorX = true)
    }

    fun showSuccessToastLongMirrorX(
        context: Context,
        isShowIcon: Boolean = false,
        content: String?
    ) {
        showSuccessToastLong(context, isShowIcon, content, mirrorX = true)
    }

    fun showFailToastMirrorX(
        context: Context,
        isShowIcon: Boolean = false,
        content: String?
    ) {
        showFailToast(context, isShowIcon, content, mirrorX = true)
    }

    fun showFailToastLongMirrorX(
        context: Context,
        isShowIcon: Boolean = false,
        content: String?
    ) {
        showFailToastLong(context, content, isShowIcon, mirrorX = true)
    }


    fun showSuccessRectangleToastMirrorX(
        context: Context,
        isShowIcon: Boolean = false,
        content: String?
    ) {
        showSuccessRectangleToast(context, isShowIcon, content, mirrorX = true)
    }

    fun showSuccessRectangleToastLongMirrorX(
        context: Context,
        isShowIcon: Boolean = false,
        content: String?
    ) {
        showSuccessRectangleToastLong(context, isShowIcon, content, mirrorX = true)
    }

    fun showFailRectangleToastMirrorX(
        context: Context,
        isShowIcon: Boolean = false,
        content: String?
    ) {
        showFailRectangleToast(context, isShowIcon, content, mirrorX = true)
    }

    fun showFailRectangleToastLongMirrorX(
        context: Context,
        isShowIcon: Boolean = false,
        content: String?
    ) {
        showFailRectangleToastLong(context, isShowIcon, content, mirrorX = true)
    }
}