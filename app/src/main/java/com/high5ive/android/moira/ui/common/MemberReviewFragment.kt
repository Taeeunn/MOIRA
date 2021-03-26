package com.high5ive.android.moira.ui.common

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
import com.high5ive.android.moira.adapter.BadgeAdapter
import com.high5ive.android.moira.data.Badge
import com.high5ive.android.moira.data.retrofit.UserPoolDetailReview
import com.high5ive.android.moira.data.retrofit.UserPoolDetailReviewData
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import com.high5ive.android.moira.ui.applicant.review.ApplicantReviewActivity
import kotlinx.android.synthetic.main.member_review_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class MemberReviewFragment : Fragment() {

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String
    var index: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences: SharedPreferences = requireActivity().getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()

        index = arguments?.getInt("index")?: 1
        Log.v("index", index.toString())
        initRetrofit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.member_review_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        show_total_btn.setOnClickListener{
            startActivity(Intent(context, ApplicantReviewActivity::class.java))
        }

        val badgeList = arrayListOf<Badge>()
        for (i in 0..5){
            badgeList.add(
                Badge(
                    "배지명 $i",
                    i+1
                )
            )
        }

        badge_recycler_view.apply{
            layoutManager = LinearLayoutManager(context)
            adapter =
                BadgeAdapter(badgeList) { badge ->
                    Toast.makeText(context, "$badge", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onResume() {
        super.onResume()

        getUserProfileReview()
    }

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        myAPI = retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
    }

    private fun getUserProfileReview() {
        Runnable {

            myAPI.getUserPoolDetailReview(token, index).enqueue(object :
                Callback<UserPoolDetailReview> {
                override fun onFailure(call: Call<UserPoolDetailReview>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<UserPoolDetailReview>, response: Response<UserPoolDetailReview>) {
                    val code: Int = response.body()?.code ?: 0

                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false

                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)

                    if(succeed){

                        val data: UserPoolDetailReviewData = response.body()?.data!!
                        Log.v("data", data.toString())

                    }

                }
            })
        }.run()
    }

}