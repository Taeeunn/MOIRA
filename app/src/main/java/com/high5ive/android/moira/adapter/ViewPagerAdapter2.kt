package com.high5ive.android.moira.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.high5ive.android.moira.InProgressFragment
import com.high5ive.android.moira.MemberEvaluateFragment
import com.high5ive.android.moira.MemberInfoFragment

class ViewPagerAdapter2(fa: FragmentActivity): FragmentStateAdapter(fa){
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> MemberInfoFragment()
            1 -> MemberEvaluateFragment()
            else -> InProgressFragment()
        }
    }
    override fun getItemCount():Int = 2
}