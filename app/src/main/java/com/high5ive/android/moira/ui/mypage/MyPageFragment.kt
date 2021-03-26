package com.high5ive.android.moira.ui.mypage

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.high5ive.android.moira.MainActivity
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.LoginInfo
import com.high5ive.android.moira.data.retrofit.LoginUser
import com.high5ive.android.moira.data.retrofit.MyPage
import com.high5ive.android.moira.data.retrofit.MyPageData
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import com.high5ive.android.moira.ui.mypage.apply.ApplyListActivity
import com.high5ive.android.moira.ui.mypage.cs.AskActivity
import com.high5ive.android.moira.ui.mypage.edit.EditProfileActivity
import com.high5ive.android.moira.ui.mypage.post.PostListActivity
import com.high5ive.android.moira.ui.mypage.scrap.ScrapListActivity
import kotlinx.android.synthetic.main.my_page_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MyPageFragment : Fragment(), View.OnClickListener{

    companion object {
        fun newInstance() = MyPageFragment()
    }

    private lateinit var viewModel: MyPageViewModel

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val preferences: SharedPreferences =requireActivity().getSharedPreferences("moira", Context.MODE_PRIVATE)
        token = preferences.getString("jwt_token", null).toString()

        initRetrofit()


        return inflater.inflate(R.layout.my_page_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()

        setMyPage()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageViewModel::class.java)
        // TODO: Use the ViewModel

        post_container.setOnClickListener(this)

        apply_container.setOnClickListener(this)

        scrap_container.setOnClickListener(this)

        edit_info_btn.setOnClickListener(this)

        account_setting.setOnClickListener(this)

        noti_setting.setOnClickListener(this)

        question.setOnClickListener(this)
    }

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        myAPI = retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
    }

    private fun setMyPage(){
        Runnable {

            myAPI.getMyPage(token).enqueue(object : Callback<MyPage> {
                override fun onFailure(call: Call<MyPage>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<MyPage>, response: Response<MyPage>) {
                    val code: Int = response.body()?.code ?: 0

                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false

                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)

                    if(succeed){
                        val data: MyPageData = response.body()?.data!!
                        Log.v("data", data.toString())
                        user_nickname.text = data.nickname
                        user_position.text = data.positionName
                        user_intro.text = data.shortIntroduction
                        post_count.text = data.writtenPostCount.toString()
                        apply_count.text = data.appliedPostCount.toString()
                        scrap_count.text = data.likedPostCount.toString()
                    }

                }
            })
        }.run()
    }



    override fun onClick(v: View?) {
        when(v?.id){
            R.id.post_container -> startActivity(Intent(context, PostListActivity::class.java))
            R.id.apply_container -> startActivity(Intent(context, ApplyListActivity::class.java))
            R.id.scrap_container -> startActivity(Intent(context, ScrapListActivity::class.java))
            R.id.edit_info_btn -> startActivity(Intent(context, EditProfileActivity::class.java))
            R.id.account_setting -> startActivity(Intent(context, EditProfileActivity::class.java))
            R.id.noti_setting -> startActivity(Intent(context, EditProfileActivity::class.java))
            R.id.question -> startActivity(Intent(context, AskActivity::class.java))
        }
    }

}