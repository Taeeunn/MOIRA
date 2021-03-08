package com.high5ive.android.moira.ui.myteam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.snackbar.Snackbar
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.MemberAdapter
import com.high5ive.android.moira.data.Member
import com.high5ive.android.moira.databinding.ActivityTeamDetailBinding
import com.high5ive.android.moira.ui.teamfinding.user.UserProfileActivity
import kotlinx.android.synthetic.main.activity_team_detail.*


class TeamDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityTeamDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_team_detail
        )

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        //binding = DataBindingUtil.setContentView(this, R.layout.leader_item)
        binding.leader.member = Member("팀장 닉네임", "android 개발자")


        more_button2.setOnClickListener(this)


        val members = arrayListOf<Member>()
        for (i in 0..5) {
            members.add(
                Member(
                    "사용자 닉네임 $i",
                    "개발자 $i"
                )
            )
        }

        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@TeamDetailActivity)
            adapter =
                MemberAdapter(members) { member ->
                    Toast.makeText(this@TeamDetailActivity, "$member", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@TeamDetailActivity, UserProfileActivity::class.java))
                }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.more_button2 -> {
                MaterialDialog(this).show {
                    cornerRadius(4f)
                    title(R.string.complete_project)
                    message(R.string.complete_project_message)

                    positiveButton(R.string.complete) {
                        Snackbar.make(v, R.string.project_completed, Snackbar.LENGTH_SHORT).show()
                    }

                    negativeButton(R.string.cancle)
                }
            }
        }
    }
}

