package com.high5ive.android.moira.ui.teamfinding.user

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
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.UserAdapter
import com.high5ive.android.moira.data.User
import com.high5ive.android.moira.data.retrofit.UserPool
import com.high5ive.android.moira.data.retrofit.UserPoolItem
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import kotlinx.android.synthetic.main.recruit_post_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class UserPoolFragment : Fragment() {

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
        return inflater.inflate(R.layout.user_pool_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var tagList = mutableListOf<String>()
        tagList.add("태그명1")
        tagList.add("태그명2")
        tagList.add("태그명3")
        setTag(tagList);


        val userList = arrayListOf<User>()
        for (i in 0..5){
            userList.add(
                User(
                    "사용자 닉네임 $i",
                    "개발자 $i",
                    "사용자 한줄소개입니다. 사용자 한줄소개입니다. $i"
                )
            )
        }

        recycler_view.apply{
            layoutManager = LinearLayoutManager(context)
            adapter =
                UserAdapter(userList) { index ->
                    Toast.makeText(context, "$index", Toast.LENGTH_SHORT).show()

                    val intent = Intent(context, UserProfileDetailActivity::class.java)
                    intent.putExtra("index", index)
                    startActivity(intent)
                }
        }
    }

    override fun onResume() {
        super.onResume()

        getUserPoolList()
    }


    private fun setTag(tagList: MutableList<String>) {
        for (index in tagList.indices) {
            val tagName = tagList[index]
            //val chip = Chip(ContextThemeWrapper(context, R.style.MaterialChipsAction))
            val chip = Chip(context)
            val drawable = context?.let { ChipDrawable.createFromAttributes(it, null, 0, R.style.MaterialChipsAction) }
            if (drawable != null) {
                chip.setChipDrawable(drawable)
            }

            chip.text = tagName
            chip.setCloseIconSizeResource(R.dimen.tag_close_icon)
//            chip.setTextAppearance(R.style.tag_text)
//            chip.setCloseIconResource(R.drawable.ic_baseline_close_24)
            chip.isCloseIconEnabled = true
            //Added click listener on close icon to remove tag from ChipGroup
            chip.setOnCloseIconClickListener {
                tagList.remove(tagName)
                tag_group.removeView(chip)
            }
            tag_group.addView(chip)
        }
    }

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        myAPI = retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
    }

    private fun getUserPoolList() {
        Runnable {

            val page: Int = 1
            val positionCategory: String = "develop"
            val sortby: String = "date"

            myAPI.getUserPoolList(token, page, positionCategory, sortby).enqueue(object :
                Callback<UserPool> {
                override fun onFailure(call: Call<UserPool>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<UserPool>, response: Response<UserPool>) {
                    val code: Int = response.body()?.code ?: 0

                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false

                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)

                    if(succeed){

                        val list: List<UserPoolItem> = response.body()?.list!!
                        Log.v("data", list.toString())

                    }

                }
            })
        }.run()
    }

}