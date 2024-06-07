package com.liang.newbaseproject.spinner

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.PopupWindow
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.liang.module_base.base.BaseActivity
import com.liang.module_base.utils.ToastUtil
import com.liang.newbaseproject.R
import com.liang.newbaseproject.databinding.ActivitySpinnerBinding


class SpinnerActivity : BaseActivity<ActivitySpinnerBinding>() {

    private lateinit var popupWindow: PopupWindow
    private lateinit var items: MutableList<SpinnerBean>
    private lateinit var popRvSpinnerAdapter: PopRvSpinnerAdapter

    private lateinit var defaultSelectDataList: MutableList<SpinnerBean>

    override fun getLayoutId(): Int = R.layout.activity_spinner
    override fun initView(savedInstanceState: Bundle?) {
        items = ArrayList()
        items.add(SpinnerBean("丁程鑫丁程鑫丁程鑫", "01"))
        items.add(SpinnerBean("贺峻霖贺峻霖贺峻霖", "02"))
        items.add(SpinnerBean("李天泽李天泽李天泽", "03"))

        defaultSelectDataList = mutableListOf()

        val mySpinnerAdapter = MySpinnerAdapter(items)

        initPopRvAdapter()

        mBinding.apply {
            spinnerSystemFit.apply {
                adapter = mySpinnerAdapter
            }
            spinnerSystemFit.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    ToastUtil.showShort(
                        this@SpinnerActivity,
                        parent?.getItemAtPosition(position).toString()
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }

            btnPopWindowSpinner.setOnClickListener {
                showPopupWindow()
            }
        }

    }

    private fun initPopRvAdapter() {
        popRvSpinnerAdapter = PopRvSpinnerAdapter()
        popRvSpinnerAdapter.submitList(items)
        defaultSelectDataList.clear()
        defaultSelectDataList.add(items[1])
        popRvSpinnerAdapter.setDefaultSelectList(defaultSelectDataList)

        popRvSpinnerAdapter.setOnMyItemClickListener {
            ToastUtil.showShort(this, it?.name ?: "")
            dismissPopupWindow()
        }

        val popupView = layoutInflater.inflate(R.layout.item_pop_window_rv, null)
        popupWindow = PopupWindow(
            popupView,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        val rvPopWindow = popupView.findViewById<RecyclerView>(R.id.rvPopWindow)
        rvPopWindow.apply {
            val linearLayoutManager = LinearLayoutManager(this@SpinnerActivity)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = linearLayoutManager
            adapter = popRvSpinnerAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@SpinnerActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        popupWindow.isOutsideTouchable = true
        popupWindow.isFocusable = true
    }

    private fun showPopupWindow() {
        if (popupWindow.isShowing) {
            return
        }
        popupWindow.showAsDropDown(mBinding.btnPopWindowSpinner, 0, 0, Gravity.CENTER)
    }

    private fun dismissPopupWindow() {
        if (popupWindow.isShowing) {
            popupWindow.dismiss()
        }
    }

}

