package com.high5ive.android.moira.ui.message

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.ChatAdapter
import com.high5ive.android.moira.adapter.RecruitAdapter
import com.high5ive.android.moira.data.retrofit.Chat
import com.high5ive.android.moira.data.retrofit.ChatItem
import com.high5ive.android.moira.data.retrofit.UserTag
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import com.high5ive.android.moira.ui.teamfinding.recruit.RecruitPostDetailActivity
import kotlinx.android.synthetic.main.activity_message_box.*
import kotlinx.android.synthetic.main.activity_message_box.recycler_view
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MessageBoxActivity : AppCompatActivity() {

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_box)

        val preferences: SharedPreferences = this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()

        initRetrofit()



        close_button.setOnClickListener {
            finish()
        }
    }

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        myAPI = retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
    }

    override fun onResume() {
        super.onResume()

        getChatList()
    }

    private fun getChatList(){
        myAPI.getChatRoom(token).enqueue(object :
            Callback<Chat> {
            override fun onFailure(call: Call<Chat>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Chat>, response: Response<Chat>) {
                val code: Int = response.body()?.code ?: 0

                val msg: String = response.body()?.msg ?: "no msg"
                val succeed: Boolean = response.body()?.succeed ?: false

                Log.v("code", code.toString())
                Log.v("success", succeed.toString())
                Log.v("msg", msg)

                if(succeed){

                    val list: List<ChatItem> = response.body()?.list!!
                    Log.v("list", list.toString())

                    recycler_view.apply{
                        layoutManager = LinearLayoutManager(context)
                        adapter =
                            ChatAdapter(list) { index ->
                                Toast.makeText(context, "$index", Toast.LENGTH_SHORT).show()
                                val intent = Intent(context, MessageHistoryActivity::class.java)
                                intent.putExtra("index", index)
                                startActivity(intent)
                            }
                    }
                }

            }
        })
    }
}