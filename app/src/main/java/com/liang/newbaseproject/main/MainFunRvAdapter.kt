package com.liang.newbaseproject.main

import android.content.Context
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.liang.newbaseproject.bean.FunItemBean
import com.liang.module_base.utils.LanguageUtilKt
import com.liang.newbaseproject.R

class MainFunRvAdapter : BaseQuickAdapter<FunItemBean, QuickViewHolder>() {

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        // 返回一个 ViewHolder
        return QuickViewHolder(R.layout.rv_function_item, parent)
    }

    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: FunItemBean?) {
        // 设置item数据
//        holder.getView<TextView>(R.id.tvName).text =
//            (if (LanguageUtilKt.isChineseVersion) item?.funNameChinese else item?.funNameEnglish)

        //getStrByLanguage
        holder.getView<TextView>(R.id.tvName).text =
            LanguageUtilKt.getStrByLanguage(
                context,
                item?.funNameResId ?: R.string.func_default_text
            )
    }

}