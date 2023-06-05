package com.liang.module_base.widget

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import com.liang.module_base.R

class LoadingDialog(context: Context) : Dialog(
    context,
    R.style.LoadingDialog
) {

    private var loadingDialog: LoadingDialog? = null

    init {
        setContentView(R.layout.layout_loading_view)
        val imageView: ImageView = findViewById(R.id.iv_image)
        val animation: Animation = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        animation.duration = 2000
        animation.repeatCount = 10
        animation.fillAfter = true
        imageView.startAnimation(animation)
    }

    fun showDialog(context: Context, isCancelable: Boolean) {
        if (context is Activity) {
            if (context.isFinishing) {
                return
            }
        }


        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(context)
            loadingDialog!!.setCancelable(isCancelable)
        }

        if (!(loadingDialog!!.isShowing)) {
            loadingDialog?.show()
        }

    }

    fun dismissDialog() {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog?.dismiss()
            loadingDialog = null
        }
    }

}