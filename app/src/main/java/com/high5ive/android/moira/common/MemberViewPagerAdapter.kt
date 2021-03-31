package com.high5ive.android.moira.common

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.high5ive.android.moira.ui.common.MemberReviewFragment
import com.high5ive.android.moira.ui.common.MemberInfoFragment

class MemberViewPagerAdapter(fa: FragmentActivity, val applyId: Int, val userId: Int, val type: String): FragmentStateAdapter(fa){
    override fun createFragment(position: Int): Fragment {

        val bundle1 = bundleOf("index" to applyId, "type" to type)
        val bundle2 = bundleOf("index" to userId, "type" to type)

        when(position){

            0 -> {
                val memberInfoFragment = MemberInfoFragment()
                memberInfoFragment.arguments = bundle1
                return memberInfoFragment
            }
            1 -> {
                val memberEvaluateFragment = MemberReviewFragment()
                memberEvaluateFragment.arguments = bundle2
                return memberEvaluateFragment
            }
            else -> {
                val memberInfoFragment = MemberInfoFragment()
                memberInfoFragment.arguments = bundle1
                return memberInfoFragment
            }
        }
    }
    override fun getItemCount():Int = 2
}