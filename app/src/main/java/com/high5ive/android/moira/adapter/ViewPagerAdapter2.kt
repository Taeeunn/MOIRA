package com.high5ive.android.moira.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.high5ive.android.moira.ui.applicant.MemberEvaluateFragment
import com.high5ive.android.moira.ui.applicant.MemberInfoFragment
import com.high5ive.android.moira.ui.myteam.inprogress.InProgressTeamFragment

class ViewPagerAdapter2(fa: FragmentActivity): FragmentStateAdapter(fa){
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> MemberInfoFragment()
            1 -> MemberEvaluateFragment()
            else -> InProgressTeamFragment()
        }
    }
    override fun getItemCount():Int = 2
}