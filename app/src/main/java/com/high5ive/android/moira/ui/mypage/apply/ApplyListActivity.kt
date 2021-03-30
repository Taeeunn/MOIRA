package com.high5ive.android.moira.ui.mypage.apply

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.snackbar.Snackbar
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.ApplyAdapter
import com.high5ive.android.moira.data.retrofit.ApplyPost
import com.high5ive.android.moira.data.retrofit.ApplyPostItem
import com.high5ive.android.moira.data.retrofit.ResponseData
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

        val preferences: SharedPreferences =
            this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()

        initRetrofit()

    }

    override fun onResume() {
        super.onResume()

        getWrittenPostList()
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

    private fun getWrittenPostList() {

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

                if (succeed) {

                    val list: List<ApplyPostItem> = response.body()?.list ?: emptyList()
                    Log.v("data", list.toString())


                    recycler_view.apply {
                        layoutManager = LinearLayoutManager(this@ApplyListActivity)
                        adapter =
                            ApplyAdapter(list) { index, type ->
                                Toast.makeText(
                                    this@ApplyListActivity,
                                    "$index",
                                    Toast.LENGTH_SHORT
                                ).show()

                                if (type == 0) {
                                    val intent = Intent(
                                        this@ApplyListActivity,
                                        ApplyInfoActivity::class.java
                                    )
                                    intent.putExtra("index", index)
                                    startActivity(intent)

                                    // 지원 취소
                                } else if (type == 1) {
                                    Log.v("index", index.toString())
                                    MaterialDialog(this@ApplyListActivity).show {
                                        title(R.string.project_duration)
                                        message(R.string.ask_apply_cancel)
                                        negativeButton(R.string.close)
                                        positiveButton(R.string.apply_cancel) {
                                            applyCancle(index)


                                        }


                                    }
                                }
                            }
                    }
                }

            }

        })
    }

    private fun applyCancle(index: Int) {
        myAPI.cancelProjectApply(token, index).enqueue(object :
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

                if (succeed) {
                    val message = "지원을 취소했습니다. "
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        message,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    getWrittenPostList()

                }

            }
        })
    }
}

