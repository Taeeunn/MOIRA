package com.high5ive.android.moira.ui.applicant.review

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.ReviewAdapter
import com.high5ive.android.moira.data.retrofit.ApplyUserReviewAll
import com.high5ive.android.moira.data.retrofit.ApplyUserReviewAllItem
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import kotlinx.android.synthetic.main.activity_applicant_review.*
import kotlinx.android.synthetic.main.activity_applicant_review.rating
import kotlinx.android.synthetic.main.activity_applicant_review.ratingBar
import kotlinx.android.synthetic.main.activity_applicant_review.recycler_view
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ApplicantReviewActivity : AppCompatActivity() {

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String
    var sort_filter: String = "date"

    var index: Int = 1
    var point: Float = 0.0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_applicant_review)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        val preferences: SharedPreferences = this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()

        index = intent.getIntExtra("index", 1)
        point = intent.getFloatExtra("point", 0.0F)


        rating.text = point.toString()
        ratingBar.rating = point

        initRetrofit()

        getUserReview()

    }

    override fun onResume() {
        super.onResume()

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                when (spinner.getItemAtPosition(position)) {
                    "최신순" -> {
                        if(sort_filter != "date") {
                            sort_filter = "date"
                            getUserReview()
                        }

                    }
                    "평점순" -> {
                        if(sort_filter != "point") {
                            sort_filter = "point"
                            getUserReview()
                        }
                    }

                    else -> {

                    }
                }
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

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        myAPI = retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
    }

    private fun getUserReview() {
        myAPI.getApplyUserReviewAll(token, index, sort_filter).enqueue(object :
            Callback<ApplyUserReviewAll> {
            override fun onFailure(call: Call<ApplyUserReviewAll>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ApplyUserReviewAll>, response: Response<ApplyUserReviewAll>) {
                val code: Int = response.body()?.code ?: 0

                val msg: String = response.body()?.msg ?: "no msg"
                val succeed: Boolean = response.body()?.succeed ?: false

                Log.v("code", code.toString())
                Log.v("success", succeed.toString())
                Log.v("msg", msg)

                if(succeed){

                    val list: List<ApplyUserReviewAllItem> = response.body()?.list!!
                    Log.v("data", list.toString())

                    review_count.text = list.size.toString()

                    recycler_view.apply{
                        layoutManager = LinearLayoutManager(this@ApplicantReviewActivity)
                        adapter =
                            ReviewAdapter(list)
                    }
                }
            }
        })
    }
}
