package com.high5ive.android.moira.ui.mypage.edit.editinfo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
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
import kotlinx.android.synthetic.main.edit_info.*
import kotlinx.android.synthetic.main.set_nickname_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class EditPositionActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService

    lateinit var jwt_token: String

    var positionId: Int = 1
    var positionIdList = arrayListOf<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_position)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        val preferences: SharedPreferences =
            this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        jwt_token = preferences.getString("jwt_token", "").toString()

        initRetrofit()
        getPositionList()

        register_button.setOnClickListener(this)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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
                    positionIdList.remove(tagId)
                    chip.setTextColor(resources.getColor(R.color.black))
                } else {

                    chip.setTextColor(resources.getColor(R.color.white))
                    positionIdList.add(tagId)
                }

                Log.v("tggg", chip.textColors.toString())
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
                if (positionIdList.size > 1) {
                    val message = "하나의 포지션만 선택가능합니다!"
                    Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()
                } else if (positionIdList.size == 0) {
                    val message = "포지션을 선택해주세요!"
                    Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()
                } else {

                    positionId = positionIdList[0]

                    editPosition(positionId)
                }

            }
        }
    }

    private fun editPosition(positionId: Int) {

        var body_data = PositionEdit(positionId)
        myAPI.editPosition(jwt_token, body_data).enqueue(object : Callback<PositionEditResponse> {
            override fun onFailure(call: Call<PositionEditResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<PositionEditResponse>,
                response: Response<PositionEditResponse>
            ) {
                Log.v("realcode", response.code().toString())
                val code: Int = response.body()?.code ?: 0
                val msg: String = response.body()?.msg ?: "no msg"
                val succeed: Boolean = response.body()?.succeed ?: false



                Log.v("code", code.toString())
                Log.v("success", succeed.toString())
                Log.v("msg", msg)


                if (succeed) {

                    val data: PositionEditResponseData = response.body()?.data!!
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