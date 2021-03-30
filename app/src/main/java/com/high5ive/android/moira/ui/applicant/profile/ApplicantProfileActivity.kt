package com.high5ive.android.moira.ui.applicant.profile

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.high5ive.android.moira.R
import com.high5ive.android.moira.common.MemberViewPagerAdapter
import com.high5ive.android.moira.data.retrofit.Alarm
import com.high5ive.android.moira.data.retrofit.AlarmItem
import com.high5ive.android.moira.data.retrofit.ProjectApplyModifyStatus
import com.high5ive.android.moira.data.retrofit.ResponseData
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import kotlinx.android.synthetic.main.activity_applicant_profile.*
import kotlinx.android.synthetic.main.my_team_fragment.*
import kotlinx.android.synthetic.main.my_team_fragment.tabLayout
import kotlinx.android.synthetic.main.my_team_fragment.viewPager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ApplicantProfileActivity : AppCompatActivity(), View.OnClickListener{

    var applyId: Int = 1
    var userId: Int = 1
    var imageUrl: String = ""
    var nickname: String = ""
    var introduction: String = ""

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_applicant_profile)

        applyId = intent.getIntExtra("applyId", 1)
        userId = intent.getIntExtra("userId", 1)
        imageUrl = intent.getStringExtra("imageurl")?: ""
        nickname = intent.getStringExtra("nickname")?: ""
        introduction = intent.getStringExtra("introduction")?: ""

        member_nickname.text = nickname
        member_intro.text = introduction

        Glide.with(this)
            .load(imageUrl)
            .error(R.drawable.ic_baseline_person_24) // ex) error(R.drawable.error)
            .into(member_image)

        val preferences: SharedPreferences = this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()
        Log.v("token", token)
        initRetrofit()

        viewPager.adapter =
            MemberViewPagerAdapter(this, applyId, userId)
        val tabLayoutTextArray = arrayOf("사용자 정보","사용자 평가")
        TabLayoutMediator(tabLayout,viewPager){tab,position->
            tab.text = tabLayoutTextArray[position]

        }.attach()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)


        accept_button.setOnClickListener(this)
    }

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        myAPI = retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
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

    private fun acceptUser(v: View){

        val status = "TEAM_INVITED"
        val body_data = ProjectApplyModifyStatus(status)
        Log.v("gggg", applyId.toString())
        myAPI.modifyProjectApplyStatus(token, applyId, body_data).enqueue(object :
            Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val code: Int = response.body()?.code ?: 0

                val msg: String = response.body()?.msg ?: "no msg"
                val succeed: Boolean = response.body()?.succeed ?: false

                Log.v("code", code.toString())
                Log.v("success", succeed.toString())
                Log.v("msg", msg)

                if(succeed){
                    val message = "유저를 팀원으로 수락했어요!"
                    Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()
                }

            }
        })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.accept_button -> {
                acceptUser(v)
            }
        }
    }
}