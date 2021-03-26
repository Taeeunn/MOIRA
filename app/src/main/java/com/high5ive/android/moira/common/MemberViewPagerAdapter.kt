package com.high5ive.android.moira.common

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.high5ive.android.moira.ui.common.MemberReviewFragment
import com.high5ive.android.moira.ui.common.MemberInfoFragment

class MemberViewPagerAdapter(fa: FragmentActivity, val index: Int): FragmentStateAdapter(fa){
    override fun createFragment(position: Int): Fragment {
        val bundle = bundleOf("index" to index)

        return when(position){

            0 -> {
                val memberInfoFragment = MemberInfoFragment()
                memberInfoFragment.arguments = bundle
                return memberInfoFragment
            }
            1 -> {
                val memberEvaluateFragment = MemberReviewFragment()
                memberEvaluateFragment.arguments = bundle
                return memberEvaluateFragment
            }
            else -> {
                val memberInfoFragment = MemberInfoFragment()
                memberInfoFragment.arguments = bundle
                return memberInfoFragment
            }
        }
    }
    override fun getItemCount():Int = 2
}