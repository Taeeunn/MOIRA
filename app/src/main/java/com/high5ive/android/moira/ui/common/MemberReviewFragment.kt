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
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.BadgeAdapter
import com.high5ive.android.moira.data.retrofit.UserPoolDetailReview
import com.high5ive.android.moira.data.retrofit.UserPoolDetailReviewData
import com.high5ive.android.moira.databinding.MemberReviewFragmentBinding
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import com.high5ive.android.moira.ui.applicant.review.ApplicantReviewActivity
import kotlinx.android.synthetic.main.member_review_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class MemberReviewFragment : Fragment() {

    private lateinit var binding: MemberReviewFragmentBinding
    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String
    var index: Int = 1
    var point: Float = 0.0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences: SharedPreferences = requireActivity().getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()

        index = arguments?.getInt("index")?: 1
        initRetrofit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.member_review_fragment, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        show_total_btn.setOnClickListener{
            
            val intent = Intent(context, ApplicantReviewActivity::class.java)
            intent.putExtra("index", index)
            intent.putExtra("point", point)
            startActivity(intent)
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

                        binding.userreview = data

                        point = data.avgMannerPoint

                        if(data.maxComplimentMarkId == 1) {
                            binding.text2.text = "프로페셔널한 모습"
                            binding.bestBadgeImage.setImageResource(R.drawable.badge_1)
                        } else if(data.maxComplimentMarkId == 2){
                            binding.text2.text = "적극적인 참여도"
                            binding.bestBadgeImage.setImageResource(R.drawable.badge_2)
                        }else{
                            binding.text2.text = "커뮤니케이션의 달인"
                            binding.bestBadgeImage.setImageResource(R.drawable.badge_3)
                        }


                        badge_recycler_view.apply{
                            layoutManager = LinearLayoutManager(context)
                            adapter =
                                BadgeAdapter(data.complimentMarkWithCountDtoList)
                        }
                    }

                }
            })
        }.run()
    }

}