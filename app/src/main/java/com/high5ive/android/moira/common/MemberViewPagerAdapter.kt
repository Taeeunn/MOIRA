package com.high5ive.android.moira.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.high5ive.android.moira.ui.common.MemberEvaluateFragment
import com.high5ive.android.moira.ui.common.MemberInfoFragment

class MemberViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa){
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> MemberInfoFragment()
            1 -> MemberEvaluateFragment()
            else -> MemberInfoFragment()
        }
    }
    override fun getItemCount():Int = 2
}