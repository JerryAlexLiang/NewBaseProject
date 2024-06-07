package com.liang.newbaseproject.spinner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.liang.newbaseproject.R


class MySpinnerAdapter(list: List<SpinnerBean>) : BaseAdapter() {

    private val dataList: List<SpinnerBean>

    init {
        dataList = list
    }

    override fun getCount(): Int = dataList.size

    override fun getItem(position: Int): Any = dataList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val layoutInflater = LayoutInflater.from(parent?.context)
        val holder: MyViewHolder
        if (view == null) {
            holder = MyViewHolder()
            view = layoutInflater.inflate(R.layout.item_spinner, null)
            view.tag = holder
            holder.tvSpinnerContent = view.findViewById(R.id.tvSpinnerContent)
        } else {
            holder = view.tag as MyViewHolder
        }
        holder.tvSpinnerContent!!.text = dataList[position].name
        return view!!
    }

    inner class MyViewHolder {
        var tvSpinnerContent: TextView? = null
    }

}

