package com.high5ive.android.moira.ui.teamfinding.apply

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.MyProfile
import com.high5ive.android.moira.data.retrofit.MyProfileData
import com.high5ive.android.moira.data.retrofit.ProjectApply
import com.high5ive.android.moira.data.retrofit.ResponseData
import com.high5ive.android.moira.databinding.ActivityApplyBinding
import com.high5ive.android.moira.databinding.ActivityRecruitPostDetailBinding
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import gun0912.tedimagepicker.util.ToastUtil.context
import kotlinx.android.synthetic.main.activity_apply.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ApplyActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding: ActivityApplyBinding
    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String

    var index: Int = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_apply
        )

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        index = intent.getIntExtra("index", 1)

        val preferences: SharedPreferences = this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()


        initRetrofit()

        getMyProfileData()


        apply_button.setOnClickListener(this)



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

    private fun getMyProfileData() {
        Runnable {

            myAPI.getMyProfileData(token).enqueue(object :
                Callback<MyProfile> {
                override fun onFailure(call: Call<MyProfile>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<MyProfile>, response: Response<MyProfile>) {

                    val code: Int = response.body()?.code ?: 0

                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false

                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)

                    if(succeed){

                        val data: MyProfileData = response.body()?.data!!
                        Log.v("data", data.toString())

                        binding.myprofile = data

                    }

                }
            })
        }.run()
    }

    private fun applyProject(v: View) {

        val userPortfolioTypeList = listOf("SCHOOL", "CAREER", "LICENSE")
        val body_data = ProjectApply(index, userPortfolioTypeList)

        myAPI.applyProject(token, body_data).enqueue(object :
            Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {


                if (response.code() == 400) {
                    var errorBody = JSONObject(response.errorBody()!!.string());

                    val code= errorBody.getInt("code")
                    val msg = errorBody.getString("msg")
                    val succeed = errorBody.getString("succeed")

                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)

                    if (code == 460) {
                        val message = "내가 작성한 모집글에는 지원할 수 없습니다!"
                        Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()
                    } else if (code == 454){
                        val message = "이미 지원한 모집글입니다!"
                        Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()
                    }
                }


                else if (response.code() == 200){

                    val code: Int = response.body()?.code ?: 0

                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false

                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)

                    val message = resources.getString(R.string.apply_completed)
                    Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()
                    startActivity(Intent(this@ApplyActivity, ApplyCompleteActivity::class.java))


                }

            }
        })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.apply_button -> {
                applyProject(v)

            }
        }
    }
}