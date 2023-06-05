package com.liang.module_base.extension

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.liang.module_base.R

/**
 * 获取主题的颜色
 */
fun Context.getThemeColor() =
    if (getNightMode()) ContextCompat.getColor(this, R.color.colorPrimary) else getAppThemeColor()

/**
 * 获取主题中字体颜色 （特殊的地方也需要 比如加载进度，比如背景等等）
 */
fun Context.getThemeTextColor() = if (getThemeColor() == Color.WHITE) ContextCompat.getColor(
    this,
    R.color.black
) else getThemeColor()

/**
 * 设置TaLayout背景颜色及文本颜色
 */
fun setTaLayoutViewTextColor(tabLayout: TabLayout) {
    if (getNightMode()) {
        tabLayout.setBackgroundColor(
            ContextCompat.getColor(
                tabLayout.context,
                R.color.colorPrimary
            )
        )
        tabLayout.setTabTextColors(
            ContextCompat.getColor(tabLayout.context, R.color.arrow_color),
            ContextCompat.getColor(tabLayout.context, R.color.color_theme_text)
        )
        tabLayout.setSelectedTabIndicatorColor(
            ContextCompat.getColor(
                tabLayout.context,
                R.color.white
            )
        )
    } else {
        val color = getAppThemeColor()
        tabLayout.setBackgroundColor(color)
        if (color == Color.WHITE) {
            tabLayout.setTabTextColors(
                ContextCompat.getColor(tabLayout.context, R.color.Grey700),
                ContextCompat.getColor(tabLayout.context, R.color.dark_dark)
            )
            tabLayout.setSelectedTabIndicatorColor(
                ContextCompat.getColor(
                    tabLayout.context,
                    R.color.dark_dark
                )
            )
        } else {
            tabLayout.setTabTextColors(
                ContextCompat.getColor(tabLayout.context, R.color.Grey300),
                ContextCompat.getColor(tabLayout.context, R.color.white)
            )
            tabLayout.setSelectedTabIndicatorColor(
                ContextCompat.getColor(
                    tabLayout.context,
                    R.color.white
                )
            )
        }
    }
}

fun setBottomNavigationItemColor(
    activity: AppCompatActivity,
    bottomNavigationView: BottomNavigationView
) {
    val color = if (getNightMode()) {
        ContextCompat.getColor(activity, R.color.colorAccent)
    } else {
        if (getNavBar()) {
            getAppThemeColor()
        } else {
            ContextCompat.getColor(activity, R.color.colorAccent)
        }
    }
    val navColor = if (color == Color.WHITE) {
        ContextCompat.getColor(activity, R.color.colorAccent)
    } else {
        color
    }
    val states = arrayOf(
        intArrayOf(android.R.attr.state_checked),
        intArrayOf(-android.R.attr.state_checked),
    )
    val stateList = ColorStateList.valueOf(navColor)
    val defaultColor =
        ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.nav_theme_color))
    val colors = intArrayOf(
        stateList.getColorForState(
            intArrayOf(android.R.attr.state_enabled), color
        ),
        defaultColor.getColorForState(
            intArrayOf(-android.R.attr.state_enabled),
            R.color.nav_theme_color
        )
    )
    val colorStateList = ColorStateList(states, colors)
    bottomNavigationView.itemTextColor = colorStateList
    bottomNavigationView.itemIconTintList = colorStateList
}
