package com.high5ive.android.moira.ui.initial.tag

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.high5ive.android.moira.R
import com.high5ive.android.moira.ui.initial.OnTransitionListener
import kotlinx.android.synthetic.main.set_nickname_fragment.*
import kotlinx.android.synthetic.main.set_tag_fragment.*
import kotlinx.android.synthetic.main.set_tag_fragment.to_next_btn
import kotlinx.android.synthetic.main.set_tag_fragment.toolbar

class SetTagFragment : Fragment() {

    companion object {
        fun newInstance() =
            SetTagFragment()
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

        setHasOptionsMenu(true);
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        val ab = (activity as AppCompatActivity?)!!.supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)
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