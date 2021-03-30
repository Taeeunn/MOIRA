package com.high5ive.android.moira.ui.mypage.edit.addinfo.award

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.MyProfileAwardAdd
import com.high5ive.android.moira.data.retrofit.MyProfileAwardAddResponse
import com.high5ive.android.moira.data.retrofit.MyProfileLicenseAdd
import com.high5ive.android.moira.data.retrofit.MyProfileLicenseAddResponse
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import kotlinx.android.synthetic.main.activity_add_certificate.*
import kotlinx.android.synthetic.main.activity_add_education.*
import kotlinx.android.synthetic.main.activity_add_education.register_button
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class AddAwardHistoryActivity : AppCompatActivity(), View.OnClickListener{

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_award_history)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        register_button.setOnClickListener(this)

        val preferences: SharedPreferences =
            this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()


        initRetrofit()

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

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.register_button -> {
                addAward()
            }
        }
    }

    private fun addAward() {

        val body_data = MyProfileAwardAdd(award_history_et.text.toString(), contest_name_et.text.toString())

        myAPI.addMyProfileAward(token, body_data).enqueue(object :
            Callback<MyProfileAwardAddResponse> {
            override fun onFailure(call: Call<MyProfileAwardAddResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<MyProfileAwardAddResponse>, response: Response<MyProfileAwardAddResponse>) {
                val code: Int = response.body()?.code ?: 0

                val msg: String = response.body()?.msg ?: "no msg"
                val succeed: Boolean = response.body()?.succeed ?: false

                Log.v("code", code.toString())
                Log.v("success", succeed.toString())
                Log.v("msg", msg)

                if (succeed) {
                    val intent = Intent()
                    setResult(RESULT_OK, intent);
                    finish()

                }

            }
        })
    }
}