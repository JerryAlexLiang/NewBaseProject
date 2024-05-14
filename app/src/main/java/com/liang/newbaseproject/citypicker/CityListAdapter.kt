package com.liang.newbaseproject.citypicker

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.liang.module_base.utils.LogUtils
import com.liang.module_base.utils.ScreenUtil
import com.liang.module_base.utils.ToastUtil
import com.liang.module_weather.logic.model.Place
import com.liang.module_weather.ui.place.PlaceAdapter
import com.liang.newbaseproject.databinding.RvCityTagItemBinding

class CityListAdapter : BaseQuickAdapter<City, CityListAdapter.MyViewHolder>() {

    private var selectDataList: MutableList<City> = mutableListOf()

    private var onItemClickListener: OnItemClickListener? = null

    fun interface OnItemClickListener {
        fun onItemClick(bean: City?)
    }

    fun setOnMyItemClickListener(onItemClickListener: CityListAdapter.OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    // 自定义ViewHolder
    class MyViewHolder(
        parent: ViewGroup,
        val binding: RvCityTagItemBinding = RvCityTagItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ),
    ) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int, item: City?) {
        // 设置Item数据
        holder.binding.tvCityTag.text = item?.name ?: ""

        holder.itemView.setOnClickListener {
            // 单选
            if (!selectDataList.contains(item)) {
                selectDataList.clear()
                item?.let { it1 -> selectDataList.add(it1) }
            }
            notifyDataSetChanged()
            LogUtils.d(tag = "CityTagAdapter2", msg = "选中: ${item?.name}")
//            ToastUtil.showShort(context, "选中: ${item?.name}")

            onItemClickListener?.onItemClick(item)
        }

        holder.binding.tvCityTag.apply {
            if (selectDataList.contains(item)) {
                setTextColor(Color.BLACK)
                textSize = ScreenUtil.sp2px(context, 12f).toFloat()
            } else {
                setTextColor(Color.GRAY)
                textSize = ScreenUtil.sp2px(context, 10f).toFloat()
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDefaultSelectList(defaultSelectList: List<City>?) {
        //单选
        selectDataList.clear()
        if (defaultSelectList != null) {
            selectDataList.addAll(defaultSelectList)
        }
        notifyDataSetChanged()
    }

    fun getSelectedList(): List<City> {
        return selectDataList
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int,
    ): MyViewHolder {
        // 返回一个ViewHolder
        return MyViewHolder(parent)
    }
}


//class SingleSelectListAdapter(layoutResId: Int) : BaseQuickAdapter<BookBean, BaseViewHolder>(layoutResId) {
//
//    private var selectDataList: MutableList<BookBean> = mutableListOf()
//
//    override fun convert(holder: BaseViewHolder, item: BookBean) {
//        holder.setText(R.id.tvName, item.name)
//

//
//        if (selectDataList.contains(item)) {
//            holder.itemView.ivSelectContainer.setImageResource(R.drawable.ui_common_checked_multi)
//        } else {
//            holder.itemView.ivSelectContainer.setImageResource(R.drawable.ui_common_uncheck)
//        }
//    }
//
//    fun setDefaultSelectList(defaultSelectList: MutableList<BookBean>) {
//        //单选
//        selectDataList.clear()
//        selectDataList.addAll(defaultSelectList)
//        notifyDataSetChanged()
//    }
//
//    fun getSelectedList(): List<BookBean?>? {
//        return selectDataList
//    }
//}