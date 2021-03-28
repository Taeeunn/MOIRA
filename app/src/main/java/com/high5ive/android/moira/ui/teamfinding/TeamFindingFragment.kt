package com.high5ive.android.moira.ui.teamfinding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.high5ive.android.moira.R
import com.high5ive.android.moira.ui.teamfinding.search.UserPoolSearchActivity
import kotlinx.android.synthetic.main.my_team_fragment.tabLayout
import kotlinx.android.synthetic.main.my_team_fragment.viewPager
import kotlinx.android.synthetic.main.team_finding_fragment.*

class TeamFindingFragment : Fragment() {



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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search_button.setOnClickListener {
            startActivity(Intent(context, UserPoolSearchActivity::class.java))
        }


        viewPager.adapter = TeamFindingViewPagerAdapter(context as FragmentActivity)
        val tabLayoutTextArray = arrayOf("모집글","인재풀")
        TabLayoutMediator(tabLayout,viewPager){tab,position->
            tab.text = tabLayoutTextArray[position]

        }.attach()
    }


}