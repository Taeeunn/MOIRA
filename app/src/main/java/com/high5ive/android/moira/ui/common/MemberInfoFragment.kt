package com.high5ive.android.moira.ui.common


import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.AwardAdapter
import com.high5ive.android.moira.data.Award
import com.high5ive.android.moira.data.retrofit.MyTeamDetail
import com.high5ive.android.moira.data.retrofit.MyTeamDetailData
import com.high5ive.android.moira.data.retrofit.UserPoolDetailInfo
import com.high5ive.android.moira.data.retrofit.UserPoolDetailInfoData
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import kotlinx.android.synthetic.main.member_info_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MemberInfoFragment : Fragment() {

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String
    var index: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences: SharedPreferences = requireActivity().getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()

        index = arguments?.getInt("index")?: 1
        Log.v("applyId", index.toString())
        initRetrofit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.member_info_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val awardList = arrayListOf<Award>()
        for (i in 0..5){
            awardList.add(
                Award(
                    "가나다 공모전 $i",
                    "최우수상 $i"
                )
            )
        }

        award_recycler_view.apply{
            layoutManager = LinearLayoutManager(context)
            adapter =
                AwardAdapter(awardList) { award ->
                    Toast.makeText(context, "$award", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onResume() {
        super.onResume()

        getUserProfileInfo()
    }

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        myAPI = retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
    }



    private fun getUserProfileInfo() {
        Runnable {

            myAPI.getUserPoolDetailInfo(token, index).enqueue(object :
                Callback<UserPoolDetailInfo> {
                override fun onFailure(call: Call<UserPoolDetailInfo>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<UserPoolDetailInfo>, response: Response<UserPoolDetailInfo>) {
                    val code: Int = response.body()?.code ?: 0

                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false

                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)

                    if(succeed){

                        val data: UserPoolDetailInfoData = response.body()?.data!!
                        Log.v("data", data.toString())

                    }

                }
            })
        }.run()
    }

}