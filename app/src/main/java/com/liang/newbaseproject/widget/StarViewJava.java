package com.liang.newbaseproject.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.liang.newbaseproject.R;

public class StarViewJava extends View {

    // Star显示位置的X坐标
    public float bitmapX;
    // Star显示位置的Y坐标
    public float bitmapY;

    // 重写构造方法
    public StarViewJava(Context context) {
        super(context);
        // 设置Star的默认显示位置的X,Y坐标
        bitmapX = 200;
        bitmapY = 150;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 创建并实例化Paint对象
//        @SuppressLint("DrawAllocation")
        Paint paint = new Paint();
        // 根据图片生成位图对象
//        @SuppressLint("DrawAllocation")
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_star_active);
        // 在画布上绘制Star
        canvas.drawBitmap(bitmap, bitmapX, bitmapY, paint);
        // 判断图片是否回收
        if (bitmap.isRecycled()) {
            // 强制回收图片
            bitmap.recycle();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 设置StarView显示位置的X坐标
        bitmapX = event.getX();
        // 设置StarView显示位置的Y坐标
        bitmapY = event.getY();
        // 重绘StarView组件
        invalidate();
        return true;
    }
}
