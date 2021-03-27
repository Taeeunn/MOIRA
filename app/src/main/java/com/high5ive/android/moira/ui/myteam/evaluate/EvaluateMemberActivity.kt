package com.high5ive.android.moira.ui.myteam.evaluate

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.MemberAdapter
import com.high5ive.android.moira.data.Member
import com.high5ive.android.moira.data.retrofit.MyTeam
import com.high5ive.android.moira.data.retrofit.MyTeamItem
import com.high5ive.android.moira.data.retrofit.TeamMember
import com.high5ive.android.moira.data.retrofit.TeamMemberItem
import com.high5ive.android.moira.databinding.ActivityEvaluateMemberBinding
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import com.high5ive.android.moira.ui.myteam.detail.TeamDetailActivity
import kotlinx.android.synthetic.main.activity_evaluate_member.*
import kotlinx.android.synthetic.main.activity_evaluate_member_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class EvaluateMemberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEvaluateMemberBinding
    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String
    var index: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluate_member)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        val preferences: SharedPreferences = this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()

        index = intent.getIntExtra("index", 1)

        initRetrofit()


        val members = arrayListOf<Member>()

        members.add(Member("팀장 닉네임", "안드로이드개발자", "팀장"))

        for (i in 0..5){
            members.add(
                Member(
                    "사용자 닉네임 $i",
                    "개발자 $i",
                    "팀원"
                )
            )
        }

//        recycler_view.apply{
//            layoutManager = LinearLayoutManager(this@EvaluateMemberActivity)
//            adapter =
//                MemberAdapter(members) { index ->
//                    Toast.makeText(this@EvaluateMemberActivity, "$index", Toast.LENGTH_SHORT)
//                        .show()
//
//                    val intent = Intent(context, EvaluateMemberDetailActivity::class.java)
//                    intent.putExtra("index", index)
//                    startActivity(intent)
//                }
//        }
    }

    override fun onResume() {
        super.onResume()

        getTeamMemberList(index)
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

    private fun getTeamMemberList(index: Int){
        Runnable {

            myAPI.getTeamMemberList(token, index).enqueue(object :
                Callback<TeamMember> {
                override fun onFailure(call: Call<TeamMember>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<TeamMember>, response: Response<TeamMember>) {
                    val code: Int = response.body()?.code ?: 0

                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false

                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)

                    if(succeed){

                        val list: List<TeamMemberItem> = response.body()?.list ?: emptyList()
                        Log.v("data", list.toString())

                    }

                }
            })
        }.run()
    }
}

