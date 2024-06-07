package com.liang.newbaseproject.spinner

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.DataBindingHolder
import com.liang.newbaseproject.R
import com.liang.newbaseproject.databinding.RvItemPopSpinnerBinding

class PopRvSpinnerAdapter :
    BaseQuickAdapter<SpinnerBean, DataBindingHolder<RvItemPopSpinnerBinding>>() {

    private var selectDataList: MutableList<SpinnerBean> = mutableListOf()

    private lateinit var onItemClickListener: OnItemClickListener

    fun interface OnItemClickListener {
        fun onItemClick(bean: SpinnerBean?)
    }

    fun setOnMyItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(
        holder: DataBindingHolder<RvItemPopSpinnerBinding>,
        position: Int,
        item: SpinnerBean?
    ) {
        if (item == null) return
        val binding = holder.binding

        holder.itemView.setOnClickListener {
            // 单选
            if (!selectDataList.contains(item)) {
                selectDataList.clear()
                selectDataList.add(item)
            }
            notifyDataSetChanged()

            onItemClickListener?.onItemClick(item)
        }

        binding.tvSpinnerContent.apply {
            text = item.name

            if (selectDataList.contains(item)) {
                setTextColor(Color.BLUE)
            } else {
                setTextColor(Color.BLACK)
            }
        }
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): DataBindingHolder<RvItemPopSpinnerBinding> {
        return DataBindingHolder(R.layout.rv_item_pop_spinner, parent)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDefaultSelectList(defaultSelectList: MutableList<SpinnerBean>) {
        //单选
        selectDataList.clear()
        selectDataList.addAll(defaultSelectList)
        notifyDataSetChanged()
    }

    fun getSelectedList(): List<SpinnerBean> {
        return selectDataList
    }
}
