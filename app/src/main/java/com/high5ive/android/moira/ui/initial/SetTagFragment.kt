package com.high5ive.android.moira.ui.initial

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.high5ive.android.moira.MainActivity
import com.high5ive.android.moira.R
import kotlinx.android.synthetic.main.set_tag_fragment.*

class SetTagFragment : Fragment() {

    companion object {
        fun newInstance() = SetTagFragment()
    }

    private lateinit var viewModel: SetTagViewModel
    lateinit var navController : NavController
    private var onTransitionListener: OnTransitionListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.set_tag_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        to_next_btn.setOnClickListener {
//            startActivity(Intent(activity, MainActivity::class.java))
            onTransitionListener?.OnTransitionListener()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SetTagViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onTransitionListener = context as OnTransitionListener
    }

    override fun onDetach() {
        super.onDetach()
        onTransitionListener = null
    }

}