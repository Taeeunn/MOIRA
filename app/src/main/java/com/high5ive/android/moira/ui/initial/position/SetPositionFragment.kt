package com.high5ive.android.moira.ui.initial.position

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.high5ive.android.moira.R
import kotlinx.android.synthetic.main.set_position_fragment.*

class SetPositionFragment : Fragment() {

    companion object {
        fun newInstance() =
            SetPositionFragment()
    }

    private lateinit var viewModel: SetPositionViewModel

    lateinit var navController : NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.set_position_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        to_next_btn.setOnClickListener {
            navController.navigate(R.id.action_setPositionFragment_to_setTagFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SetPositionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}