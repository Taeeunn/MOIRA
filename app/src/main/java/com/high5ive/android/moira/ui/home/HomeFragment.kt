package com.high5ive.android.moira.ui.home

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.high5ive.android.moira.HomeViewModel
import com.high5ive.android.moira.R
import com.high5ive.android.moira.ui.message.MessageBoxActivity
import com.high5ive.android.moira.ui.notify.NotifyActivity
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noti_frame.setOnClickListener {
            startActivity(Intent(context, NotifyActivity::class.java))
        }

        chat_frame.setOnClickListener {
            startActivity(Intent(context, MessageBoxActivity::class.java))
        }
    }

}