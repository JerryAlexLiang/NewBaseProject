package com.liang.module_weather.ui.place

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.liang.module_weather.R
import com.liang.module_weather.databinding.RvItemWeatherPlaceBinding
import com.liang.module_weather.logic.model.Place

/**
 * - Time: 2024/3/27/0027 9:18
 * - User: Jerry
 * - Description: 彩云天气城市搜索列表适配器
 */
class PlaceAdapter(private val placeList: List<Place>) :
    RecyclerView.Adapter<PlaceAdapter.MyViewHolder>() {

    private lateinit var binding: RvItemWeatherPlaceBinding

    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(bean: Place?)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

//    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val tvPlaceName: TextView = view.findViewById(R.id.tvPlaceName)
//        val tvPlaceAddress: TextView = view.findViewById(R.id.tvPlaceAddress)
//    }

    // 使用ViewBinding
    inner class MyViewHolder(private val binding: RvItemWeatherPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // 使用ViewBinding时，一定要在这里绑定数据，否则会出现UI不更新问题
        fun bind(placeBean: Place) {
            binding.tvPlaceName.text = placeBean.name
            binding.tvPlaceAddress.text = binding.root.context.getString(
                R.string.weather_place_address_title,
                placeBean.address
            )
            binding.root.setOnClickListener {
                onItemClickListener?.onItemClick(placeBean)
            }
        }
    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.rv_item_weather_place, parent, false)
//        val viewHolder = MyViewHolder(view)
//        return viewHolder
//    }

    // 使用ViewBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = RvItemWeatherPlaceBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = placeList.size

//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val placeBean = placeList[position]
//        holder.itemView.apply {
//
//            holder.apply {
//                tvPlaceName.text = placeBean.name
//                tvPlaceAddress.text =
//                    context.getString(R.string.weather_place_address_title, placeBean.address)
//            }
//
//            setOnClickListener {
//                onItemClickListener?.onItemClick(placeBean)
//            }
//        }
//    }

    // 使用ViewBinding
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val placeBean = placeList[position]
        holder.bind(placeBean)
    }
}

