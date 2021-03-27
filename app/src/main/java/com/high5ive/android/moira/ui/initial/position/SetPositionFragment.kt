package com.high5ive.android.moira.ui.initial.position

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.high5ive.android.moira.R
import com.high5ive.android.moira.data.retrofit.PositionCategory
import com.high5ive.android.moira.data.retrofit.PositionCategoryResponse
import com.high5ive.android.moira.data.retrofit.PositionItem
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import kotlinx.android.synthetic.main.set_nickname_fragment.*
import kotlinx.android.synthetic.main.set_position_fragment.*
import kotlinx.android.synthetic.main.set_position_fragment.to_next_btn
import kotlinx.android.synthetic.main.set_position_fragment.toolbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SetPositionFragment : Fragment() {

    companion object {
        fun newInstance() =
            SetPositionFragment()
    }

    private lateinit var viewModel: SetPositionViewModel

    lateinit var navController : NavController
    lateinit var position: String

    lateinit var retrofit: Retrofit
    lateinit var myAPI: RetrofitService
    var nickname = ""



    var develop: Boolean = false
    var plan: Boolean = false
    var design: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.set_position_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        nickname = arguments?.getString("nickname")?: ""
        Log.v("tnickname", nickname)
        initRetrofit()
        getPositionCategory()

        to_next_btn.setOnClickListener {
            navController.navigate(R.id.action_setPositionFragment_to_setTagFragment)
        }

        develop_btn.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                design_btn.isChecked = false
                plan_btn.isChecked = false
                position = "develop"
            }
        }



        plan_btn.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                develop_btn.isChecked = false
                design_btn.isChecked = false
                position = "plan"
            }
        }

        design_btn.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                develop_btn.isChecked = false
                plan_btn.isChecked = false
                position = "design"
            }
        }

        setHasOptionsMenu(true);
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        val ab = (activity as AppCompatActivity?)!!.supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SetPositionViewModel::class.java)
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

    private fun getPositionCategory() {
        Runnable {

            myAPI.getPositionCategories().enqueue(object : Callback<PositionCategoryResponse> {
                override fun onFailure(call: Call<PositionCategoryResponse>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<PositionCategoryResponse>,
                    response: Response<PositionCategoryResponse>
                ) {
                    val code: Int = response.body()?.code ?: 0
                    val msg: String = response.body()?.msg ?: "no msg"
                    val succeed: Boolean = response.body()?.succeed ?: false
                    val list: List<PositionItem> = response.body()?.list ?: emptyList()



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

}