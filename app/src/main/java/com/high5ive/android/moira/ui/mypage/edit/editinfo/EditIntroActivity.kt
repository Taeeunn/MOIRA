package com.high5ive.android.moira.ui.mypage.edit.editinfo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.*
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import kotlinx.android.synthetic.main.activity_edit_intro.*
import kotlinx.android.synthetic.main.activity_edit_nickname.register_button
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class EditIntroActivity : AppCompatActivity() {

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService

    lateinit var jwt_token: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_intro)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)


        initRetrofit()

        val preferences: SharedPreferences =
            this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        jwt_token = preferences.getString("jwt_token", "").toString()

        register_button.setOnClickListener {

            editIntro(enter_intro_et.text.toString())

        }
    }

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        myAPI = retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
    }

    private fun editIntro(intro: String) {

        var body_data = IntroEdit(intro)
        myAPI.editIntroduction(jwt_token, body_data).enqueue(object :
            Callback<IntroEditResponse> {
            override fun onFailure(call: Call<IntroEditResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<IntroEditResponse>, response: Response<IntroEditResponse>) {
                val code: Int = response.body()?.code ?: 0

                val msg: String = response.body()?.msg ?: "no msg"
                val succeed: Boolean = response.body()?.succeed ?: false

                Log.v("code", code.toString())
                Log.v("success", succeed.toString())
                Log.v("msg", msg)

                if (succeed) {

                    val data: IntroEditResponseData = response.body()?.data!!
                    Log.v("data", data.toString())

                    val intent = Intent()
                    setResult(RESULT_OK, intent);
                    finish()

                }

            }
        })
    }
}