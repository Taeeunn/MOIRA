package com.high5ive.android.moira.ui.applicant.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.MemberAdapter
import com.high5ive.android.moira.data.Member
import com.high5ive.android.moira.ui.applicant.profile.ApplicantProfileActivity
import kotlinx.android.synthetic.main.activity_applicant_list.*


class ApplicantListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_applicant_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        val members = arrayListOf<Member>()
        for (i in 0..10){
            members.add(
                Member(
                    "사용자 닉네임 $i",
                    "개발자 $i"
                )
            )
        }

        recycler_view.apply{
            layoutManager = LinearLayoutManager(this@ApplicantListActivity)
            adapter =
                MemberAdapter(members) { member ->
                    Toast.makeText(this@ApplicantListActivity, "$member", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@ApplicantListActivity, ApplicantProfileActivity::class.java))
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