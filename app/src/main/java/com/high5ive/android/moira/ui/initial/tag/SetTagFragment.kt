package com.high5ive.android.moira.ui.initial.tag

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.high5ive.android.moira.MainActivity
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.*
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import com.high5ive.android.moira.ui.initial.OnTransitionListener
import kotlinx.android.synthetic.main.set_nickname_fragment.*
import kotlinx.android.synthetic.main.set_tag_fragment.*
import kotlinx.android.synthetic.main.set_tag_fragment.to_next_btn
import kotlinx.android.synthetic.main.set_tag_fragment.toolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SetTagFragment : Fragment() {

    companion object {
        fun newInstance() =
            SetTagFragment()
    }

    private lateinit var viewModel: SetTagViewModel
    lateinit var navController : NavController
    private var onTransitionListener: OnTransitionListener? = null

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService

    lateinit var jwt_token: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.set_tag_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        val preferences: SharedPreferences =requireActivity().getSharedPreferences("moira", Context.MODE_PRIVATE)
        jwt_token = preferences.getString("jwt_token", null).toString()

        Log.v("Jwt", jwt_token)
        to_next_btn.setOnClickListener {
//            startActivity(Intent(activity, MainActivity::class.java))

            signupUser()
//            onTransitionListener?.OnTransitionListener()
        }

        initRetrofit()
        getPositionDetail()
        getHashTags()


        setHasOptionsMenu(true);
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        val ab = (activity as AppCompatActivity?)!!.supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SetTagViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onTransitionListener = context as OnTransitionListener
    }

    override fun onDetach() {
        super.onDetach()
        onTransitionListener = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                Log.v("hello", "hello")
                navController.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRetrofit() {

        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
        myAPI = retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
    }

    private fun getPositionDetail() {
        Runnable {

            myAPI.getPositionDetail(1).enqueue(object : Callback<PositionDetail> {
                override fun onFailure(call: Call<PositionDetail>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<PositionDetail>,
                    response: Response<PositionDetail>
                ) {
                    val code: Int = response.body()?.code ?: 0
                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false
                    val list: List<PositionDetailItem> = response.body()?.list ?: emptyList()



                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)
                    Log.v("list", list.toString())

//                    if (firstLogin){
//                        navController.navigate(R.id.action_loginFragment_to_setNicknameFragment)
//                    } else{
//                        startActivity(Intent(context, MainActivity::class.java))
//                    }

                }
            })
        }.run()
    }

    private fun getHashTags() {
        Runnable {

            myAPI.getHashTags().enqueue(object : Callback<HashTags> {
                override fun onFailure(call: Call<HashTags>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<HashTags>, response: Response<HashTags>) {
                    val code: Int = response.body()?.code ?: 0
                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false
                    val list: List<HashTagItem> = response.body()?.list ?: emptyList()



                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)
                    Log.v("list", list.toString())

//                    if (firstLogin){
//                        navController.navigate(R.id.action_loginFragment_to_setNicknameFragment)
//                    } else{
//                        startActivity(Intent(context, MainActivity::class.java))
//                    }

                }
            })
        }.run()
    }

    private fun signupUser() {
        Runnable {

            val hashtagIdList: List<Int> = listOf(1)
            val nickname: String = "moira2"
            val positionId: Int = 1

            val body_data = SignUpInfo(hashtagIdList, nickname, positionId)
            Log.v("body", body_data.toString() )
            myAPI.signupUser(jwt_token, body_data).enqueue(object : Callback<ResponseData> {
                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                    Log.v("code", response.code().toString())
                    Log.v("msg", response.message())
                    val code: Int = response.body()?.code ?: 0
                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false


                    Log.v("api", call.request().header("X-AUTH-TOKEN").toString())
                    Log.v("api", call.request().body().toString())
                    Log.v("code", code.toString())
                    Log.v("success", succeed.toString())
                    Log.v("msg", msg)

//                    if (firstLogin){
//                        navController.navigate(R.id.action_loginFragment_to_setNicknameFragment)
//                    } else{
//                        startActivity(Intent(context, MainActivity::class.java))
//                    }

                    if(succeed){
                        startActivity(Intent(context, MainActivity::class.java))
                    }

                }
            })
        }.run()
    }

}