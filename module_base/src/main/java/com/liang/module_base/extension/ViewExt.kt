package com.liang.module_base.extension

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.liang.module_base.utils.ScreenUtil
import com.liang.module_base.R

/**
 * @Time: 2023/5/4/0004 on 13:28
 * @User: Jerry
 * @Description: View 操作相关扩展
 */


/**
 * ImageView利用Glide加载图片
 * @param url 图片url（可远程可本地）
 * @param showPlaceholder 是否展示placeholder，默认为true
 */
fun ImageView.loadByGlide(url: String, showPlaceholder: Boolean = true) {
    if (showPlaceholder) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.ic_default_img)
            .error(R.drawable.app_vector_broken_image)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(this)
    } else {
        Glide.with(context)
            .load(url)
            .error(R.drawable.app_vector_broken_image)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(this)
    }
}

/**
 * ImageView利用Glide加载图片
 * @param resourceId 本地图片资源Id
 * @param showPlaceholder 是否展示placeholder，默认为false
 */
fun ImageView.loadByGlide(@DrawableRes resourceId: Int, showPlaceholder: Boolean = false) {
    // 之所以不添加withCrossFade渐变效果，是由于SplashBannerAdapter启动加载本地大图会出现滑动时图片闪烁
    if (showPlaceholder) {
        Glide.with(context)
            .load(resourceId)
            .placeholder(R.drawable.ic_default_img)
            .error(R.drawable.app_vector_broken_image)
            .into(this)
    } else {
        Glide.with(context)
            .load(resourceId)
            .error(R.drawable.app_vector_broken_image)
            .into(this)
    }
}

/**
 * ImageView利用Glide加载圆形图片
 * @param url 图片url（可远程可本地）
 */
fun ImageView.loadCircleByGlide(url: String) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.ic_default_img)
        .error(R.drawable.app_vector_broken_image)
        .apply(RequestOptions.bitmapTransform(CircleCrop()))
        .transition(DrawableTransitionOptions.withCrossFade(500))
        .into(this)
}

/**
 * ImageView利用Glide加载圆形图片
 * @param resourceId 本地图片资源Id
 */
fun ImageView.loadCircleByGlide(@DrawableRes resourceId: Int) {
    Glide.with(context)
        .load(resourceId)
        .placeholder(R.drawable.ic_default_img)
        .error(R.drawable.app_vector_broken_image)
        .apply(RequestOptions.bitmapTransform(CircleCrop()))
        .transition(DrawableTransitionOptions.withCrossFade(500))
        .into(this)
}

/**
 * SwipeRefreshLayout设置加载主题颜色
 * @author LTP  2022/3/24
 */
fun SwipeRefreshLayout.initColors() {
    setColorSchemeResources(
        R.color.purple_500, android.R.color.holo_red_light,
        android.R.color.holo_orange_light, android.R.color.holo_green_light
    )
}

/**
 * RecyclerView列表为空时的显示视图
 */
fun RecyclerView.getEmptyView(message: String = "列表为空"): View {
    return LayoutInflater.from(context)
        .inflate(R.layout.empty_view, parent as ViewGroup, false).apply {
            findViewById<TextView>(R.id.tv_empty).text = message
        }
}

/**
 * 初始化普通的toolbar 只设置标题
 *
 * @param titleStr 标题
 */
fun Toolbar.initTitle(titleStr: String = "") {
    title = titleStr
}

/**
 * 初始化返回键
 *
 * @param backImg 返回键资源路径
 * @param onBack 返回事件
 */
fun Toolbar.initClose(
    backImg: Int = R.drawable.ic_back,
    onBack: () -> Unit
) {
    setNavigationIcon(backImg)
    setNavigationOnClickListener { onBack() }
}

/**
 * Activity上显示AlertDialog
 *
 * @param message AlertDialog内容信息
 * @param title AlertDialog标题，默认为 "温馨提示"
 * @param positiveButtonText AlertDialog右侧按键内容 默认为 "确定"
 * @param positiveAction AlertDialog点击右侧按键的行为 默认是空方法
 * @param negativeButtonText AlertDialog左侧按键内容 默认为 "取消"
 * @param negativeAction AlertDialog点击左侧按键的行为 默认是空方法
 */
fun AppCompatActivity.showDialog(
    message: String,
    title: String = "温馨提示",
    positiveButtonText: String = "确定",
    positiveAction: () -> Unit = {},
    negativeButtonText: String = "取消",
    negativeAction: () -> Unit = {}
) {
    MaterialDialog(this)
        .cancelable(true)
        .lifecycleOwner(this)
        .show {
            title(text = title)
            message(text = message)
            positiveButton(text = positiveButtonText) { positiveAction.invoke() }
            negativeButton(text = negativeButtonText) { negativeAction.invoke() }
        }
}

/**
 * Fragment上显示AlertDialog
 *
 * @param message AlertDialog内容信息
 * @param title AlertDialog标题，默认为 "温馨提示"
 * @param positiveButtonText AlertDialog右侧按键内容 默认为 "确定"
 * @param positiveAction AlertDialog点击右侧按键的行为 默认是空方法
 * @param negativeButtonText AlertDialog左侧按键内容 默认为 "取消"
 * @param negativeAction AlertDialog点击左侧按键的行为 默认是空方法
 */
fun Fragment.showDialog(
    message: String,
    title: String = "温馨提示",
    positiveButtonText: String = "确定",
    positiveAction: () -> Unit = {},
    negativeButtonText: String = "取消",
    negativeAction: () -> Unit = {}
) {
    MaterialDialog(requireContext())
        .cancelable(true)
        .lifecycleOwner(viewLifecycleOwner)
        .show {
            title(text = title)
            message(text = message)
            positiveButton(text = positiveButtonText) { positiveAction.invoke() }
            negativeButton(text = negativeButtonText) { negativeAction.invoke() }
        }
}

/** 加载框 */
@SuppressLint("StaticFieldLeak")
private var mLoadingDialog: MaterialDialog? = null

/** 打开加载框 */
fun AppCompatActivity.showLoading(message: String = "请求网络中") {
    if (!this.isFinishing) {
        if (mLoadingDialog == null) {
            mLoadingDialog = MaterialDialog(this)
                .cancelable(true)
                .cancelOnTouchOutside(false)
                .cornerRadius(6f)
                .customView(R.layout.dialog_loading)
                .maxWidth(literal = ScreenUtil.dp2px(this, 120f))
                .lifecycleOwner(this)
            mLoadingDialog?.getCustomView()?.run {
                this.findViewById<TextView>(R.id.tv_loadingMsg).text = message
            }
        }
        mLoadingDialog?.show()
    }
}

/** 打开加载框 */
fun Fragment.showLoading(message: String = "请求网络中") {
    if (!this.isRemoving) {
        if (mLoadingDialog == null) {
            mLoadingDialog = MaterialDialog(requireContext())
                .cancelable(true)
                .cancelOnTouchOutside(false)
                .cornerRadius(6f)
                .customView(R.layout.dialog_loading)
                .maxWidth(literal = ScreenUtil.dp2px(requireContext(), 120f))
                .lifecycleOwner(this)
            mLoadingDialog?.getCustomView()?.run {
                this.findViewById<TextView>(R.id.tv_loadingMsg).text = message
            }
        }
        mLoadingDialog?.show()
    }
}

/** 隐藏Loading加载框 */
fun hideLoading() {
    mLoadingDialog?.dismiss()
    mLoadingDialog = null
}

/**
 * 设置view显示
 */
fun View?.visible() {
    this ?: return
    visibility = View.VISIBLE
}

/**
 * 设置view不显示
 */
fun View?.invisible() {
    this ?: return
    visibility = View.INVISIBLE
}

/**
 * 设置view隐藏
 */
fun View?.gone() {
    this ?: return
    visibility = View.GONE
}

/**
 * 根据条件设置view显示隐藏
 * @param flag 为true 显示，为false 隐藏
 */
fun View?.visibleOrGone(flag: Boolean) {
    this ?: return
    visibility = if (flag) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

/**
 * 根据条件设置view显示隐藏
 * @param flag 为true 显示，为false 隐藏
 */
fun View?.visibleOrInvisible(flag: Boolean) {
    this ?: return
    visibility = if (flag) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}

/**
 * 防抖处理，防止重复点击事件，默认0.5秒内不可重复点击
 * @param interval 时间间隔，默认0.5秒
 * @param action 执行方法
 */
var lastClickTime = 0L
inline fun View.antiShakingClick(interval: Long = 500, crossinline action: (view: View) -> Unit) {
    setOnClickListener {
        val currentTime = System.currentTimeMillis()
        val flag: Boolean = !(lastClickTime != 0L && (currentTime - lastClickTime < interval))
        lastClickTime = currentTime
        if (flag) action(it)
    }
}


/**
 * View长按点击事件
 */
inline fun View.onLongClick(crossinline block: (View) -> Unit) {
    setOnLongClickListener {
        block(it)
        true
    }
}


/**
 * 批量设置控件点击事件。
 *
 * @param v 点击的控件
 * @param block 处理点击事件回调代码块
 */
fun setOnClickListener(vararg v: View?, block: View.() -> Unit) {
    val listener = View.OnClickListener { it.block() }
    v.forEach { it?.setOnClickListener(listener) }
}

/**
 * 批量设置控件点击事件。
 *
 * @param v 点击的控件
 * @param listener 处理点击事件监听器
 */
fun setOnClickListener(vararg v: View?, listener: View.OnClickListener) {
    v.forEach { it?.setOnClickListener(listener) }
}

/**
 * 镜像翻转View
 */
fun mirrorView(view: View, mirrorX: Float = 1F, mirrorY: Float = 1F) {
    view.scaleX = mirrorX
    view.scaleY = mirrorY
}

/**
 * X轴镜像翻转View
 */
fun mirrorViewByX(view: View, mirrorX: Float = 1F) {
    mirrorView(view, mirrorX)
}

/**
 * 根据条件设置是否镜像X轴显示View
 */
fun mirrorViewByX(view: View, mirrorX: Boolean) {
    if (mirrorX) {
        mirrorView(view, mirrorX = -1F)
    } else {
        mirrorView(view, mirrorX = 1F)
    }
}

/**
 * X轴正向镜像翻转View
 */
fun mirrorViewByXForPositive(view: View) {
    mirrorView(view, 1F)
}

/**
 * X轴反向镜像翻转View
 */
fun mirrorViewByXForReverse(view: View) {
    mirrorView(view, -1F)
}
