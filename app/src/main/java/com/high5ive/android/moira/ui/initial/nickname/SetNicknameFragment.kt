package com.high5ive.android.moira.ui.initial.nickname

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.high5ive.android.moira.R
import kotlinx.android.synthetic.main.set_nickname_fragment.*


class SetNicknameFragment : Fragment() {

    companion object {
        fun newInstance() =
            SetNicknameFragment()
    }

    private lateinit var viewModel: SetNicknameViewModel

    lateinit var navController : NavController
    lateinit var token_: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.set_nickname_fragment, container, false)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        val preferences: SharedPreferences =requireActivity().getSharedPreferences("moira", Context.MODE_PRIVATE)
        token_ = preferences.getString("token", null).toString()

        Log.v("nicknametoken", token_)

        to_next_btn.setOnClickListener {
            navController.navigate(R.id.action_setNicknameFragment_to_setPositionFragment)
        }

        setHasOptionsMenu(true);
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        val ab = (activity as AppCompatActivity?)!!.supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)
//        enter_nickname_et.setOnClickListener{
//            nickname_text_layout.isErrorEnabled = true;
//            nickname_text_layout.error = "*이미 사용중인 닉네임입니다.";
//        }



//
//        nickname_text_layout.isErrorEnabled = true;
//        nickname_text_layout.error = "*이미 사용중인 닉네임입니다.";
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SetNicknameViewModel::class.java)
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