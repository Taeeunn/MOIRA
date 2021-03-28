package com.high5ive.android.moira.ui.teamfinding.user

import android.app.AlertDialog
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
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.ScrapUserAdapter
import com.high5ive.android.moira.adapter.UserAdapter
import com.high5ive.android.moira.data.User
import com.high5ive.android.moira.data.retrofit.*
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import com.high5ive.android.moira.ui.myteam.detail.TeamDetailActivity
import com.high5ive.android.moira.ui.myteam.evaluate.EvaluateMemberActivity
import com.high5ive.android.moira.ui.teamfinding.recruit.RecruitPostDetailActivity
import com.high5ive.android.moira.ui.teamfinding.search.UserPoolSearchActivity
import kotlinx.android.synthetic.main.dialog_user_pool.*
import kotlinx.android.synthetic.main.dialog_user_pool.view.*
import kotlinx.android.synthetic.main.team_finding_fragment.*
import kotlinx.android.synthetic.main.user_pool_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class UserPoolFragment : Fragment() {

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String

    var position_filter: String = "개발자"
    var sort_filter: String = "date"

    var userpool_visible: Boolean = false

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



    override fun onResume() {
        super.onResume()

        val preferences: SharedPreferences = requireActivity().getSharedPreferences("moira", Context.MODE_PRIVATE)
        userpool_visible = preferences.getBoolean("userpool_visible", false)

        getUserPoolList()

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                when (spinner1.getItemAtPosition(position)) {
                    "개발" -> {
                        Log.v("itemselect", "개발")
                        if(position_filter != "개발자") {
                            position_filter = "개발자"
                            getUserPoolList()
                        }

                    }
                    "기획" -> {
                        Log.v("itemselect", "기획")
                        if(position_filter != "기획자") {
                            position_filter = "기획자"
                            getUserPoolList()
                        }
                    }

                    "디자인" -> {
                        Log.v("itemselect", "디자인")
                        if(position_filter != "디자이너") {
                            position_filter = "디자이너"
                            getUserPoolList()
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
                        if (sort_filter != "date") {
                            sort_filter = "date"
                            getUserPoolList()
                        }

                    }
                    "조회순" -> {
                        Log.v("itemselect", "조회순")
                        if (sort_filter != "hitCount") {
                            sort_filter = "hitCount"
                            getUserPoolList()
                        }
                    }

                    "좋아요순" -> {
                        Log.v("itemselect", "좋아요순")
                        if (sort_filter != "likeCount") {
                            sort_filter = "likeCount"
                            getUserPoolList()
                        }
                    }
                    else -> {

                    }
                }
            }
        }



        requireActivity().new_post_btn.setOnClickListener{

            // Dialog만들기
            val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_user_pool, null)
            val mBuilder = AlertDialog.Builder(context)
                .setView(mDialogView)

            val  mAlertDialog = mBuilder.show()

            mAlertDialog.onoff.isChecked = userpool_visible

            mDialogView.positiveButton.setOnClickListener {
                val onoff = mDialogView.onoff
                if(!userpool_visible && onoff.isChecked){

                    displayUserPool()

                    Log.v("switch", "on")
                } else if (userpool_visible && !onoff.isChecked){

                    displayUserPool()
                    Log.v("switch", "off")
                }
                mAlertDialog.dismiss()
            }

            mDialogView.negativeButton.setOnClickListener {
                mAlertDialog.dismiss()
            }

        }

        requireActivity().search_button.setOnClickListener {
            startActivity(Intent(context, UserPoolSearchActivity::class.java))
        }

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

    private fun displayUserPool() {

        myAPI.registerUserPool(token).enqueue(object :
            Callback<UserRegistration> {
            override fun onFailure(call: Call<UserRegistration>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<UserRegistration>, response: Response<UserRegistration>) {
                val code: Int = response.body()?.code ?: 0

                val msg: String = response.body()?.msg ?: "no msg"
                val succeed: Boolean = response.body()?.succeed ?: false

                Log.v("code", code.toString())
                Log.v("success", succeed.toString())
                Log.v("msg", msg)

                if(succeed){

                    val visible: Boolean = response.body()?.data?.visible!!
                    Log.v("data", visible.toString())

                    val preferences: SharedPreferences =
                        requireActivity().getSharedPreferences("moira", Context.MODE_PRIVATE)
                    preferences.edit().putBoolean("userpool_visible", visible).apply()

                    userpool_visible = visible

                    getUserPoolList()

                }

            }
        })
    }

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        myAPI = retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
    }

    private fun getUserPoolList() {
        Runnable {

            val page: Int = 1
            val positionCategory: String = "개발자"
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


                        recycler_view.apply{
                            layoutManager = LinearLayoutManager(context)
                            adapter =
                                UserAdapter(list) { index, type ->
                                    Toast.makeText(context, "$index", Toast.LENGTH_SHORT).show()

                                    if (type == 0) {
                                        val intent = Intent(context, UserProfileDetailActivity::class.java)
                                        intent.putExtra("index", index)
                                        startActivity(intent)

                                    } else if (type == 1) {
                                        likeUser(index)
                                    }
                                }
                        }

                    }

                }
            })
        }.run()
    }

    private fun likeUser(index: Int) {
        myAPI.onoffLikeUser(token, index).enqueue(object :
            Callback<UserLike> {
            override fun onFailure(call: Call<UserLike>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<UserLike>, response: Response<UserLike>) {
                val code: Int = response.body()?.code ?: 0

                val msg: String = response.body()?.msg ?: "no msg"
                val succeed: Boolean = response.body()?.succeed ?: false

                Log.v("code", code.toString())
                Log.v("success", succeed.toString())
                Log.v("msg", msg)

                if(succeed){

                    val data: UserLikeData = response.body()?.data!!
                    Log.v("data", data.toString())



                }

            }
        })
    }

}