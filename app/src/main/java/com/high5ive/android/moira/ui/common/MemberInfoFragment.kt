package com.high5ive.android.moira.ui.common


import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.AwardAdapter
import com.high5ive.android.moira.data.Award
import kotlinx.android.synthetic.main.member_info_fragment.*

class MemberInfoFragment : Fragment() {

    companion object {
        fun newInstance() =
            MemberInfoFragment()
    }

    private lateinit var viewModel: MemberInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.member_info_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val awardList = arrayListOf<Award>()
        for (i in 0..5){
            awardList.add(
                Award(
                    "가나다 공모전 $i",
                    "최우수상 $i"
                )
            )
        }

        award_recycler_view.apply{
            layoutManager = LinearLayoutManager(context)
            adapter =
                AwardAdapter(awardList) { award ->
                    Toast.makeText(context, "$award", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MemberInfoViewModel::class.java)
        // TODO: Use the ViewModel


    }

}