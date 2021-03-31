package com.high5ive.android.moira.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.high5ive.android.moira.R
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        card1.setOnClickListener {
            startActivity(Intent(context, ProjectStartActivity::class.java))
        }

        card2.setOnClickListener {
            startActivity(Intent(context, MoiraDescriptionActivity::class.java))
        }
    }

}