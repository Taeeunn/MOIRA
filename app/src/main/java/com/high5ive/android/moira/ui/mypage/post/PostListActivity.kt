package com.high5ive.android.moira.ui.mypage.post

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.PostAdapter
import com.high5ive.android.moira.data.retrofit.*
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import com.high5ive.android.moira.ui.applicant.list.ApplicantListActivity
import kotlinx.android.synthetic.main.activity_post_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class PostListActivity : AppCompatActivity() {

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        val preferences: SharedPreferences =this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()

        initRetrofit()
        getWrittenPostList()


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

            myAPI.getWrittenPostList(token).enqueue(object : Callback<WrittenPost> {
                override fun onFailure(call: Call<WrittenPost>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<WrittenPost>, response: Response<WrittenPost>) {
                    val code: Int = response.body()?.code ?: 0

                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false

                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)

                    if(succeed){

                        val list: List<WrittenPostItem> = response.body()?.list ?: emptyList()
                        Log.v("data", list.toString())

                        recycler_view.apply{
                            layoutManager = LinearLayoutManager(this@PostListActivity)
                            adapter =
                                PostAdapter(list) { post ->
                                    val intent =Intent(this@PostListActivity, ApplicantListActivity::class.java)
                                    intent.putExtra("index", post.projectId)
                                    intent.putExtra("time", post.writtenTime)
                                    intent.putExtra("imageurl", post.projectImageUrl)
                                    intent.putExtra("hit", post.hitCount)
                                    intent.putExtra("title", post.projectTitle)

                                    startActivity(intent)

                                }
                        }

                    }

                }
            })
        }.run()
    }
}
