package com.high5ive.android.moira.ui.common


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.AwardAdapter
import com.high5ive.android.moira.adapter.CareerAdapter
import com.high5ive.android.moira.adapter.CertificateAdapter
import com.high5ive.android.moira.adapter.LinkAdapter
import com.high5ive.android.moira.data.retrofit.ProjectApplyDetail
import com.high5ive.android.moira.data.retrofit.ProjectApplyDetailData
import com.high5ive.android.moira.data.retrofit.UserPoolDetailInfo
import com.high5ive.android.moira.data.retrofit.UserPoolDetailInfoData
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import kotlinx.android.synthetic.main.member_info_fragment.award_recycler_view
import kotlinx.android.synthetic.main.member_info_fragment.career_recycler_view
import kotlinx.android.synthetic.main.member_info_fragment.certificate_recycler_view
import kotlinx.android.synthetic.main.member_info_fragment.link_recycler_view
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MemberInfoFragment : Fragment() {

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String
    var index: Int = 1
    var type: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences: SharedPreferences = requireActivity().getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()

        index = arguments?.getInt("index")?: 1
        type = arguments?.getString("type")?: ""
        initRetrofit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.member_info_fragment, container, false)
    }



    override fun onResume() {
        super.onResume()

        if (type=="user") {
            getUserProfileInfo()
        }

        else{
            getApplicantProfileInfo()
        }



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

                    val succeed: Boolean = response.body()?.succeed ?: false

                    if(succeed){

                        val data: UserPoolDetailInfoData = response.body()?.data!!


                        career_recycler_view.apply {
                            layoutManager = LinearLayoutManager(context)
                            adapter =
                                CareerAdapter(data.userCareerResponseDtoList)
                        }

                        link_recycler_view.apply {
                            layoutManager = LinearLayoutManager(context)
                            adapter =
                                LinkAdapter(data.userLinkResponseDtoList)
                        }

                        certificate_recycler_view.apply {
                            layoutManager = LinearLayoutManager(context)
                            adapter =
                                CertificateAdapter(data.userLicenseResponseDtoList)
                        }

                        award_recycler_view.apply {
                            layoutManager = LinearLayoutManager(context)
                            adapter =
                                AwardAdapter(data.userAwardResponseDtoList)
                        }

                    }

                }
            })
        }.run()
    }

    private fun getApplicantProfileInfo(){

        myAPI.getProjectApplyDetail(token, index).enqueue(object : Callback<ProjectApplyDetail> {
            override fun onFailure(call: Call<ProjectApplyDetail>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ProjectApplyDetail>, response: Response<ProjectApplyDetail>) {
                val succeed: Boolean = response.body()?.succeed ?: false


                if (succeed) {

                    val data: ProjectApplyDetailData = response.body()?.data!!
                    Log.v("data", data.toString())



                    career_recycler_view.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter =
                            CareerAdapter(data.userCareerResponseDtoList)
                    }

                    link_recycler_view.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter =
                            LinkAdapter(data.userLinkResponseDtoList)
                    }

                    certificate_recycler_view.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter =
                            CertificateAdapter(data.userLicenseResponseDtoList)
                    }

                    award_recycler_view.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter =
                            AwardAdapter(data.userAwardResponseDtoList)
                    }

                }

            }

        })
    }

}