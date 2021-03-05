package com.high5ive.android.moira.ui.teamfinding.user

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.MemberAdapter
import com.high5ive.android.moira.adapter.UserAdapter
import com.high5ive.android.moira.data.Member
import com.high5ive.android.moira.data.User
import com.high5ive.android.moira.ui.applicant.ApplicantProfileActivity
import kotlinx.android.synthetic.main.activity_recruit_list.*
import kotlinx.android.synthetic.main.activity_recruit_list.recycler_view
import kotlinx.android.synthetic.main.recruit_post_fragment.*

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

        var tagList = mutableListOf<String>()
        tagList.add("태그명1")
        tagList.add("태그명2")
        tagList.add("태그명3")
        setTag(tagList);


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
                    startActivity(Intent(context, UserProfileActivity::class.java))
                }
        }
    }

    private fun setTag(tagList: MutableList<String>) {
        for (index in tagList.indices) {
            val tagName = tagList[index]
            //val chip = Chip(ContextThemeWrapper(context, R.style.MaterialChipsAction))
            val chip = Chip(context)
            val drawable = context?.let { ChipDrawable.createFromAttributes(it, null, 0, R.style.MaterialChipsAction) }
            if (drawable != null) {
                chip.setChipDrawable(drawable)
            }

            chip.text = tagName
            chip.setTextAppearance(R.style.tag_text)
            chip.setCloseIconResource(R.drawable.ic_baseline_close_24)
            chip.isCloseIconEnabled = true
            //Added click listener on close icon to remove tag from ChipGroup
            chip.setOnCloseIconClickListener {
                tagList.remove(tagName)
                tag_group.removeView(chip)
            }
            tag_group.addView(chip)
        }
    }

}