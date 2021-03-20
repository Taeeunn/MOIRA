package com.high5ive.android.moira.ui.initial.position

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.high5ive.android.moira.R
import kotlinx.android.synthetic.main.set_nickname_fragment.*
import kotlinx.android.synthetic.main.set_position_fragment.*
import kotlinx.android.synthetic.main.set_position_fragment.to_next_btn
import kotlinx.android.synthetic.main.set_position_fragment.toolbar

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

        setHasOptionsMenu(true);
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        val ab = (activity as AppCompatActivity?)!!.supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SetPositionViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                Log.v("hello", "hello")
                navController.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}