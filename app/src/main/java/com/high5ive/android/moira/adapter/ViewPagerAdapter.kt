package com.high5ive.android.moira.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.high5ive.android.moira.InProgressFragment

class ViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa){
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> InProgressFragment()
            1 -> InProgressFragment()
            else -> InProgressFragment()
        }
    }
    override fun getItemCount():Int = 2
}