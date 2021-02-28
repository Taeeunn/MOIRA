package com.high5ive.android.moira.ui.myteam.complete

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.TeamAdapter
import com.high5ive.android.moira.data.Team
import kotlinx.android.synthetic.main.recruit_post_fragment.*

class CompleteTeamFragment : Fragment() {

    companion object {
        fun newInstance() = CompleteTeamFragment()
    }

    private lateinit var viewModel: CompleteTeamViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.complete_team_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CompleteTeamViewModel::class.java)
        // TODO: Use the ViewModel

        val teamList = arrayListOf<Team>()
        for (i in 0..30){
            teamList.add(
                Team(
                    "팀 이름 $i"
                )
            )
        }

        recycler_view.apply{
            layoutManager = GridLayoutManager(context, 2)
            adapter =
                TeamAdapter(teamList) { team ->
                    Toast.makeText(context, "$team", Toast.LENGTH_SHORT).show()
                }
        }


    }

}