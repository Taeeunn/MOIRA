package com.high5ive.android.moira.ui.mypage.scrap

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.RecruitAdapter
import com.high5ive.android.moira.adapter.ScrapRecruitAdapter
import com.high5ive.android.moira.data.retrofit.*
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import com.high5ive.android.moira.ui.teamfinding.recruit.RecruitPostDetailActivity
import kotlinx.android.synthetic.main.fragment_scrap_recruit_post.*
import kotlinx.android.synthetic.main.recruit_post_fragment.recycler_view
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ScrapRecruitPostFragment : Fragment() {

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String

    var position_filter: String = "개발자"
    var sort_filter: String = "date"

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


    override fun onResume() {
        super.onResume()
        getScrapRecruitPost()

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                when (spinner1.getItemAtPosition(position)) {
                    "개발" -> {
                        Log.v("itemselect", "개발")
                        if(position_filter != "개발자") {
                            position_filter = "개발자"
                            getScrapRecruitPost()
                        }

                    }
                    "기획" -> {
                        Log.v("itemselect", "기획")
                        if(position_filter != "기획자") {
                            position_filter = "기획자"
                            getScrapRecruitPost()
                        }
                    }

                    "디자인" -> {
                        Log.v("itemselect", "디자인")
                        if(position_filter != "디자이너") {
                            position_filter = "디자이너"
                            getScrapRecruitPost()
                        }
                    }
                    else -> {

                    }
                }
            }

        }



        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                when (spinner2.getItemAtPosition(position)) {
                    "최신순" -> {
                        Log.v("itemselect", "최신순")
                        if(sort_filter != "date") {
                            sort_filter = "date"
                            getScrapRecruitPost()
                        }

                    }
                    "조회순" -> {
                        Log.v("itemselect", "조회순")
                        if(sort_filter != "hit") {
                            sort_filter = "hit"
                            getScrapRecruitPost()
                        }
                    }

                    "좋아요순" -> {
                        Log.v("itemselect", "좋아요순")
                        if(sort_filter != "like") {
                            sort_filter = "like"
                            getScrapRecruitPost()
                        }
                    }
                    else -> {

                    }
                }
            }

        }
    }

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        myAPI = retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
    }

    private fun getScrapRecruitPost() {
        Runnable {

            myAPI.getScrapRecruitPostList(token, position_filter, sort_filter).enqueue(object : Callback<ScrapRecruitPost> {
                override fun onFailure(call: Call<ScrapRecruitPost>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<ScrapRecruitPost>, response: Response<ScrapRecruitPost>) {
                    val code: Int = response.body()?.code ?: 0

                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false

                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)

                    if(succeed){

                        val list: List<ScrapRecruitPostItem> = response.body()?.list ?: emptyList()
                        Log.v("data", list.toString())

                        count.text = list.size.toString()

                        recycler_view.apply{
                            layoutManager = LinearLayoutManager(context)
                            adapter =
                                ScrapRecruitAdapter(list) { index ->
                                    Toast.makeText(context, "$index", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(context, RecruitPostDetailActivity::class.java)
                                    intent.putExtra("index", index)
                                    startActivity(intent)
                                }
                        }

                    }

                }
            })
        }.run()
    }


}