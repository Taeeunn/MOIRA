package com.high5ive.android.moira.ui.teamfinding.recruit

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.google.android.material.snackbar.Snackbar
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.PositionAdapter
import com.high5ive.android.moira.data.Position
import com.high5ive.android.moira.data.retrofit.RecruitPostDetail
import com.high5ive.android.moira.data.retrofit.RecruitPostDetailData
import com.high5ive.android.moira.data.retrofit.UserPoolDetailInfo
import com.high5ive.android.moira.data.retrofit.UserPoolDetailInfoData
import com.high5ive.android.moira.databinding.ActivityRecruitPostDetailBinding
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import com.high5ive.android.moira.ui.teamfinding.apply.ApplyActivity
import kotlinx.android.synthetic.main.activity_recruit_post_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RecruitPostDetailActivity : AppCompatActivity(), View.OnClickListener{


    private lateinit var binding: ActivityRecruitPostDetailBinding

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String

    var index: Int = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_recruit_post_detail
        )



        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        index = intent.getIntExtra("index", 1)

        val preferences: SharedPreferences = this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()

        index = intent.getIntExtra("index", 1)
        Log.v("index", index.toString())
        initRetrofit()

        comment_img_btn.setOnClickListener(this)
        apply_btn.setOnClickListener(this)
        more_button.setOnClickListener(this)


        val positionList = arrayListOf(
            Position("개발자", 1), Position("디자이너", 1), Position("PM", 1)
        )


        position_recycler_view.apply{
            layoutManager = LinearLayoutManager(this@RecruitPostDetailActivity)
            adapter =
                PositionAdapter(positionList) { position ->
                    Toast.makeText(this@RecruitPostDetailActivity, "$position", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onResume() {
        super.onResume()

        getRecruitPostDetail()
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
            R.id.comment_img_btn -> startActivity(Intent(this@RecruitPostDetailActivity, CommentActivity::class.java))

            R.id.apply_btn -> {

                val intent = Intent(this@RecruitPostDetailActivity, ApplyActivity::class.java)
                intent.putExtra("index", index)
                startActivity(intent)
            }

            R.id.more_button -> {
                MaterialDialog(this).show {
                    title(R.string.report_reason)
                    negativeButton(R.string.cancle)
                    positiveButton(R.string.report)

                    listItemsSingleChoice(R.array.report_reason) { _, _, text ->
                        val msg = resources.getString(R.string.report_completed) + " " + text
                        Snackbar.make(v, msg, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        myAPI = retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
    }

    private fun getRecruitPostDetail() {
        Runnable {

            myAPI.getRecruitPostDetail(token, index).enqueue(object :
                Callback<RecruitPostDetail> {
                override fun onFailure(call: Call<RecruitPostDetail>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<RecruitPostDetail>, response: Response<RecruitPostDetail>) {
                    val code: Int = response.body()?.code ?: 0

                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false

                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)

                    if(succeed){

                        val data: RecruitPostDetailData = response.body()?.data!!
                        Log.v("data", data.toString())

                        binding.recruitpost = data



                    }

                }
            })
        }.run()
    }

}
