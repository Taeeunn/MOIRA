package com.high5ive.android.moira.ui.teamfinding.recruit

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.RecruitAdapter
import com.high5ive.android.moira.data.retrofit.*
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import com.high5ive.android.moira.ui.teamfinding.newpost.NewPostActivity
import com.high5ive.android.moira.ui.teamfinding.search.RecruitPostSearchActivity
import kotlinx.android.synthetic.main.recruit_post_fragment.*
import kotlinx.android.synthetic.main.recruit_post_fragment.recycler_view
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class RecruitPostFragment : Fragment() {

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String
    var position_filter: String = "개발자"
    var sort_filter: String = "date"

    val hashtagList = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences: SharedPreferences =
            requireActivity().getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()

        initRetrofit()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recruit_post_fragment, container, false)
    }


    override fun onResume() {
        super.onResume()

        tag_group.removeAllViews()

        getUserTag()

        new_post_btn.setOnClickListener {
            startActivity(Intent(context, NewPostActivity::class.java))
        }

        search_button.setOnClickListener {
            startActivity(Intent(context, RecruitPostSearchActivity::class.java))
        }


        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                when (spinner1.getItemAtPosition(position)) {
                    "개발" -> {
                        if (position_filter != "개발자") {
                            position_filter = "개발자"
                            getRecruitPostList()
                        }

                    }
                    "기획" -> {
                        if (position_filter != "기획자") {
                            position_filter = "기획자"
                            getRecruitPostList()
                        }
                    }

                    "디자인" -> {
                        if (position_filter != "디자이너") {
                            position_filter = "디자이너"
                            getRecruitPostList()
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
                        if (sort_filter != "date") {
                            sort_filter = "date"
                            getRecruitPostList()
                        }

                    }
                    "조회순" -> {
                        if (sort_filter != "hitCount") {
                            sort_filter = "hitCount"
                            getRecruitPostList()
                        }
                    }

                    "좋아요순" -> {
                        if (sort_filter != "likeCount") {
                            sort_filter = "likeCount"
                            getRecruitPostList()
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


    private fun setTag(tagList: MutableList<String>) {
        for (index in tagList.indices) {
            val tagName = tagList[index]

            hashtagList.add(tagName)
            val chip = Chip(context)
            val drawable = context?.let {
                ChipDrawable.createFromAttributes(it, null, 0, R.style.MaterialChipsAction)
            }
            if (drawable != null) {
                chip.setChipDrawable(drawable)
            }

            chip.text = tagName
            chip.setCloseIconSizeResource(R.dimen.tag_close_icon)
            chip.isCloseIconEnabled = true
            chip.setOnCloseIconClickListener {
                tagList.remove(tagName)
                tag_group.removeView(chip)
                hashtagList.remove(tagName)
                getRecruitPostList()
            }
            tag_group.addView(chip)
        }
    }


    private fun getUserTag() {
        Runnable {

            myAPI.getUserTagList(token).enqueue(object :
                Callback<UserTag> {
                override fun onFailure(call: Call<UserTag>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<UserTag>, response: Response<UserTag>) {
                    val code: Int = response.body()?.code ?: 0

                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false

                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)

                    if (succeed) {

                        val list: List<String> = response.body()?.list!!

                        setTag(list.toMutableList());
                        Log.v("data", list.toString())

                        getRecruitPostList()

                    }

                }
            })
        }.run()
    }

    private fun getRecruitPostList() {
        Runnable {

            var hashtagListString = ""

            for (index in hashtagList.indices) {
                val tagName = hashtagList[index]
                hashtagListString += tagName

                if (index != hashtagList.size - 1) {
                    hashtagListString += ","
                }

            }


            myAPI.getRecruitPostList(token, null, 0, position_filter, sort_filter, hashtagListString).enqueue(object :
                Callback<RecruitPost> {
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

                    if (succeed) {

                        val list: List<RecruitPostItem> = response.body()?.list!!
                        Log.v("data", list.toString())


                        recycler_view.apply {
                            layoutManager = LinearLayoutManager(context)
                            adapter =
                                RecruitAdapter(list) { index ->
                                    Toast.makeText(context, "$index", Toast.LENGTH_SHORT).show()
                                    val intent =
                                        Intent(context, RecruitPostDetailActivity::class.java)
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