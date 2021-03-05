package com.high5ive.android.moira.ui.myteam.inprogress

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.RecruitAdapter
import com.high5ive.android.moira.adapter.TeamAdapter
import com.high5ive.android.moira.data.Recruit
import com.high5ive.android.moira.data.Team
import com.high5ive.android.moira.ui.myteam.TeamDetailActivity
import com.high5ive.android.moira.ui.myteam.evaluate.EvaluateMemberActivity
import com.high5ive.android.moira.ui.teamfinding.recruit.RecruitDetailActivity
import kotlinx.android.synthetic.main.in_progress_team_fragment.*
import kotlinx.android.synthetic.main.recruit_post_fragment.*
import kotlinx.android.synthetic.main.recruit_post_fragment.recycler_view

class InProgressTeamFragment : Fragment() {

    companion object {
        fun newInstance() =
            InProgressTeamFragment()
    }

    private lateinit var viewModel: InProgressTeamViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.in_progress_team_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(InProgressTeamViewModel::class.java)
        // TODO: Use the ViewModel

//        spinner.adapter =
//            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, items) }
//


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                when (spinner.getItemAtPosition(position)) {
                    "최신순" -> {
                        Log.v("itemselect", "최신순")
                    }
                    "인기순" -> {
                        Log.v("itemselect", "인기순")
                    }
                    else -> {

                    }
                }
            }

        }


        val teamList = arrayListOf<Team>()
        for (i in 0..30) {
            teamList.add(
                Team(
                    "팀 이름 $i"
                )
            )
        }

        recycler_view.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter =
                TeamAdapter(teamList) { team, type ->

                    Toast.makeText(context, "$team", Toast.LENGTH_SHORT).show()
                    if (type == 0) {
                        startActivity(Intent(context, TeamDetailActivity::class.java))
                    } else if (type == 1) {
                        startActivity(Intent(context, EvaluateMemberActivity::class.java))
                    }
                }
//                TeamAdapter(teamList, team, team){
//
//                }
        }
    }

}