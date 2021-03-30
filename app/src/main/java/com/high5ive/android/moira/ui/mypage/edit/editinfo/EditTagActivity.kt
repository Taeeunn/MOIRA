package com.high5ive.android.moira.ui.mypage.edit.editinfo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.snackbar.Snackbar
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.*
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import kotlinx.android.synthetic.main.activity_edit_position.*
import kotlinx.android.synthetic.main.activity_edit_position.designer_tag_group
import kotlinx.android.synthetic.main.activity_edit_position.developer_tag_group
import kotlinx.android.synthetic.main.activity_edit_position.planner_tag_group
import kotlinx.android.synthetic.main.activity_edit_position.register_button
import kotlinx.android.synthetic.main.activity_edit_tag.*
import kotlinx.android.synthetic.main.set_tag_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class EditTagActivity : AppCompatActivity(), View.OnClickListener{

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService

    lateinit var jwt_token: String

    var tagIdList = arrayListOf<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_tag)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        val preferences: SharedPreferences =
            this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        jwt_token = preferences.getString("jwt_token", "").toString()

        initRetrofit()
//        getPositionList()
        getHashTags()

        register_button.setOnClickListener(this)
    }

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        myAPI = retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
    }

    private fun getPositionList() {
        Runnable {

            myAPI.getPosition(jwt_token).enqueue(object : Callback<PositionResponse> {
                override fun onFailure(call: Call<PositionResponse>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<PositionResponse>,
                    response: Response<PositionResponse>
                ) {
                    Log.v("realcode", response.code().toString())
                    val code: Int = response.body()?.code ?: 0
                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false



                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)


                    if (succeed) {

                        val data: List<PositionResponseItem> = response.body()?.list!!
                        Log.v("data", data.toString())

                        setTag(data.toMutableList())

                    }
//                    if (firstLogin){
//                        navController.navigate(R.id.action_loginFragment_to_setNicknameFragment)
//                    } else{
//                        startActivity(Intent(context, MainActivity::class.java))
//                    }

                }
            })
        }.run()
    }

    private fun setTag(tagList: MutableList<PositionResponseItem>) {

        for (index in tagList.indices) {
            val tagName = tagList[index].positionName
            val tagId = tagList[index].positionId
            val categoryId = tagList[index].positionCategoryId

            val chip = Chip(this)
            val drawable =
                ChipDrawable.createFromAttributes(this, null, 0, R.style.MaterialChips)
            chip.setChipDrawable(drawable)

            chip.text = tagName

            chip.setOnClickListener {

                if (chip.textColors == resources.getColorStateList(R.color.white)) {
                    tagIdList.remove(tagId)
                    chip.setTextColor(resources.getColor(R.color.black))
                } else {

                    chip.setTextColor(resources.getColor(R.color.white))
                    tagIdList.add(tagId)
                }

            }

            if (categoryId == 1) {
                developer_tag_group.addView(chip)
            } else if (categoryId == 2) {
                designer_tag_group.addView(chip)
            } else {
                planner_tag_group.addView(chip)
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.register_button -> {

                editTags(tagIdList)


            }
        }
    }

    private fun setTag2(tagList: MutableList<Hashtag>) {
        for (index in tagList.indices) {
            val tagName = tagList[index].hashtagName
            val tagId = tagList[index].hashtagId

            //val chip = Chip(ContextThemeWrapper(context, R.style.MaterialChipsAction))
            val chip = Chip(this)
            chip.setOnClickListener {

                if (chip.textColors == resources.getColorStateList(R.color.white)){
                    tagIdList.remove(tagId)
                    chip.setTextColor(resources.getColor(R.color.black))
                } else{

                    chip.setTextColor(resources.getColor(R.color.white))
                    tagIdList.add(tagId)
                }

                Log.v("tggg", chip.textColors.toString())
            }


            val drawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.MaterialChips)
            chip.setChipDrawable(drawable)
            chip.text = tagName



            //Added click listener on close icon to remove tag from ChipGroup

            interest_tag_group.addView(chip)
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

                    setTag2(list.toMutableList())

//                    if (firstLogin){
//                        navController.navigate(R.id.action_loginFragment_to_setNicknameFragment)
//                    } else{
//                        startActivity(Intent(context, MainActivity::class.java))
//                    }

                }
            })
        }.run()
    }

    private fun editTags(tagIdList: List<Int>) {

        Log.v("list", tagIdList.toString())
        var body_data = HashtagEdit(tagIdList)
        myAPI.editTag(jwt_token, body_data).enqueue(object : Callback<HashtagEditResponse> {
            override fun onFailure(call: Call<HashtagEditResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<HashtagEditResponse>,
                response: Response<HashtagEditResponse>
            ) {
                Log.v("realcode", response.code().toString())
                val code: Int = response.body()?.code ?: 0
                val msg: String = response.body()?.msg ?: "no msg"
                val succeed: Boolean = response.body()?.succeed ?: false



                Log.v("code", code.toString())
                Log.v("success", succeed.toString())
                Log.v("msg", msg)


                if (succeed) {

                    val data: HashtagData = response.body()?.data!!
                    Log.v("data", data.toString())

                    val intent = Intent()
                    setResult(RESULT_OK, intent);
                    finish()
                }

//                    if (firstLogin){
//                        navController.navigate(R.id.action_loginFragment_to_setNicknameFragment)
//                    } else{
//                        startActivity(Intent(context, MainActivity::class.java))
//                    }

            }
        })
    }
}