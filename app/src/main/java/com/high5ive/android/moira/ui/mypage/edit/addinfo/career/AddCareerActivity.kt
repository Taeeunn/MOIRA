package com.high5ive.android.moira.ui.mypage.edit.addinfo.career

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.MyProfileCareerAdd
import com.high5ive.android.moira.data.retrofit.MyProfileCareerAddResponse
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import kotlinx.android.synthetic.main.activity_add_career.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class AddCareerActivity : AppCompatActivity(), View.OnClickListener{

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_career)

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
                addCareer(v)
            }
        }
    }

    private fun addCareer(v: View) {

        val body_data = MyProfileCareerAdd(company_name_et.text.toString(), resignation_et.text.toString(),
            join_company_et.text.toString(),job_department_et.text.toString())

        Log.v("job", job_department_et.text.toString())
        myAPI.addMyProfileCareer(token, body_data).enqueue(object :
            Callback<MyProfileCareerAddResponse> {
            override fun onFailure(call: Call<MyProfileCareerAddResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<MyProfileCareerAddResponse>, response: Response<MyProfileCareerAddResponse>) {
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

                } else{
                    val message = "입사일, 퇴사일의 포맷을 (yyyy-mm-dd)로 맞춰주세요!"
                    Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()
                }

            }
        })
    }
}