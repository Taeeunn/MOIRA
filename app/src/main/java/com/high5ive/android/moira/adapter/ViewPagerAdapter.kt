package com.high5ive.android.moira.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.high5ive.android.moira.ui.myteam.inprogress.InProgressTeamFragment

class ViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa){
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> InProgressTeamFragment()
            1 -> InProgressTeamFragment()
            else -> InProgressTeamFragment()
        }
    }
    override fun getItemCount():Int = 2
}