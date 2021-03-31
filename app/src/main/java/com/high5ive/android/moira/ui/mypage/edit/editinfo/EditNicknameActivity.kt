package com.high5ive.android.moira.ui.mypage.edit.editinfo

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.*
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import kotlinx.android.synthetic.main.activity_edit_nickname.*
import kotlinx.android.synthetic.main.set_nickname_fragment.enter_nickname_et
import kotlinx.android.synthetic.main.set_nickname_fragment.nickname_text_layout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class EditNicknameActivity : AppCompatActivity() {

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService

    lateinit var jwt_token: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_nickname)


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

            checkNickName(enter_nickname_et.text.toString())

        }
    }

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        myAPI = retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
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

    private fun checkNickName(nickname: String){
        Runnable {

            myAPI.checkNickname(jwt_token, nickname).enqueue(object : Callback<ResponseData> {
                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                    Log.v("realcode", response.code().toString())
                    val code: Int = response.body()?.code ?: 0
                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false



                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)


                    if (succeed) {

                        editNickname(nickname)


                    } else{
                        nickname_text_layout.helperText = "*이미 사용중인 닉네임입니다."
                        nickname_text_layout.setHelperTextColor(resources.getColorStateList(R.color.red))
                    }

                }
            })
        }.run()
    }

    private fun editNickname(nickname: String) {

        var body_data = NicknameEdit(nickname)
        myAPI.editNickname(jwt_token, body_data).enqueue(object :
            Callback<NicknameEditResponse> {
            override fun onFailure(call: Call<NicknameEditResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<NicknameEditResponse>, response: Response<NicknameEditResponse>) {
                val code: Int = response.body()?.code ?: 0

                val msg: String = response.body()?.msg ?: "no msg"
                val succeed: Boolean = response.body()?.succeed ?: false

                Log.v("code", code.toString())
                Log.v("success", succeed.toString())
                Log.v("msg", msg)

                if (succeed) {

                    val data: NicknameEditResponseData = response.body()?.data!!
                    Log.v("data", data.toString())

                    val intent = Intent()
                    setResult(RESULT_OK, intent);
                    finish()

                }

            }
        })
    }
}