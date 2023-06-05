package com.liang.module_base.utils.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 创建日期: 2021/7/27 on 3:40 PM
 * 描述:
 * 作者: 杨亮
 */
class SpaceItemDecorationKt : RecyclerView.ItemDecoration() {

    private var left = 0
    private var right = 0
    private var top = 0
    private var bottom = 0

    fun setLeft(left: Int): SpaceItemDecorationKt {
        this.left = left
        return this
    }

    fun setRight(right: Int): SpaceItemDecorationKt {
        this.right = right
        return this
    }

    fun setTop(top: Int): SpaceItemDecorationKt {
        this.top = top
        return this
    }

    fun setBottom(bottom: Int): SpaceItemDecorationKt {
        this.bottom = bottom
        return this
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = top
        outRect.bottom = bottom
        outRect.left = left
        outRect.right = right
    }
}