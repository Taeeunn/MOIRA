package com.high5ive.android.moira.ui.initial.position

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
    lateinit var position: String
    var develop: Boolean = false
    var plan: Boolean = false
    var design: Boolean = false


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

        develop_btn.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                design_btn.isChecked = false
                plan_btn.isChecked = false
                position = "develop"
            }
        }

        plan_btn.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                develop_btn.isChecked = false
                design_btn.isChecked = false
                position = "plan"
            }
        }

        design_btn.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                develop_btn.isChecked = false
                plan_btn.isChecked = false
                position = "design"
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SetPositionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}