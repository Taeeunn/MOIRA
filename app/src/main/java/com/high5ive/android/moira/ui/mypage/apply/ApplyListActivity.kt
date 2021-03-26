package com.high5ive.android.moira.ui.mypage.apply

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.ApplyAdapter
import com.high5ive.android.moira.data.Apply
import com.high5ive.android.moira.data.retrofit.ApplyPost
import com.high5ive.android.moira.data.retrofit.ApplyPostItem
import com.high5ive.android.moira.data.retrofit.WrittenPost
import com.high5ive.android.moira.data.retrofit.WrittenPostItem
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import kotlinx.android.synthetic.main.activity_apply_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class ApplyListActivity : AppCompatActivity() {

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        val preferences: SharedPreferences =this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()

        initRetrofit()
        getWrittenPostList()


        val applyList = arrayListOf<Apply>()
        for (i in 0..10){
            applyList.add(Apply("모집글 제목 모집글 제목 모집글 제목 모집글 제목 $i"))
        }

        recycler_view.apply{
            layoutManager = LinearLayoutManager(this@ApplyListActivity)
            adapter =
                ApplyAdapter(applyList) { apply, type ->
                    Toast.makeText(this@ApplyListActivity, "$apply", Toast.LENGTH_SHORT).show()

//                    if (type==0) {
//                        startActivity(Intent(this@ApplyListActivity, RecruitDetailActivity::class.java))
//                    }
//                    else if(type==1){
//                        startActivity(Intent(this@ApplyListActivity, ApplicantListActivity::class.java))
//                    }
                }
        }
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

    private fun getWrittenPostList() {
        Runnable {

            myAPI.getApplyPostList(token).enqueue(object : Callback<ApplyPost> {
                override fun onFailure(call: Call<ApplyPost>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<ApplyPost>, response: Response<ApplyPost>) {
                    val code: Int = response.body()?.code ?: 0

                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false

                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)

                    if(succeed){

                        val list: List<ApplyPostItem> = response.body()?.list ?: emptyList()
                        Log.v("data", list.toString())

                    }

                }
            })
        }.run()
    }
}
