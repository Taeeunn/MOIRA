package com.high5ive.android.moira.ui.teamfinding.recruit

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.RecruitAdapter
import com.high5ive.android.moira.data.Recruit
import com.high5ive.android.moira.data.retrofit.*
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import kotlinx.android.synthetic.main.recruit_post_fragment.*
import kotlinx.android.synthetic.main.recruit_post_fragment.recycler_view
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


//https://mobikul.com/android-chips-dynamicaly-add-remove-tags-chips-view/
class RecruitPostFragment : Fragment() {

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
        return inflater.inflate(R.layout.recruit_post_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var tagList = mutableListOf<String>()
        tagList.add("태그명1")
        tagList.add("태그명2")
        tagList.add("태그명3")
        setTag(tagList);

        val recruit = arrayListOf<Recruit>()
        for (i in 0..30){
            recruit.add(
                Recruit(
                    "팀원 모집글 제목 팀원 모집글 제목 팀원 모집글 제목  $i",
                    "사용자 닉네임 $i",
                    i+1
                )
            )
        }

        recycler_view.apply{
            layoutManager = LinearLayoutManager(context)
            adapter =
                RecruitAdapter(recruit) { index ->
                    Toast.makeText(context, "$index", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, RecruitPostDetailActivity::class.java)
                    intent.putExtra("index", index)
                    startActivity(intent)
                }
        }
    }

    override fun onResume() {
        super.onResume()

        getUserTag()
        getRecruitPostList()
    }

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        myAPI = retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
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
//            chip.setTextAppearance(R.style.tag_text)
//            chip.setCloseIconResource(R.drawable.ic_baseline_highlight_off_24)
            chip.setCloseIconSizeResource(R.dimen.tag_close_icon)
            chip.isCloseIconEnabled = true
            //Added click listener on close icon to remove tag from ChipGroup
            chip.setOnCloseIconClickListener {
                tagList.remove(tagName)
                tag_group.removeView(chip)
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

                    if(succeed){

                        val list: List<String> = response.body()?.list!!
                        Log.v("data", list.toString())

                    }

                }
            })
        }.run()
    }

    private fun getRecruitPostList() {
        Runnable {

            val page: Int = 0
            val position: String = "개발자"
            val sort: String = "date"
            val tag: String = "해시태그1,해시태그2"

            myAPI.getRecruitPostList(token, page, position, null, null).enqueue(object :
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

                    Log.v("url", call.request().url().toString())
                    if(succeed){

                        val list: List<RecruitPostItem> = response.body()?.list!!
                        Log.v("data", list.toString())

                    }

                }
            })
        }.run()
    }

}