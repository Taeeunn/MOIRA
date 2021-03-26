package com.high5ive.android.moira.ui.teamfinding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.high5ive.android.moira.ui.teamfinding.recruit.RecruitPostFragment
import com.high5ive.android.moira.ui.teamfinding.user.UserPoolFragment

class TeamFindingViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa){
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> RecruitPostFragment()
            1 -> UserPoolFragment()
            else -> RecruitPostFragment()
        }
    }
    override fun getItemCount():Int = 2
}