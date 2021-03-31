package com.high5ive.android.moira.ui.teamfinding.search

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.RecruitAdapter
import com.high5ive.android.moira.data.retrofit.RecruitPost
import com.high5ive.android.moira.data.retrofit.RecruitPostItem
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import com.high5ive.android.moira.ui.teamfinding.recruit.RecruitPostDetailActivity
import kotlinx.android.synthetic.main.activity_recruit_post_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RecruitPostSearchActivity : AppCompatActivity(), View.OnClickListener{
    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recruit_post_search)

        val preferences: SharedPreferences = this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()

        initRetrofit()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        search_button.setOnClickListener(this)

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

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.search_button -> {
                searchRecruitPost(v)
            }
        }
    }

    private fun searchRecruitPost(v: View) {
        val keyword = searchView.query.toString()


        myAPI.getRecruitPostList(token, keyword, null, null, null, null).enqueue(object :
            Callback<RecruitPost> {
            override fun onFailure(call: Call<RecruitPost>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<RecruitPost>, response: Response<RecruitPost>) {
                val code: Int = response.body()?.code ?: 0

                val msg: String = response.body()?.msg ?: "no msg"
                val succeed: Boolean = response.body()?.succeed ?: false

                Log.v("code", code.toString())
                Log.v("success", succeed.toString())
                Log.v("msg", msg)

                if(succeed){

                    val list: List<RecruitPostItem> = response.body()?.list!!
                    Log.v("data", list.toString())

                    if(list.isEmpty()){
                        val msg = "해당 키워드를 가진 모집글이 없습니다."
                        Snackbar.make(v, msg, Snackbar.LENGTH_SHORT).show()
                    }

                    search_text.text = "'" + keyword + "' 검색 내역"
                    recycler_view.apply {
                        layoutManager = LinearLayoutManager(this@RecruitPostSearchActivity)
                        adapter = RecruitAdapter(list) { index ->
//                            Toast.makeText(this@RecruitPostSearchActivity, "$index", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@RecruitPostSearchActivity, RecruitPostDetailActivity::class.java)
                            intent.putExtra("index", index)
                            startActivity(intent)
                        }


                    }

                }

            }
        })
    }
}