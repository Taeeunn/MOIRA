package com.high5ive.android.moira.ui.myteam.evaluate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.MemberAdapter
import com.high5ive.android.moira.data.Member
import com.high5ive.android.moira.databinding.ActivityEvaluateMemberBinding
import kotlinx.android.synthetic.main.activity_evaluate_member.*
import kotlinx.android.synthetic.main.activity_evaluate_member_detail.*


class EvaluateMemberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEvaluateMemberBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,
            R.layout.activity_evaluate_member
        )

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        //binding = DataBindingUtil.setContentView(this, R.layout.leader_item)
        binding.leader.member=
            Member("팀장 닉네임", "android 개발자")

        leader.setOnClickListener{
            startActivity(Intent(this, EvaluateMemberDetailActivity::class.java))
        }

//        complete_button.setOnClickListener{
//            startActivity(Intent(this, EvaluateMemberActivity::class.java))
//        }

        val members = arrayListOf<Member>()
        for (i in 0..5){
            members.add(
                Member(
                    "사용자 닉네임 $i",
                    "개발자 $i"
                )
            )
        }

        recycler_view.apply{
            layoutManager = LinearLayoutManager(this@EvaluateMemberActivity)
            adapter =
                MemberAdapter(members) { member ->
                    Toast.makeText(this@EvaluateMemberActivity, "$member", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(context, EvaluateMemberDetailActivity::class.java))
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
}

