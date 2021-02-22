package com.high5ive.android.moira

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.high5ive.android.moira.adapter.ReviewAdapter
import com.high5ive.android.moira.data.Review
import kotlinx.android.synthetic.main.activity_recruit_list.*

class ApplicantReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_applicant_review)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        val reviewList = arrayListOf<Review>()
        for (i in 0..30){
            reviewList.add(
                Review(
                    "사용자 닉네임 $i",
                    i * 0.16,
                    "최고의 팀원입니다. 최고의 팀원입니다. 최고의 팀원입니다. 최고의 팀원입니다."
                )
            )
        }

        recycler_view.apply{
            layoutManager = LinearLayoutManager(this@ApplicantReviewActivity)
            adapter =
                ReviewAdapter(reviewList) { person ->
                    Toast.makeText(this@ApplicantReviewActivity, "$person", Toast.LENGTH_SHORT)
                        .show()
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
