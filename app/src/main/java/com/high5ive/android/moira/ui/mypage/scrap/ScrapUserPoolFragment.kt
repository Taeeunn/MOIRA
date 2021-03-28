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
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.ScrapRecruitAdapter
import com.high5ive.android.moira.adapter.ScrapUserAdapter
import com.high5ive.android.moira.data.retrofit.*
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import com.high5ive.android.moira.ui.teamfinding.recruit.RecruitPostDetailActivity
import com.high5ive.android.moira.ui.teamfinding.user.UserProfileDetailActivity
import kotlinx.android.synthetic.main.fragment_scrap_recruit_post.*
import kotlinx.android.synthetic.main.fragment_scrap_recruit_post.recycler_view
import kotlinx.android.synthetic.main.recruit_post_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class ScrapUserPoolFragment : Fragment() {

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences: SharedPreferences = requireActivity().getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()

        initRetrofit()

    }

    override fun onResume() {
        super.onResume()
        getScrapUserPool()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scrap_user_pool, container, false)
    }

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        myAPI = retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
    }

    private fun getScrapUserPool() {
        Runnable {

            myAPI.getScrapUserPoolList(token, "develop", "date").enqueue(object :
                Callback<ScrapUserPool> {
                override fun onFailure(call: Call<ScrapUserPool>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<ScrapUserPool>, response: Response<ScrapUserPool>) {
                    val code: Int = response.body()?.code ?: 0

                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false

                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)

                    if(succeed){



                        val list: List<ScrapUserPoolItem> = response.body()?.list ?: emptyList()
                        Log.v("data", list.toString())

                        count.text = list.size.toString()

                        recycler_view.apply{
                            layoutManager = LinearLayoutManager(context)
                            adapter =
                                ScrapUserAdapter(list) { index ->
                                    Toast.makeText(context, "$index", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(context, UserProfileDetailActivity::class.java)
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