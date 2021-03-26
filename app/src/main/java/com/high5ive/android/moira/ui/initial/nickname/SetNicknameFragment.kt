package com.high5ive.android.moira.ui.initial.nickname

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.ResponseData
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import kotlinx.android.synthetic.main.set_nickname_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class SetNicknameFragment : Fragment() {

    companion object {
        fun newInstance() =
            SetNicknameFragment()
    }

    private lateinit var viewModel: SetNicknameViewModel

    lateinit var navController : NavController
    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.set_nickname_fragment, container, false)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        initRetrofit()


        to_next_btn.setOnClickListener {

            checkNickName(enter_nickname_et.text.toString())

        }

        setHasOptionsMenu(true);
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        val ab = (activity as AppCompatActivity?)!!.supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)
//        enter_nickname_et.setOnClickListener{
//            nickname_text_layout.isErrorEnabled = true;
//            nickname_text_layout.error = "*이미 사용중인 닉네임입니다.";
//        }



//
//        nickname_text_layout.isErrorEnabled = true;
//        nickname_text_layout.error = "*이미 사용중인 닉네임입니다.";
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SetNicknameViewModel::class.java)
        // TODO: Use the ViewModel
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

    private fun checkNickName(nickname: String){
        Runnable {

            myAPI.checkNickname(nickname).enqueue(object : Callback<ResponseData> {
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


                    if (succeed) {


                        val bundle = bundleOf("nickname" to nickname)
                        navController.navigate(R.id.action_setNicknameFragment_to_setPositionFragment, bundle)
                    } else{
                        nickname_text_layout.helperText = "*이미 사용중인 닉네임입니다."
                        nickname_text_layout.setHelperTextColor(resources.getColorStateList(R.color.red))
                    }
//                    if (firstLogin){
//                        navController.navigate(R.id.action_loginFragment_to_setNicknameFragment)
//                    } else{
//                        startActivity(Intent(context, MainActivity::class.java))
//                    }

                }
            })
        }.run()
    }

}