package com.high5ive.android.moira.ui.myteam.complete

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
import androidx.recyclerview.widget.GridLayoutManager
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.TeamAdapter
import com.high5ive.android.moira.data.Team
import com.high5ive.android.moira.data.retrofit.MyTeam
import com.high5ive.android.moira.data.retrofit.MyTeamItem
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import com.high5ive.android.moira.ui.myteam.detail.TeamDetailActivity
import com.high5ive.android.moira.ui.myteam.evaluate.EvaluateMemberActivity
import kotlinx.android.synthetic.main.in_progress_team_fragment.*
import kotlinx.android.synthetic.main.recruit_post_fragment.recycler_view
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class CompleteTeamFragment : Fragment() {

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String
    var sort: String = "date"

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
        return inflater.inflate(R.layout.complete_team_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val teamList = arrayListOf<Team>()
        for (i in 0..30){
            teamList.add(
                Team(
                    "팀 이름 $i"
                )
            )
        }

        recycler_view.apply{
            layoutManager = GridLayoutManager(context, 2)
            adapter =
                TeamAdapter(teamList) { type, index ->
                    Log.v("tagsss", type.toString())

                    Toast.makeText(context, "$index", Toast.LENGTH_SHORT).show()
                    if (type==0) {
                        val intent = Intent(context, TeamDetailActivity::class.java)
                        intent.putExtra("index", index)
                        startActivity(intent)
                    }
                    else if(type==1){
                        val intent = Intent(context, EvaluateMemberActivity::class.java)
                        intent.putExtra("index", index)
                        startActivity(intent)
                    }
                }
        }
    }

    override fun onResume() {
        super.onResume()

        getCompleteTeamList()

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                getCompleteTeamList()
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                when (spinner.getItemAtPosition(position)) {
                    "최신순" -> {
                        Log.v("itemselect", "최신순")
                        if(sort == "character") {
                            getCompleteTeamList()
                            sort = "date"
                        }

                    }
                    "가나다순" -> {
                        Log.v("itemselect", "가나다순")
                        if(sort == "date") {
                            getCompleteTeamList()
                            sort = "character"
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

    private fun getCompleteTeamList() {
        Runnable {

            myAPI.getMyTeamList(token, sort, "COMPLETE").enqueue(object :
                Callback<MyTeam> {
                override fun onFailure(call: Call<MyTeam>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<MyTeam>, response: Response<MyTeam>) {
                    val code: Int = response.body()?.code ?: 0

                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false

                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)

                    if(succeed){

                        val list: List<MyTeamItem> = response.body()?.list ?: emptyList()
                        Log.v("data", list.toString())

                    }

                }
            })
        }.run()
    }

}