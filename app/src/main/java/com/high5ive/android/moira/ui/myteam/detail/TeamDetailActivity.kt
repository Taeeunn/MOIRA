package com.high5ive.android.moira.ui.myteam.detail

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.snackbar.Snackbar
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.MemberAdapter
import com.high5ive.android.moira.data.Member
import com.high5ive.android.moira.data.retrofit.MyProjectTeammateResponseDTO
import com.high5ive.android.moira.data.retrofit.MyTeamDetail
import com.high5ive.android.moira.data.retrofit.MyTeamDetailData
import com.high5ive.android.moira.databinding.ActivityTeamDetailBinding
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import com.high5ive.android.moira.ui.teamfinding.user.UserProfileDetailActivity
import kotlinx.android.synthetic.main.activity_team_detail.*
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



    }

    override fun onResume() {
        super.onResume()

        getTeamDetail()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.team_option, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            R.id.name_modify -> {
                Log.v("modifyyj", "modididi")
            }

            R.id.image_modify -> {
                Log.v("modifyyj", "modididi")
            }

            R.id.complete_project -> {
                Log.v("modifyyj", "modididi")
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
            Log.v("index", index.toString())
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
                        val list: List<MyProjectTeammateResponseDTO> = data.myProjectTeammateResponseDTOList

                        Log.v("data", data.toString())

                        binding.myteam = data

                        recycler_view.apply {
                            layoutManager = LinearLayoutManager(this@TeamDetailActivity)
                            adapter =
                                MemberAdapter(list) { member ->
                                    Toast.makeText(this@TeamDetailActivity, "$member", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@TeamDetailActivity, UserProfileDetailActivity::class.java))
                                }
                        }

                    }

                }
            })
        }.run()
    }
}

