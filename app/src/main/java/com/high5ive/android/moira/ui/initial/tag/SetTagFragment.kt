package com.high5ive.android.moira.ui.initial.tag

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.high5ive.android.moira.MainActivity
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.*
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import kotlinx.android.synthetic.main.set_tag_fragment.*
import kotlinx.android.synthetic.main.set_tag_fragment.tag_group
import kotlinx.android.synthetic.main.set_tag_fragment.to_next_btn
import kotlinx.android.synthetic.main.set_tag_fragment.toolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SetTagFragment : Fragment() {

    lateinit var navController : NavController


    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService

    lateinit var jwt_token: String

    var nickname = ""
    var positionId: Int = 1
    val hashtagIdList = mutableListOf<Int>()
    var index: Int = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.set_tag_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nickname = arguments?.getString("nickname")?: ""

        Log.v("nickname", nickname)
        Log.v("positionId", positionId.toString())

        navController = Navigation.findNavController(view)

        val preferences: SharedPreferences =requireActivity().getSharedPreferences("moira", Context.MODE_PRIVATE)
        jwt_token = preferences.getString("jwt_token", null).toString()

        index = arguments?.getInt("index")?: 1

        to_next_btn.setOnClickListener {
            signupUser()
            Log.v("tagIdList", hashtagIdList.toString())
        }

        initRetrofit()
        getPositionDetail()
        getHashTags()


        setHasOptionsMenu(true);
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        val ab = (activity as AppCompatActivity?)!!.supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                Log.v("hello", "hello")
                navController.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        myAPI = retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
    }

    private fun getPositionDetail() {
        Runnable {

            Log.v("index", index.toString())
            myAPI.getPositionDetail(jwt_token, index).enqueue(object : Callback<PositionDetail> {
                override fun onFailure(call: Call<PositionDetail>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<PositionDetail>,
                    response: Response<PositionDetail>
                ) {
                    val code: Int = response.body()?.code ?: 0
                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false
                    val list: List<PositionDetailItem> = response.body()?.list ?: emptyList()



                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)
                    Log.v("list", list.toString())

                    setTag2(list.toMutableList())

                }
            })
        }.run()
    }

    private fun setTag(tagList: MutableList<Hashtag>) {
        for (index in tagList.indices) {
            val tagName = tagList[index].hashtagName
            val tagId = tagList[index].hashtagId

            val chip = Chip(context)
            Log.v("tggg", chip.textColors.toString())
            chip.setOnClickListener {

                if (chip.textColors == resources.getColorStateList(R.color.white)){
                    hashtagIdList.remove(tagId)
                    chip.setTextColor(resources.getColor(R.color.black))
                } else{

                    chip.setTextColor(resources.getColor(R.color.white))
                    hashtagIdList.add(tagId)
                }

            }


            val drawable = context?.let { ChipDrawable.createFromAttributes(it, null, 0, R.style.MaterialChips) }
            if (drawable != null) {
                chip.setChipDrawable(drawable)
            }
            chip.text = tagName

            tag_group.addView(chip)
        }
    }

    private fun setTag2(tagList: MutableList<PositionDetailItem>) {
        for (index in tagList.indices) {
            val tagName = tagList[index].positionName
            val tagId = tagList[index].positionId

            val chip = Chip(context)
            Log.v("tggg", chip.textColors.toString())
            chip.setOnClickListener {

                if (chip.textColors == resources.getColorStateList(R.color.white)){
                    chip.setTextColor(resources.getColor(R.color.black))
                } else{
                    positionId = tagId
                    chip.setTextColor(resources.getColor(R.color.white))
                }
            }


            val drawable = context?.let { ChipDrawable.createFromAttributes(it, null, 0, R.style.MaterialChips) }
            if (drawable != null) {
                chip.setChipDrawable(drawable)
            }
            chip.text = tagName

            position_tag_group.addView(chip)
        }
    }

    private fun getHashTags() {
        Runnable {

            myAPI.getHashTags(jwt_token).enqueue(object : Callback<Hashtags> {
                override fun onFailure(call: Call<Hashtags>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<Hashtags>, response: Response<Hashtags>) {
                    val code: Int = response.body()?.code ?: 0
                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false
                    val list: List<Hashtag> = response.body()?.list ?: emptyList()

                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)
                    Log.v("list", list.toString())

                    setTag(list.toMutableList())

                }
            })
        }.run()
    }

    private fun signupUser() {
        Runnable {

            val body_data = SignUpInfo(hashtagIdList, nickname, positionId)
            myAPI.signupUser(jwt_token, body_data).enqueue(object : Callback<ResponseData> {
                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                    Log.v("code", response.code().toString())
                    Log.v("msg", response.message())
                    val code: Int = response.body()?.code ?: 0
                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false

                    Log.v("msg", msg)


                    if(succeed){
                        startActivity(Intent(context, MainActivity::class.java))
                    }

                }
            })
        }.run()
    }

}