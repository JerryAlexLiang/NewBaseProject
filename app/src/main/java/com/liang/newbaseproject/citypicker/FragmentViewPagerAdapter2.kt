package com.liang.newbaseproject.citypicker

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentViewPagerAdapter2(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private var provinceFragment: ProvinceListFragment2 = ProvinceListFragment2()
    private var cityFragment: CityListFragment2 = CityListFragment2()

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
