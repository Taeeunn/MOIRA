package com.high5ive.android.moira.ui.mypage.scrap

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.high5ive.android.moira.ui.myteam.inprogress.InProgressTeamFragment
import com.high5ive.android.moira.ui.teamfinding.recruit.RecruitPostFragment
import com.high5ive.android.moira.ui.teamfinding.user.UserPoolFragment

class ScrapViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa){
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> RecruitPostFragment()
            1 -> UserPoolFragment()
            else -> InProgressTeamFragment()
        }
    }
    override fun getItemCount():Int = 2
}