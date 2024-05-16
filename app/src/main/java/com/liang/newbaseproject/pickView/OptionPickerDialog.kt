package com.liang.newbaseproject.pickView

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.annotation.ColorInt
import com.liang.newbaseproject.R
import com.liang.newbaseproject.databinding.DialogSinglePickerBinding

/**
 * - Time: 2024/05/16 13:51
 * - User: Jerry
 * - Description: 条件选择器弹框
 */
class OptionPickerDialog private constructor(context: Context) :
    Dialog(context, R.style.DatePicker_Dialog) {
    private val binding = DialogSinglePickerBinding.inflate(LayoutInflater.from(context))
    private var mTitle: String? = null
    private var mHighlightColor: Int? = null
    private var isCanceledTouchOutside: Boolean? = null
    private lateinit var mDataList: ArrayList<String>
    private var mSelectedItemPosition: Int? = 0
    private var mOnDataResult: ((data: String) -> Unit)? = null

    init {
        setContentView(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindowParams()
        initView()
    }

    private fun initView() {
        binding.tvDialogTitle.text = mTitle ?: ""
        val highlightColor = mHighlightColor ?: Color.BLACK
        binding.dialogPickerView.setHighlightColor(highlightColor)
        binding.tvDialogTopConfirm.setTextColor(highlightColor)
        setCanceledOnTouchOutside(isCanceledTouchOutside ?: true)
        binding.dialogPickerView.setItems(mDataList)
        binding.dialogPickerView.setSelectedItem(mDataList[mSelectedItemPosition ?: 0])

        binding.tvDialogTopCancel.setOnClickListener {
            dismiss()
        }
        binding.tvDialogTopConfirm.setOnClickListener {
            dismiss()
            mOnDataResult?.invoke(binding.dialogPickerView.getSelectedItem())
        }
    }

    private fun setWindowParams() {
        val params = window!!.attributes
//        params.gravity = Gravity.BOTTOM
        params.gravity = Gravity.CENTER
//        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.width = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.attributes = params
    }

    class Builder(private val context: Context) {
        private var mTitle: String? = null
        private var mHighlightColor: Int? = null
        private var isCanceledTouchOutside: Boolean? = null
        private lateinit var mDataList: ArrayList<String>
        private var mSelectedItemPosition: Int = 0
        private var onDataResult: ((data: String) -> Unit)? = null

        fun setItems(mDataList: ArrayList<String>): Builder {
            this.mDataList = mDataList
            return this
        }

        fun setSelectedItem(mSelectedItemPosition: Int): Builder {
            this.mSelectedItemPosition = mSelectedItemPosition
            return this
        }

        fun setTitle(title: String?): Builder {
            this.mTitle = title
            return this
        }

        fun setHighlightColor(@ColorInt color: Int): Builder {
            this.mHighlightColor = color
            return this
        }

        fun setCanceledTouchOutside(canceledTouchOutside: Boolean): Builder {
            this.isCanceledTouchOutside = canceledTouchOutside
            return this
        }

        fun setOnDataResult(onDataResult: ((data: String) -> Unit)): Builder {
            this.onDataResult = onDataResult
            return this
        }

        fun build(): OptionPickerDialog {
            val dialog = OptionPickerDialog(context)
            dialog.mTitle = mTitle
            dialog.mHighlightColor = mHighlightColor
            dialog.mDataList = mDataList
            dialog.mSelectedItemPosition = mSelectedItemPosition
            dialog.isCanceledTouchOutside = isCanceledTouchOutside
            dialog.mOnDataResult = onDataResult
            return dialog
        }
    }
}