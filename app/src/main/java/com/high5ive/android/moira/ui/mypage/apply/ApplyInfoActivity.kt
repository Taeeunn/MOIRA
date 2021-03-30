package com.high5ive.android.moira.ui.mypage.apply

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
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.ApplyAdapter
import com.high5ive.android.moira.data.retrofit.ApplyPost
import com.high5ive.android.moira.data.retrofit.ApplyPostItem
import com.high5ive.android.moira.data.retrofit.ProjectApplyDetail
import com.high5ive.android.moira.data.retrofit.ProjectApplyDetailData
import com.high5ive.android.moira.databinding.ActivityApplyInfoBinding
import com.high5ive.android.moira.databinding.ActivityRecruitPostDetailBinding
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import kotlinx.android.synthetic.main.activity_apply_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ApplyInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApplyInfoBinding
    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String
    var index: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_apply_info
        )

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        val preferences: SharedPreferences =
            this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()

        index = intent.getIntExtra("index", 1)

        initRetrofit()

        getApplyInfo()


    }

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        myAPI =
            retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
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

    private fun getApplyInfo(){

        Log.v("index", index.toString())
        myAPI.getProjectApplyDetail(token, index).enqueue(object : Callback<ProjectApplyDetail> {
            override fun onFailure(call: Call<ProjectApplyDetail>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ProjectApplyDetail>, response: Response<ProjectApplyDetail>) {
                val code: Int = response.body()?.code ?: 0

                val msg: String = response.body()?.msg ?: "no msg"
                val succeed: Boolean = response.body()?.succeed ?: false

                Log.v("code", code.toString())
                Log.v("success", succeed.toString())
                Log.v("msg", msg)

                if (succeed) {

                    val data: ProjectApplyDetailData = response.body()?.data!!
                    Log.v("data", data.toString())


                    binding.applyinfo = data

                    Glide.with(this@ApplyInfoActivity)
                        .load(data.imageUrl)
                        .override(20, 20)
                        .error(R.drawable.ic_baseline_person_24) // ex) error(R.drawable.error)
                        .into(binding.memberImage)


//                    recycler_view.apply {
//                        layoutManager = LinearLayoutManager(this@ApplyListActivity)
//                        adapter =
//                            ApplyAdapter(list) { index, type ->
//                                Toast.makeText(
//                                    this@ApplyListActivity,
//                                    "$index",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//
//                                if (type == 0) {
//                                    val intent = Intent(
//                                        this@ApplyListActivity,
//                                        ApplyInfoActivity::class.java
//                                    )
//                                    intent.putExtra("index", index)
//                                    startActivity(intent)
//
//                                    // 지원 취소
//                                } else if (type == 1) {
//                                    Log.v("index", index.toString())
//                                    MaterialDialog(this@ApplyListActivity).show {
//                                        title(R.string.project_duration)
//                                        message(R.string.ask_apply_cancel)
//                                        negativeButton(R.string.close)
//                                        positiveButton(R.string.apply_cancel) {
//                                            applyCancle(index)
//
//
//                                        }
//
//
//                                    }
//                                }
//                            }
//                    }
                }

            }

        })
    }
}