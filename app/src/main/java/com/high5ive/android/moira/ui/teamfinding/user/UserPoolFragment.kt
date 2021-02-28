package com.high5ive.android.moira.ui.teamfinding.user

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.MemberAdapter
import com.high5ive.android.moira.adapter.UserAdapter
import com.high5ive.android.moira.data.Member
import com.high5ive.android.moira.data.User
import kotlinx.android.synthetic.main.activity_recruit_list.*

class UserPoolFragment : Fragment() {

    companion object {
        fun newInstance() = UserPoolFragment()
    }

    private lateinit var viewModel: UserPoolViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.user_pool_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserPoolViewModel::class.java)
        // TODO: Use the ViewModel


        val userList = arrayListOf<User>()
        for (i in 0..5){
            userList.add(
                User(
                    "사용자 닉네임 $i",
                    "개발자 $i",
                    "사용자 한줄소개입니다. 사용자 한줄소개입니다. $i"
                )
            )
        }

        recycler_view.apply{
            layoutManager = LinearLayoutManager(context)
            adapter =
                UserAdapter(userList) { user ->
                    Toast.makeText(context, "$user", Toast.LENGTH_SHORT).show()
                }
        }
    }

}