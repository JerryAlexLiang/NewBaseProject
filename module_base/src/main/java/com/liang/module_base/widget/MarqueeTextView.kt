package com.liang.module_base.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * @Time: 2023/5/4/0004 on 13:38
 * @User: Jerry
 * @Description: 跑马灯
 */
class MarqueeTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    override fun isFocused() = true
}

/*
<com.kotlin.mvvm.view.MarqueeTextView
                android:id="@+id/tv_title"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:gravity="center_vertical"
                android:layout_gravity="center"
                android:layout_marginEnd="30dp"
                android:ellipsize="marquee"
                android:focusable="true"
                android:focusableInTouchMode="true"
                android:marqueeRepeatLimit="marquee_forever"
                android:scrollHorizontally="true"
                android:singleLine="true"
                android:textSize="18dp"/>
 */