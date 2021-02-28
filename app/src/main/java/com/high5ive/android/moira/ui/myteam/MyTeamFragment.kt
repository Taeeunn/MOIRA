package com.high5ive.android.moira.ui.myteam

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.high5ive.android.moira.MyTeamViewModel
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.my_team_fragment.*

class MyTeamFragment : Fragment() {

    companion object {
        fun newInstance() = MyTeamFragment()
    }

    private lateinit var viewModel: MyTeamViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.my_team_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyTeamViewModel::class.java)
        // TODO: Use the ViewModel

        viewPager.adapter = MyTeamViewPagerAdapter(context as FragmentActivity)
        val tabLayoutTextArray = arrayOf("진행중인 팀","완료한 팀")
        TabLayoutMediator(tabLayout,viewPager){tab,position->
            tab.text = tabLayoutTextArray[position]

        }.attach()
    }

}