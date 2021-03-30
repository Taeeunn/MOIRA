package com.high5ive.android.moira.ui.message

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.google.android.material.snackbar.Snackbar
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.ChatAdapter
import com.high5ive.android.moira.data.retrofit.*
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import kotlinx.android.synthetic.main.activity_message_box.*
import kotlinx.android.synthetic.main.activity_message_history.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MessageHistoryActivity : AppCompatActivity(), View.OnClickListener{

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String
    var index: Int = 1

    val reportType = listOf("불건전한_내용", "상업적인_내용", "허위_내용", "욕설_및_비난")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_history)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        menu_button.setOnClickListener(this)

        val preferences: SharedPreferences = this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()

        index = intent.getIntExtra("index", 1)
        initRetrofit()


    }


    override fun onResume() {
        super.onResume()

        getChatDetail()
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

    private fun getChatDetail(){
        myAPI.getChatDetail(token, index, 1).enqueue(object :
            Callback<ChatDetail> {
            override fun onFailure(call: Call<ChatDetail>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ChatDetail>, response: Response<ChatDetail>) {
                val code: Int = response.body()?.code ?: 0

                val msg: String = response.body()?.msg ?: "no msg"
                val succeed: Boolean = response.body()?.succeed ?: false

                Log.v("code", code.toString())
                Log.v("success", succeed.toString())
                Log.v("msg", msg)

                if(succeed){

                    val list: List<ChatDetailItem> = response.body()?.list!!
                    Log.v("list", list.toString())


                }

            }
        })
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.menu_button -> {
                MaterialDialog(this).show {
                    title(R.string.report_reason)
                    negativeButton(R.string.cancle)
                    positiveButton(R.string.report)

                    listItemsSingleChoice(R.array.chat_report_reason) { _, index, text ->

                        reportChat(v, reportType[index], text.toString())

                    }
                }
            }
        }
    }

    private fun reportChat(view: View, reportType: String, reportReason: String){

        val body_data = Report(reportType, index, "CHAT")
        myAPI.report(token, body_data).enqueue(object :
            Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {


                if (response.code() == 200){

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
}