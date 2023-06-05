package com.liang.module_base.extension

import com.blankj.utilcode.util.SizeUtils


fun Int.dp2px() = SizeUtils.dp2px(this.toFloat())

fun Float.dp2px() = SizeUtils.dp2px(this)

fun Int.sp2px() = SizeUtils.sp2px(this.toFloat())

fun Float.sp2px() = SizeUtils.sp2px(this)

fun Int.px2dp() = SizeUtils.px2dp(this.toFloat())

fun Float.px2dp() = SizeUtils.px2dp(this)

fun Int.px2sp() = SizeUtils.px2sp(this.toFloat())

fun Float.px2sp() = SizeUtils.px2sp(this)