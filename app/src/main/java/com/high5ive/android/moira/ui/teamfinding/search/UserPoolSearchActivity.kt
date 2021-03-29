package com.high5ive.android.moira.ui.teamfinding.search

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
import com.google.android.material.snackbar.Snackbar
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.MemberAdapter
import com.high5ive.android.moira.adapter.UserAdapter
import com.high5ive.android.moira.data.retrofit.*
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import com.high5ive.android.moira.ui.teamfinding.user.UserProfileDetailActivity
import gun0912.tedimagepicker.util.ToastUtil.context
import kotlinx.android.synthetic.main.activity_team_detail.*
import kotlinx.android.synthetic.main.activity_user_pool_search.*
import kotlinx.android.synthetic.main.activity_user_pool_search.recycler_view
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class UserPoolSearchActivity : AppCompatActivity(), View.OnClickListener{

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_pool_search)

        val preferences: SharedPreferences = this.getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()

        initRetrofit()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        search_button.setOnClickListener(this)

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
            R.id.search_button -> {
                searchUser(v)
            }
        }
    }

    private fun searchUser(v: View) {
        val nickname = searchView.query.toString()

        if(nickname.length<3){
            val msg = "세 글자 이상 입력해주세요."
            search_text.text = ""

            Snackbar.make(v, msg, Snackbar.LENGTH_SHORT).show()

            val list: List<UserPoolItem> = emptyList()
            recycler_view.apply {
                layoutManager = LinearLayoutManager(this@UserPoolSearchActivity)
                adapter =
                    UserAdapter(list) { index, type ->
                        Toast.makeText(context, "$index", Toast.LENGTH_SHORT).show()

                        if (type == 0) {
                            val intent = Intent(context, UserProfileDetailActivity::class.java)
                            intent.putExtra("index", index)
                            startActivity(intent)

                        } else if (type == 1) {
                            likeUser(index)
                        }
                    }
            }
            return
        }

        myAPI.searchUserPool(token, nickname).enqueue(object :
            Callback<UserPoolSearch> {
            override fun onFailure(call: Call<UserPoolSearch>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<UserPoolSearch>, response: Response<UserPoolSearch>) {
                val code: Int = response.body()?.code ?: 0

                val msg: String = response.body()?.msg ?: "no msg"
                val succeed: Boolean = response.body()?.succeed ?: false

                Log.v("code", code.toString())
                Log.v("success", succeed.toString())
                Log.v("msg", msg)

                if(succeed){

                    val list: List<UserPoolItem> = response.body()?.list!!
                    Log.v("data", list.toString())

                    if(list.isEmpty()){
                        val msg = "해당 닉네임을 가진 사용자가 없습니다."
                        Snackbar.make(v, msg, Snackbar.LENGTH_SHORT).show()
                    }



                    search_text.text = "'" + nickname + "' 검색 내역"
                    recycler_view.apply {
                        layoutManager = LinearLayoutManager(this@UserPoolSearchActivity)
                        adapter =
                            UserAdapter(list) { index, type ->
                                Toast.makeText(this@UserPoolSearchActivity, "$index", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@UserPoolSearchActivity, UserProfileDetailActivity::class.java))
                            }
                    }

                }

            }
        })
    }

    private fun likeUser(index: Int) {
        myAPI.onoffLikeUser(token, index).enqueue(object :
            Callback<UserLike> {
            override fun onFailure(call: Call<UserLike>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<UserLike>, response: Response<UserLike>) {
                val code: Int = response.body()?.code ?: 0

                val msg: String = response.body()?.msg ?: "no msg"
                val succeed: Boolean = response.body()?.succeed ?: false

                Log.v("code", code.toString())
                Log.v("success", succeed.toString())
                Log.v("msg", msg)

                if(succeed){

                    val data: UserLikeData = response.body()?.data!!
                    Log.v("data", data.toString())



                }

            }
        })
    }
}