package com.high5ive.android.moira.ui.mypage

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.BadgeAdapter
import com.high5ive.android.moira.data.retrofit.UserPoolDetailReview
import com.high5ive.android.moira.data.retrofit.UserPoolDetailReviewData
import com.high5ive.android.moira.databinding.ActivityMyReviewBinding
import com.high5ive.android.moira.databinding.MemberReviewFragmentBinding
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import com.high5ive.android.moira.ui.applicant.review.ApplicantReviewActivity
import kotlinx.android.synthetic.main.member_review_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MyReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyReviewBinding
    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String
    var index: Int = 1
    var point: Float = 0.0F


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_my_review
        )

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)


        val preferences: SharedPreferences = this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()

        index = intent.getIntExtra("index", 1)

        initRetrofit()

        getUserProfileReview()

        show_total_btn.setOnClickListener{

            val intent = Intent(this, ApplicantReviewActivity::class.java)
            intent.putExtra("index", index)
            intent.putExtra("point", point)
            startActivity(intent)
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

    private fun getUserProfileReview() {
        Runnable {

            myAPI.getUserPoolDetailReview(token, index).enqueue(object :
                Callback<UserPoolDetailReview> {
                override fun onFailure(call: Call<UserPoolDetailReview>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<UserPoolDetailReview>, response: Response<UserPoolDetailReview>) {
                    val code: Int = response.body()?.code ?: 0

                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false

                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)

                    if(succeed){

                        val data: UserPoolDetailReviewData = response.body()?.data!!
                        Log.v("data", data.toString())

                        binding.userreview = data

                        point = data.avgMannerPoint

                        if(data.maxComplimentMarkId == 1) {
                            binding.text2.text = "프로페셔널한 모습"
                            binding.bestBadgeImage.setImageResource(R.drawable.badge_1)
                        } else if(data.maxComplimentMarkId == 2){
                            binding.text2.text = "적극적인 참여도"
                            binding.bestBadgeImage.setImageResource(R.drawable.badge_2)
                        }else{
                            binding.text2.text = "커뮤니케이션의 달인"
                            binding.bestBadgeImage.setImageResource(R.drawable.badge_3)
                        }


                        badge_recycler_view.apply{
                            layoutManager = LinearLayoutManager(context)
                            adapter =
                                BadgeAdapter(data.complimentMarkWithCountDtoList)
                        }
                    }

                }
            })
        }.run()
    }
}