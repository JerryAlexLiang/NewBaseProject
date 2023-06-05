package com.liang.module_base.extension

import android.os.Build
import android.text.Html
import android.text.Spanned

/**
 * @Time: 2023/5/4/0004 on 13:09
 * @User: Jerry
 * @Description: 字符串相关扩展
 */
@Suppress("DEPRECATION")
fun String.fromHtml(): Spanned? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) Html.fromHtml(
        this,
        Html.FROM_HTML_MODE_LEGACY
    ) else Html.fromHtml(this)

/**
 * String.trim() 只能去除字符串首尾两端的空格，使用 trimAll() 可去除字符串中所有空格
 * @return 去除所有空格后的字符串值
 */
fun String?.trimAll(): String? {
    if (this == null) return null
    if (this.isBlank()) return ""
    return this.replace(" ".toRegex(), "")
}

/**
 * 判断指定字符串是否能转换为 Int 格式
 * @return 是否能转换，true 能转换，false 不能
 */
fun String?.canToInt(): Boolean {
    if (this.isNullOrBlank()) {
        return false
    }
    try {
        this.toInt()
    } catch (e: NumberFormatException) {
        return false
    }
    return true
}

/**
 * 限制字符串的长度，不考虑特殊字符
 * @param maxCount 最大长度
 * @return 经过限制处理后的字符串值
 */
fun String?.limitLength(maxCount: Int): String? {
    if (this == null) return null
    if (this.isBlank()) return ""
    return if (this.length <= maxCount) this else substring(0, maxCount)
}

/**
 * 限制字符串的长度，考虑特殊字符
 * @param maxCount 最大长度
 * @return 经过限制处理后的字符串值
 */
fun String?.getLimitString(maxCount: Int): String? {
    if (this == null) return null
    if (this.isBlank()) return ""
    var count = 0
    var tempStr: String
//    for (i in 0 until length) {
    for (i in indices) {
        tempStr = this.substring(i, i + 1)
        if (tempStr.toByteArray().size == 3) {
            count += 2
        } else {
            count++
        }
        if (count > maxCount) {
            return this.substring(0, i)
        }
    }
    return this
}

/**
 * 判断指定字符串是否包含 emoji 表情
 * @return 是否包含 emoji 表情，true 包含，false 不包含
 */
fun String?.isContainsEmoji(): Boolean {
    if (this.isNullOrBlank()) {
        return false
    }
    val len: Int = this.length
    for (i in 0 until len) {
        if (this[i].isEmojiCharacter()) {
            return true
        }
    }
    return false
}

/**
 * 过滤 emoji 表情
 * @return 经过过滤处理后的字符串值
 */
fun String?.filterEmoji(): String? {
    if (this == null) return null
    if (this.isBlank()) return ""
    return this.replace("[^\\u0000-\\uFFFF]".toRegex(), "")
}

/**
 * 判断指定字符是否为 emoji 表情
 * @return 是否为 emoji 表情，true 是，false 不是
 */
fun Char?.isEmojiCharacter(): Boolean {
    if (this == null) {
        return false
    }
    return !(this.code == 0x0 || this.code == 0x9 || this.code == 0xA || this.code == 0xD ||
            this.code in 0x20..0xD7FF ||
            this.code in 0xE000..0xFFFD ||
            this.code in 0x10000..0x10FFFF)
}