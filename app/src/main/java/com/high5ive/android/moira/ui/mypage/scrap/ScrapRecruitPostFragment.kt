package com.high5ive.android.moira.ui.mypage.scrap

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.ApplyPost
import com.high5ive.android.moira.data.retrofit.ApplyPostItem
import com.high5ive.android.moira.data.retrofit.RecruitPost
import com.high5ive.android.moira.data.retrofit.RecruitPostItem
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ScrapRecruitPostFragment : Fragment() {

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preferences: SharedPreferences = requireActivity().getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()

        initRetrofit()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_scrap_recruit_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        getScrapRecruitPost()
    }

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        myAPI = retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
    }

    private fun getScrapRecruitPost() {
        Runnable {

            myAPI.getScrapRecruitPostList(token, "develop", "date").enqueue(object : Callback<RecruitPost> {
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

                        val list: List<RecruitPostItem> = response.body()?.list ?: emptyList()
                        Log.v("data", list.toString())

                    }

                }
            })
        }.run()
    }


}