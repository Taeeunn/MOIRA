package com.high5ive.android.moira.ui.common

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.BadgeAdapter
import com.high5ive.android.moira.data.Badge
import com.high5ive.android.moira.ui.applicant.review.ApplicantReviewActivity
import kotlinx.android.synthetic.main.member_evaluate_fragment.*


class MemberEvaluateFragment : Fragment() {

    companion object {
        fun newInstance() =
            MemberEvaluateFragment()
    }

    private lateinit var viewModel: MemberEvaluateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.member_evaluate_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        show_total_btn.setOnClickListener{
            startActivity(Intent(context, ApplicantReviewActivity::class.java))
        }

        val badgeList = arrayListOf<Badge>()
        for (i in 0..5){
            badgeList.add(
                Badge(
                    "배지명 $i",
                    i+1
                )
            )
        }

        badge_recycler_view.apply{
            layoutManager = LinearLayoutManager(context)
            adapter =
                BadgeAdapter(badgeList) { badge ->
                    Toast.makeText(context, "$badge", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MemberEvaluateViewModel::class.java)
        // TODO: Use the ViewModel




    }

}