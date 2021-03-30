package com.high5ive.android.moira.ui.home

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.HomeResponse
import com.high5ive.android.moira.data.retrofit.HomeResponseData
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import com.high5ive.android.moira.ui.message.MessageBoxActivity
import kotlinx.android.synthetic.main.home_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class HomeFragment : Fragment() {

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences: SharedPreferences = requireActivity().getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()
        Log.v("token", token)
        initRetrofit()



        noti_frame.setOnClickListener {
            startActivity(Intent(context, AlarmActivity::class.java))
        }

        card1.setOnClickListener {
            startActivity(Intent(context, ProjectStartActivity::class.java))
        }

        card2.setOnClickListener {
            startActivity(Intent(context, MoiraDescriptionActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        getHome()
    }

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        myAPI = retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
    }

    private fun getHome() {
        myAPI.getHome(token).enqueue(object :
            Callback<HomeResponse> {
            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                val code: Int = response.body()?.code ?: 0

                val msg: String = response.body()?.msg ?: "no msg"
                val succeed: Boolean = response.body()?.succeed ?: false

                Log.v("code", code.toString())
                Log.v("success", succeed.toString())
                Log.v("msg", msg)

                if(succeed){

                    val data: HomeResponseData = response.body()?.data!!

                    Log.v("data", data.toString())

                    if(data.hasUnreadAlarm){
                        new_noti.visibility = View.VISIBLE
                    } else{
                        new_noti.visibility = View.GONE
                    }

                }

            }
        })
    }
    
    
    

}