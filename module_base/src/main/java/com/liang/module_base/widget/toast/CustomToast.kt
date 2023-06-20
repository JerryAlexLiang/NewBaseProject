package com.liang.module_base.widget.toast

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.liang.module_base.R

/**
 * - 创建日期：2018/6/17 on 下午11:54
 * - 描述:在该类中设置创建CustomToast对象的方法
 * - 作者:yangliang
 */
class CustomToast(context: Context?, layoutResID: Int) {

    val toast: Toast  //Toast对象
    val view: View  //Toast的UI效果
    val icon: ImageView  //图标
    val message: TextView  //内容

    init {
        toast = Toast(context)
        // view = LayoutInflater.from(context).inflate(R.layout.core_custom_toast_layout, null);
        view = LayoutInflater.from(context).inflate(layoutResID, null)
        icon = view.findViewById(R.id.iv_toast_icon)
        message = view.findViewById(R.id.tv_toast_message)
    }

    /**
     * 显示
     */
    fun show() {
        toast.show()
    }

    class Builder( //上下文
        private val mContext: Context
    ) {
        private var icon //图标图片
                : Bitmap? = null
        private var iconID = R.drawable.core_icon_user_logo //图标资源ID
        private var message //内容
                : String? = null
        private var backgroundColor = 0x56000000 //背景颜色
        private var duration = Toast.LENGTH_SHORT //设置时间
        private var mine: CustomToast? = null
        private var gravity = Gravity.NO_GRAVITY //设置位置
        private var offsetX = 0 //设置偏移度X

        //        private int offsetY = 0;//设置偏移度Y
        private var offsetY = 160 //设置偏移度Y
        private var isShowIcon //是否显示图标
                = false
        private var textColor = Color.WHITE //字体颜色
        private var layoutResID = 0
        fun setLayoutResID(layoutResID: Int): Builder {
            this.layoutResID = layoutResID
            return this
        }

        /**
         * 设置图标
         *
         * @param bitmap 图标资源
         * @return
         */
        fun setIcon(bitmap: Bitmap?): Builder {
            icon = bitmap
            return this
        }

        fun setIcon(@DrawableRes resId: Int): Builder {
            iconID = resId
            return this
        }

        fun showIcon(showIcon: Boolean?): Builder {
            if (showIcon != null) {
                isShowIcon = showIcon
            }
            return this
        }

        /**
         * 设置内容
         *
         * @param message 内容信息
         * @return
         */
        fun setMessage(message: String?): Builder {
            this.message = message
            return this
        }

        /**
         * 设置背景
         */
        fun setBackgroundColor(backgroundColor: Int): Builder {
            this.backgroundColor = backgroundColor
            return this
        }

        /**
         * 设置吐司时长
         */
        fun setDuration(duration: Int): Builder {
            this.duration = duration
            return this
        }

        /**
         * 设置位置
         */
        fun setGravity(gravity: Int): Builder {
            this.gravity = gravity
            return this
        }

        /**
         * 偏移量
         */
        fun setOffsetX(offsetX: Int): Builder {
            this.offsetX = offsetX
            return this
        }

        fun setOffsetY(offsetY: Int): Builder {
            this.offsetY = offsetY
            return this
        }

        /**
         * 设置字体的颜色
         *
         * @param color
         * @return
         */
        fun setTextColor(@ColorInt color: Int): Builder {
            textColor = color
            return this
        }

        /**
         * 创建CustomToast对象
         */
        fun build(): CustomToast {
            if (null == mine) {
                //创建对象
                mine = CustomToast(mContext, layoutResID)
            }
            if (isShowIcon) {
                //显示图标
                mine!!.icon.visibility = View.VISIBLE
                //判断是否显示图标
                if (null != icon) {
                    //设置图片
                    mine!!.icon.setImageBitmap(icon)
                } else {
                    //设置图片
                    mine!!.icon.setBackgroundResource(iconID)
                }
            }

            //判断内容是否为空
            if (!message!!.isEmpty()) {
                //不为空设置内容文字
                mine!!.message.text = message
            } else {
                mine!!.message.text = ""
            }

            //设置底层背景
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mine!!.view.background = BackgroundDrawable(backgroundColor, mContext)
            }
            //设置时长
            mine!!.toast.duration = duration
            //添加自定义效果
            mine!!.toast.view = mine!!.view
            //设置偏移量
            mine!!.toast.setGravity(gravity, offsetX, offsetY)
            //设置字体颜色
            mine!!.message.setTextColor(textColor)
            return mine as CustomToast
        }
    }
}