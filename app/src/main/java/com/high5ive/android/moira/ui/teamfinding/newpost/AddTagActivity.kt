package com.high5ive.android.moira.ui.teamfinding.newpost

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.Hashtag
import com.high5ive.android.moira.data.retrofit.Hashtags
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import kotlinx.android.synthetic.main.activity_add_tag.*
import kotlinx.android.synthetic.main.activity_add_tag.tag_group
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class AddTagActivity : AppCompatActivity() {

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService

    lateinit var jwt_token: String

    val hashtagNameList = arrayListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tag)

        val preferences: SharedPreferences =this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        jwt_token = preferences.getString("jwt_token", null).toString()

        initRetrofit()

        getHashTags()

        register_button.setOnClickListener{

            val intent = Intent()
            intent.putStringArrayListExtra("list", hashtagNameList)
            setResult(RESULT_OK, intent);
            finish()
        }
    }

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2?먯꽌 留뚮뱺 Retrofit client??instance瑜?遺덈윭?듬땲??
        myAPI = retrofit.create(RetrofitService::class.java) // ?ш린??retrofit???곕━??interface瑜?援ы쁽?댁＜怨?
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

    private fun setTag(tagList: MutableList<Hashtag>) {
        for (index in tagList.indices) {
            val tagName = tagList[index].hashtagName

            val chip = Chip(this)
            chip.setOnClickListener {

                if (chip.textColors == resources.getColorStateList(R.color.white)){
                    hashtagNameList.remove(tagName)
                    chip.setTextColor(resources.getColor(R.color.black))
                } else{
                    chip.setTextColor(resources.getColor(R.color.white))
                    hashtagNameList.add(tagName)
                }

            }


            val drawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.MaterialChips)
            chip.setChipDrawable(drawable)
            chip.text = tagName

            tag_group.addView(chip)
        }
    }

}