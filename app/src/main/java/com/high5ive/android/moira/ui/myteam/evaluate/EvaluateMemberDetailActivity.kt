package com.high5ive.android.moira.ui.myteam.evaluate

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.*
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import kotlinx.android.synthetic.main.activity_evaluate_member_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class EvaluateMemberDetailActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String
    var index: Int = 1
    var image: String = ""
    var nickname: String = ""
    var complimentMarkIdList = arrayListOf<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluate_member_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        val preferences: SharedPreferences =
            this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()

        index = intent.getIntExtra("index", 1)
        nickname = intent.getStringExtra("nickname") ?: ""
        image = intent.getStringExtra("image") ?: ""

        initRetrofit()

        Glide.with(this)
            .load(image)
            .override(20, 20)
            .error(R.drawable.ic_baseline_person_24) // ex) error(R.drawable.error)
            .into(member_image)

        member_nickname.text = nickname



        complete_button.setOnClickListener(this)
        badge_image1.setOnClickListener(this)
        badge_image2.setOnClickListener(this)
        badge_image3.setOnClickListener(this)

    }

    override fun onResume() {
        super.onResume()

        getComplimentList()
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
            R.id.complete_button -> {

                completeTeamMemberReview(v, index)

            }
            R.id.badge_image1 -> {
                if (badge_image1.isChecked) {
                    complimentMarkIdList.add(1)
                }else{
                    complimentMarkIdList.remove(1)
                }
            }

            R.id.badge_image2 -> {
                if (badge_image2.isChecked) {
                    complimentMarkIdList.add(2)
                }else{
                    complimentMarkIdList.remove(2)
                }
            }

            R.id.badge_image3 -> {
                if (badge_image3.isChecked) {
                    complimentMarkIdList.add(3)
                }else{
                    complimentMarkIdList.remove(3)
                }
            }

        }
    }

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        myAPI = retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
    }


    private fun completeTeamMemberReview(v: View, index: Int) {
        Runnable {
            val mannerPoint = ratingBar.rating.toInt()
            val reviewContent = review_et.text.toString()

            var body_data =
                UserReviewAddRequestDto(complimentMarkIdList, mannerPoint, reviewContent, index)

            Log.v("hhh", complimentMarkIdList.toString())


            myAPI.reviewTeamMember(token, body_data).enqueue(object :
                Callback<TeamMemberReview> {
                override fun onFailure(call: Call<TeamMemberReview>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<TeamMemberReview>,
                    response: Response<TeamMemberReview>
                ) {
                    val code: Int = response.body()?.code ?: 0

                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false

                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)

                    if (succeed) {

                        val data: TeamMemberReviewData = response.body()?.data!!
                        Log.v("data", data.toString())

                        val message = "팀원 평가를 완료했습니다!"
                        Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()
                        finish()

                    }

                }
            })
        }.run()
    }

    private fun getComplimentList() {
        Runnable {

            myAPI.getComplimentList(token).enqueue(object :
                Callback<Compliment> {
                override fun onFailure(call: Call<Compliment>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<Compliment>, response: Response<Compliment>) {
                    val code: Int = response.body()?.code ?: 0

                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false

                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)

                    if (succeed) {

                        val list: List<ComplimentItem> = response.body()?.list ?: emptyList()
                        Log.v("data", list.toString())

                    }

                }
            })
        }.run()
    }
}