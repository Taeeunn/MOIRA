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
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.ProjectTeammateAdapter
import com.high5ive.android.moira.data.retrofit.*
import com.high5ive.android.moira.databinding.ActivityTeamDetailBinding
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import com.high5ive.android.moira.ui.teamfinding.user.UserProfileDetailActivity
import kotlinx.android.synthetic.main.activity_recruit_post_detail.*
import kotlinx.android.synthetic.main.activity_team_detail.*
import kotlinx.android.synthetic.main.activity_team_detail.more_button
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

        more_button.setOnClickListener(this)

        //binding = DataBindingUtil.setContentView(this, R.layout.leader_item)



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
                        completeProject(v)

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

                        if(data.leader){
                            more_button.visibility=View.VISIBLE
                        } else{
                            more_button.visibility=View.GONE
                        }

                        if(data.imageUrl==null){
                            team_image.setImageResource(R.drawable.ic_baseline_public_24)
                        } else {
                            Glide.with(this@TeamDetailActivity)
                                .load(data.imageUrl[0])
                                .error(R.drawable.ic_baseline_public_24)
                                .override(100, 100)
                                .into(team_image)
                        }


                        recycler_view.apply {
                            layoutManager = LinearLayoutManager(this@TeamDetailActivity)
                            adapter =
                                ProjectTeammateAdapter(list) { member, type ->


                                    if(type==0){
                                        Toast.makeText(this@TeamDetailActivity, "리더는 클릭하실 수 없습니다!", Toast.LENGTH_SHORT).show()
                                    }
                                    if(type==1) {
                                        val intent = Intent(
                                            this@TeamDetailActivity,
                                            UserProfileDetailActivity::class.java
                                        )
                                        intent.putExtra("nickname", member.nickname)
                                        intent.putExtra("position", member.position)
                                        intent.putExtra("image", member.imageUrl)
                                        intent.putExtra("index1", member.userId)
                                        intent.putExtra("index2", member.projectApplyId)

                                        if (member.imageUrl != null) {
                                            intent.putExtra("image", member.imageUrl)
                                        }
                                        startActivity(intent)
                                    }
                                }
                        }

                    }

                }
            })
        }.run()
    }

    private fun completeProject(v: View){

        var body_data = ProjectModifyStatusRequestDTO("COMPLETED")
        myAPI.editProjectStatus(token, index, body_data).enqueue(object :
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

                    val message = "프로젝트를 완료했습니다!"
                    Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()

                }

            }
        })
    }


}

