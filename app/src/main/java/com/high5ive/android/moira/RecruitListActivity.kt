package com.high5ive.android.moira

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.high5ive.android.moira.adapter.RecruitAdapter
import com.high5ive.android.moira.data.Recruit
import kotlinx.android.synthetic.main.activity_recruit_list.*

class RecruitListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recruit_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        val recruit = arrayListOf<Recruit>()
        for (i in 0..30){
            recruit.add(
                Recruit(
                    "모집글 $i",
                    "닉네임 $i"
                )
            )
        }

        recycler_view.apply{
            layoutManager = LinearLayoutManager(this@RecruitListActivity)
            adapter =
                RecruitAdapter(recruit) { person ->
                    Toast.makeText(this@RecruitListActivity, "$person", Toast.LENGTH_SHORT).show()
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


