package com.high5ive.android.moira.ui.applicant.list

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.ApplyMemberAdapter
import com.high5ive.android.moira.data.retrofit.*

import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import com.high5ive.android.moira.ui.applicant.profile.ApplicantProfileActivity
import com.high5ive.android.moira.ui.teamfinding.recruit.RecruitPostDetailActivity

import kotlinx.android.synthetic.main.activity_applicant_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class ApplicantListActivity : AppCompatActivity(), View.OnClickListener{


    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String
    var index: Int = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_applicant_list)


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        val preferences: SharedPreferences = this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()

        index = intent.getIntExtra("index", 1)

        val time = intent.getStringExtra("time")?: ""
        val hit = intent.getIntExtra("hit", 0)
        val imageurl = intent.getStringExtra("imageurl")?: ""
        val title = intent.getStringExtra("title")?: ""


        initRetrofit()

        move_to_contest.setOnClickListener(this)
        complete_button.setOnClickListener(this)

        views.text = hit.toString()
        recruit_title.text = title
        date.text = time


        Glide.with(this@ApplicantListActivity)
            .load(imageurl)
            .error(R.drawable.ic_baseline_public_24)
            .override(60, 60)
            .into(contest_image)

        getApplicantList()

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



    private fun getApplicantList(){


        myAPI.getProjectApplyUserList(token, index).enqueue(object :
            Callback<ProjectApplyUser> {
            override fun onFailure(call: Call<ProjectApplyUser>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ProjectApplyUser>, response: Response<ProjectApplyUser>) {

                val succeed: Boolean = response.body()?.succeed ?: false

                if(succeed){

                    val list: List<ProjectApplyUserItem> = response.body()?.list!!

                    recruit_count.text = list.size.toString()

                    recycler_view.apply{
                        layoutManager = LinearLayoutManager(this@ApplicantListActivity)
                        adapter =
                            ApplyMemberAdapter(list) { applyuser ->
//                                Toast.makeText(this@ApplicantListActivity, "$applyuser", Toast.LENGTH_SHORT).show()

                                val intent =Intent(this@ApplicantListActivity, ApplicantProfileActivity::class.java)
                                intent.putExtra("applyId", applyuser.projectApplyId)
                                intent.putExtra("userId", applyuser.userId)
                                intent.putExtra("imageurl", applyuser.imageUrl)
                                intent.putExtra("nickname", applyuser.nickname)
                                intent.putExtra("introduction", applyuser.shortIntroduction)

                                startActivity(intent)
                            }
                    }

                }

            }
        })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.move_to_contest -> {
                val intent = Intent(this, RecruitPostDetailActivity::class.java)
                intent.putExtra("index", index)
                startActivity(intent)
            }

            R.id.complete_button -> {
                progressProject(v)
            }
        }
    }

    private fun progressProject(v: View){

        val body_data = ProjectModifyStatusRequestDTO("PROGRESSING")
        myAPI.editProjectStatus(token, index, body_data).enqueue(object :
            Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {

                val succeed: Boolean = response.body()?.succeed ?: false
                if(succeed){

                    val message = "프로젝트를 시작합니다!"
                    Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()
                }

            }
        })
    }
}