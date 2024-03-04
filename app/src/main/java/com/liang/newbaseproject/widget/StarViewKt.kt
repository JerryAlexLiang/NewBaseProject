package com.liang.newbaseproject.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import com.liang.newbaseproject.R

class StarViewKt(context: Context?) : View(context) {
    // 设置Star的默认显示位置的X,Y坐标
    var bitmapX = 600f
    var bitmapY = 150f

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // 创建并实例化Paint对象
        val paint = Paint()
        // 根据图片生成位图对象
        val bitmap = BitmapFactory.decodeResource(this.resources, R.drawable.icon_star_normal)
        // 在画布上绘制Star
        canvas?.drawBitmap(bitmap, bitmapX, bitmapY, paint)
        // 判断图片是否回收
        if (bitmap.isRecycled) {
            // 强制回收图片
            bitmap.recycle()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        // 设置StarView显示位置的X坐标
        bitmapX = event.x
        // 设置StarView显示位置的Y坐标
        bitmapY = event.y
        // 重绘StarView组件
        invalidate()
        return true
    }
}
