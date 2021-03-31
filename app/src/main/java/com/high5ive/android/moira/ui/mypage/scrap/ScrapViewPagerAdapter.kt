package com.high5ive.android.moira.ui.mypage.scrap

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class ScrapViewPagerAdapter(fa: FragmentActivity): FragmentStateAdapter(fa){
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ScrapRecruitPostFragment()
            1 -> ScrapUserPoolFragment()
            else -> ScrapRecruitPostFragment()
        }
    }

    override fun getItemCount():Int = 2


}