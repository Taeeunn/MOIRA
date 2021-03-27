package com.high5ive.android.moira.ui.myteam.detail

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.high5ive.android.moira.data.retrofit.MyTeamDetail
import com.high5ive.android.moira.data.retrofit.MyTeamDetailData
import com.high5ive.android.moira.databinding.ActivityTeamDetailBinding
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import com.high5ive.android.moira.ui.teamfinding.user.UserProfileDetailActivity
import kotlinx.android.synthetic.main.activity_team_detail.recycler_view
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class TeamDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityTeamDetailBinding
    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String
    var index: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_team_detail
        )

        val preferences: SharedPreferences = this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()

        index = intent.getIntExtra("index", 1)

        initRetrofit()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        //binding = DataBindingUtil.setContentView(this, R.layout.leader_item)

        val members = arrayListOf<Member>()
        members.add(Member("팀장 닉네임", "안드로이드 개발자", "팀장"))
        for (i in 0..5) {
            members.add(
                Member(
                    "사용자 닉네임 $i",
                    "개발자 $i",
                    "팀원"
                )
            )
        }

        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@TeamDetailActivity)
            adapter =
                MemberAdapter(members) { member ->
                    Toast.makeText(this@TeamDetailActivity, "$member", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@TeamDetailActivity, UserProfileDetailActivity::class.java))
                }
        }
    }

    override fun onResume() {
        super.onResume()

        getTeamDetail()
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
            R.id.more_button -> {
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

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        myAPI = retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
    }

    private fun getTeamDetail(){
        Runnable {

            myAPI.getMyTeamDetail(token, index).enqueue(object :
                Callback<MyTeamDetail> {
                override fun onFailure(call: Call<MyTeamDetail>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<MyTeamDetail>, response: Response<MyTeamDetail>) {
                    val code: Int = response.body()?.code ?: 0

                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false

                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)

                    if(succeed){

                        val data: MyTeamDetailData = response.body()?.data!!
                        Log.v("data", data.toString())

                    }

                }
            })
        }.run()
    }
}

