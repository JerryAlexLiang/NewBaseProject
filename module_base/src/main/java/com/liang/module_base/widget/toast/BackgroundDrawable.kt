package com.liang.module_base.widget.toast

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.ColorInt

/**
 * - 创建日期：2018/6/18 on 上午12:18
 * - 描述:底层背景
 * - 作者:yangliang
 */
class BackgroundDrawable(@ColorInt color: Int, context: Context) : Drawable() {
    private val paint: Paint
    private val mContext: Context

    init {
        //上下文
        mContext = context.applicationContext
        paint = Paint(Paint.ANTI_ALIAS_FLAG) //画笔
        paint.color = color
        paint.isDither = true //设置防抖动
        paint.style = Paint.Style.FILL
    }

    override fun draw(canvas: Canvas) {
        val width = bounds.width()
        val height = bounds.height()
        canvas.drawRoundRect(
            0f,
            0f,
            width.toFloat(),
            height.toFloat(),
            dp2px(20).toFloat(),
            dp2px(20).toFloat(),
            paint
        )
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    /**
     * 设置半透明
     * @return
     */
    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    private fun dp2px(values: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, values.toFloat(),
            mContext.resources.displayMetrics
        ).toInt()
    }
}