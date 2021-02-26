package com.high5ive.android.moira

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class MemberEvaluateFragment : Fragment() {

    companion object {
        fun newInstance() = MemberEvaluateFragment()
    }

    private lateinit var viewModel: MemberEvaluateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.member_evaluate_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MemberEvaluateViewModel::class.java)
        // TODO: Use the ViewModel
    }

}