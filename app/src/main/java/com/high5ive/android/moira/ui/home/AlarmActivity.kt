package com.high5ive.android.moira.ui.home

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.Alarm
import com.high5ive.android.moira.data.retrofit.AlarmItem
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import kotlinx.android.synthetic.main.activity_alarm.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class AlarmActivity : AppCompatActivity() {

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        val preferences: SharedPreferences = this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()
        Log.v("token", token)
        initRetrofit()

        close_button.setOnClickListener() {
            finish()
        }


    }

    override fun onResume() {
        super.onResume()

        getAlarm()
    }

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        myAPI = retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
    }

    private fun getAlarm() {
        myAPI.getAlarm(token, 1).enqueue(object :
            Callback<Alarm> {
            override fun onFailure(call: Call<Alarm>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<Alarm>, response: Response<Alarm>) {
                val code: Int = response.body()?.code ?: 0

                val msg: String = response.body()?.msg ?: "no msg"
                val succeed: Boolean = response.body()?.succeed ?: false

                Log.v("code", code.toString())
                Log.v("success", succeed.toString())
                Log.v("msg", msg)

                if(succeed){

                    val list: List<AlarmItem> = response.body()?.list!!

                    Log.v("data", list.toString())


                }

            }
        })
    }
}