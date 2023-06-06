package com.liang.newbaseproject.normal

import android.view.View
import android.view.ViewGroup
import coil.load
import coil.transform.RoundedCornersTransformation
import com.liang.newbaseproject.R
import com.liang.newbaseproject.bean.WanDataBean
import com.liang.newbaseproject.databinding.BannerViewItemBinding
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

/**
 * - Time: 2023/6/2/0002 on 9:11
 * - User: Jerry
 * - Description: Banner适配器
 */
class BannerAdapter : BaseBannerAdapter<WanDataBean>() {

    override fun getLayoutId(viewType: Int): Int = R.layout.banner_view_item

    override fun createViewHolder(
        parent: ViewGroup,
        itemView: View?,
        viewType: Int
    ): BaseViewHolder<WanDataBean> {
        return ViewBindingViewHolder(BannerViewItemBinding.bind(itemView!!))
    }

    override fun bindData(
        holder: BaseViewHolder<WanDataBean>?,
        data: WanDataBean?,
        position: Int,
        pageSize: Int
    ) {
        // 如果需要通过getAdapterPosition()方法获取position,可参考如下代码
        // val adapterPosition = holder!!.adapterPosition
        // val realPosition =
        // BannerUtils.getRealPosition(isCanLoop.toInt(), adapterPosition, mList.size)
        if (holder is ViewBindingViewHolder) {
            holder.viewBinding.ivBanner.load(data?.imagePath) {
                crossfade(true)
                placeholder(com.liang.module_base.R.drawable.app_vector_image)
                error(com.liang.module_base.R.drawable.app_vector_broken_image)
                transformations(RoundedCornersTransformation(10f,10f,10f,10f))
            }
        }
    }

    internal class ViewBindingViewHolder(var viewBinding: BannerViewItemBinding) :
        BaseViewHolder<WanDataBean>(viewBinding.root)

}