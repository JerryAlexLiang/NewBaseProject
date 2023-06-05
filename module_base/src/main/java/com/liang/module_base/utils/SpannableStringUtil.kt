package com.liang.module_base.utils

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.*
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import java.util.regex.Pattern

object SpannableStringUtil {

    private const val TAG = "SpannableStringUtil"

    /**
     * 设置字体的高亮色 大小
     */
    fun highlight(
        text: String?,
        target: String?,
        colorString: String?,
        size: Int
    ): SpannableStringBuilder {

        val spannable = SpannableStringBuilder(text)
        var span: CharacterStyle
        try {
            val p = Pattern.compile(target)
            val m = p.matcher(text)
            while (m.find()) {
                span = ForegroundColorSpan(Color.parseColor(colorString)) // 需要重复！
                spannable.setSpan(
                    span, m.start(), m.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                span = AbsoluteSizeSpan(size)
                spannable.setSpan(
                    span, m.start(), m.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return spannable
    }

    /**
     * 设置字体的高亮色
     */
    fun highlight(text: String?, target: String?, colorString: String?): SpannableStringBuilder {
        val defaultColor = colorString ?: "#FF61E9F6"
        val spannable = SpannableStringBuilder(text)
        var span: CharacterStyle
        try {
            val p = Pattern.compile(target)
            val m = p.matcher(text)
            while (m.find()) {
                span = ForegroundColorSpan(Color.parseColor(defaultColor)) // 需要重复！
                spannable.setSpan(
                    span, m.start(), m.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return spannable
    }

    /**
     * 设置字体的前景色
     */
    fun addForeColorSpan(content: String, color: Int, start: Int, end: Int): SpannableString {
        val spanString = SpannableString(content)
        val span = ForegroundColorSpan(color)
        spanString.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spanString
    }

    /**
     * 设置url
     */
    fun addUrlSpan(content: String, url: String, start: Int, end: Int): SpannableString {
        val spanString = SpannableString(content)
        val span = URLSpan(url)
        spanString.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spanString
    }

    fun addForeColorAndUrlSpan(
        content: String,
        url: String,
        color: Int,
        start: Int,
        end: Int
    ): SpannableString {
        val spanString = SpannableString(content)
        val span = ForegroundColorSpan(color)
        spanString.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val urlSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                ToastUtils.showLong(url)
            }
        }
        spanString.setSpan(urlSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spanString
    }

    fun addClickEvent(
        content: String,
        clickValue: String,
        clickableSpan: ClickableSpan
    ): SpannableStringBuilder {
        val spanString = SpannableStringBuilder(content)
        try {
            val p = Pattern.compile(clickValue)
            val m = p.matcher(content)
            while (m.find()) {
                spanString.setSpan(
                    clickableSpan, m.start(), m.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return spanString
    }

}