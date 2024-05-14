package com.liang.newbaseproject.citypicker

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private var provinceFragment: ProvinceListFragment = ProvinceListFragment()
    private var cityFragment: CityListFragment = CityListFragment()

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
//        when (position) {
//            0 -> return provinceFragment
//            1 -> return cityFragment
//        }
//        return Fragment()

        return when (position) {
            0 -> return provinceFragment
            1 -> return cityFragment
            else -> Fragment()
        }
    }
}
