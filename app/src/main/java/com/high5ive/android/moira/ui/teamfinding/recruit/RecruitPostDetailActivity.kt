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
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.snackbar.Snackbar
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.PositionAdapter
import com.high5ive.android.moira.data.retrofit.*
import com.high5ive.android.moira.databinding.ActivityRecruitPostDetailBinding
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import com.high5ive.android.moira.ui.teamfinding.apply.ApplyActivity
import com.high5ive.android.moira.ui.teamfinding.apply.ApplyCompleteActivity
import kotlinx.android.synthetic.main.activity_recruit_post_detail.*
import kotlinx.android.synthetic.main.activity_recruit_post_detail.tag_group
import kotlinx.android.synthetic.main.recruit_post_fragment.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RecruitPostDetailActivity : AppCompatActivity(), View.OnClickListener{


    private lateinit var binding: ActivityRecruitPostDetailBinding

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String

    var isLiked: Boolean = false

    var index: Int = 1

    val reportType = listOf("불건전한_내용", "상업적인_내용", "허위_내용", "욕설_및_비난")


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


        val preferences: SharedPreferences = this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()

        index = intent.getIntExtra("index", 1)
        Log.v("index", index.toString())
        initRetrofit()

        comment_img_btn.setOnClickListener(this)
        apply_btn.setOnClickListener(this)
        more_button.setOnClickListener(this)
        favorite_img_btn.setOnClickListener(this)




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

                    listItemsSingleChoice(R.array.report_reason) { _, index, text ->

                        reportRecruitPost(v, reportType[index], text.toString())

                    }
                }
            }

            R.id.favorite_img_btn -> {
                if (isLiked){
                    modifyLikeRecruitPost()
                }else{
                    modifyLikeRecruitPost()
                }
            }
        }
    }

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        myAPI = retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
    }

    private fun reportRecruitPost(view: View, reportType: String, reportReason: String){

        val body_data = Report(reportType, index, "PROJECT")
        myAPI.report(token, body_data).enqueue(object :
            Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {

                if (response.code() == 400) {
                    val errorBody = JSONObject(response.errorBody()!!.string());

                    val code= errorBody.getInt("code")
                    val msg = errorBody.getString("msg")
                    val succeed = errorBody.getString("succeed")

                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)

                    if (code == 462) {
                        val message = "내가 작성한 모집글은 신고할 수 없습니다!"
                        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
                    }
                }


                else if (response.code() == 200){

                    val code: Int = response.body()?.code ?: 0
                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false

                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)

                    val message = resources.getString(R.string.report_completed) + " " + reportReason
                    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()


                }

            }
        })
    }

    private fun getRecruitPostDetail() {
        Runnable {

            myAPI.getRecruitPostDetail(token, index).enqueue(object :
                Callback<RecruitPostDetail> {
                override fun onFailure(call: Call<RecruitPostDetail>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<RecruitPostDetail>, response: Response<RecruitPostDetail>) {

                    if (response.code() == 500) {
                        var errorBody = JSONObject(response.errorBody()!!.string());

                        val code= errorBody.getInt("code")
                        val msg = errorBody.getString("msg")
                        val succeed = errorBody.getString("succeed")

                        Log.v("code", code.toString())
                        Log.v("success", succeed.toString())
                        Log.v("msg", msg)

                    }
                    Log.v("tjtjtj", response.code().toString())
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

                        isLiked = data.isLike

                        if (isLiked){
                            favorite_img_btn.setBackgroundResource(R.drawable.ic_full_heart)

                        }else{
                            favorite_img_btn.setBackgroundResource(R.drawable.ic_empty_heart)
                        }

                        position_recycler_view.apply{
                            layoutManager = LinearLayoutManager(this@RecruitPostDetailActivity)
                            adapter =
                                PositionAdapter(data.positionCategoryList) { position ->
                                    Toast.makeText(this@RecruitPostDetailActivity, "$position", Toast.LENGTH_SHORT).show()
                                }
                        }

                        setTag(data.hashtagList.toMutableList())



                    }

                }
            })
        }.run()
    }

    private fun setTag(tagList: MutableList<String>) {
        for (index in tagList.indices) {
            val tagName = tagList[index]

            val chip = Chip(this)
            val drawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.MaterialChipsAction)
            chip.setChipDrawable(drawable)

            chip.text = tagName
//
            tag_group.addView(chip)
        }
    }

    private fun modifyLikeRecruitPost() {
        myAPI.likeRecruitPost(token, index).enqueue(object :
            Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                val code: Int = response.body()?.code ?: 0

                val msg: String = response.body()?.msg ?: "no msg"
                val succeed: Boolean = response.body()?.succeed ?: false

                Log.v("code", code.toString())
                Log.v("success", succeed.toString())
                Log.v("msg", msg)

                if(succeed){

                    if (isLiked){
                        favorite_img_btn.setBackgroundResource(R.drawable.ic_empty_heart)
                        isLiked=false
                    }else{
                        favorite_img_btn.setBackgroundResource(R.drawable.ic_full_heart)
                        isLiked=true
                    }

                }

            }
        })
    }

}
