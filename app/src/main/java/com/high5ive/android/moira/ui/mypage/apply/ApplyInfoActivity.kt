package com.high5ive.android.moira.ui.mypage.apply

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.*
import com.high5ive.android.moira.data.retrofit.ProjectApplyDetail
import com.high5ive.android.moira.data.retrofit.ProjectApplyDetailData
import com.high5ive.android.moira.databinding.ActivityApplyInfoBinding
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import kotlinx.android.synthetic.main.edit_info.*
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

                    career_recycler_view.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter =
                            CareerAdapter(data.userCareerResponseDtoList)
                    }

                    link_recycler_view.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter =
                            LinkAdapter(data.userLinkResponseDtoList)
                    }

                    certificate_recycler_view.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter =
                            CertificateAdapter(data.userLicenseResponseDtoList)
                    }

                    award_recycler_view.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter =
                            AwardAdapter(data.userAwardResponseDtoList)
                    }

                }
            }

        })
    }
}