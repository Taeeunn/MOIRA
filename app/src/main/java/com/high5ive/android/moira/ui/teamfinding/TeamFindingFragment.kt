package com.high5ive.android.moira.ui.teamfinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.high5ive.android.moira.R
import kotlinx.android.synthetic.main.my_team_fragment.*

class TeamFindingFragment : Fragment() {

    companion object {
        fun newInstance() =
            TeamFindingFragment()
    }

    private lateinit var viewModel: TeamFindingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.team_finding_fragment, container, false)
//        val toolbar = rootView.findViewById<Toolbar>(R.id.toolbar)
//        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
//        val ab = supportActionBar!!
//        ab.setDisplayShowTitleEnabled(false)
//        ab.setDisplayHomeAsUpEnabled(true)


        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TeamFindingViewModel::class.java)
        // TODO: Use the ViewModel

        viewPager.adapter = TeamFindingViewPagerAdapter(context as FragmentActivity)
        val tabLayoutTextArray = arrayOf("모집글","인재풀")
        TabLayoutMediator(tabLayout,viewPager){tab,position->
            tab.text = tabLayoutTextArray[position]

        }.attach()
    }

}