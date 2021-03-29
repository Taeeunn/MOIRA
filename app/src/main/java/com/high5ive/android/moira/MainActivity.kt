package com.high5ive.android.moira

import android.content.*
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.high5ive.android.moira.data.retrofit.FCMRegister
import com.high5ive.android.moira.data.retrofit.LoginInfo
import com.high5ive.android.moira.data.retrofit.LoginUser
import com.high5ive.android.moira.data.retrofit.ResponseData
import com.high5ive.android.moira.network.RetrofitClient
import com.high5ive.android.moira.network.RetrofitService
import com.high5ive.android.moira.ui.home.HomeFragment
import com.kakao.sdk.common.util.Utility
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_fragment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class MainActivity : AppCompatActivity() {

//    private var receiver: BroadcastReceiver? = null
//    private var mIsReceiverRegistered: Boolean = false
//    lateinit var retrofit: Retrofit
//    lateinit var myAPI: RetrofitService
//
//    lateinit var jwt_token: String

//    var fcm_token= ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val preferences: SharedPreferences =
//            this.getSharedPreferences("moira", Context.MODE_PRIVATE)
//        jwt_token = preferences.getString("jwt_token", "").toString()
//
//        initRetrofit()
        bottomNavigationView.setupWithNavController(fragment.findNavController())

        var keyHash = Utility.getKeyHash(this)

//        navController.addOnDestinationChangedListener { _, destination, _ ->
//
//            if(destination.id == R.id.first_fragment) {
//                // your intro fragment will hide your bottomNavigationView
//                bottomNavigationView.visibility = View.GONE
//            } else if (destination.id == R.id.second_fragment){
//                // your second fragment will show your bottomNavigationView
//                bottomNavigationView.visibility = View.VISIBLE
//            }
//        }

//        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(
//            this@MainActivity,
//            OnSuccessListener<InstanceIdResult> { instanceIdResult ->
//                val token = instanceIdResult.token
//
//                fcm_token = token
//                Log.i("FCM Token", token)
//
//                registerFCM(fcm_token)
//
//
//            })
//
        Log.v("hash", keyHash)
    }

//    private fun initRetrofit() {
//
//        retrofit = RetrofitClient.getInstance() // 2에서 만든 Retrofit client의 instance를 불러옵니다.
//        myAPI = retrofit.create(RetrofitService::class.java) // 여기서 retrofit이 우리의 interface를 구현해주고
//
//    }
//
//    private fun registerFCM(fcm_token: String){
//        val body_data = FCMRegister(fcm_token)
//        myAPI.registerUser(jwt_token, body_data).enqueue(object : Callback<ResponseData> {
//            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
//                t.printStackTrace()
//            }
//
//            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
//                Log.v("realcode", response.code().toString())
//                val code: Int = response.body()?.code ?: 0
//                val msg: String = response.body()?.msg ?: "no msg"
//                val succeed: Boolean = response.body()?.succeed ?: false
//
//                if(succeed) {
//                    pushNotification()
//                }
//                Log.v("code", code.toString())
//                Log.v("success", succeed.toString())
//                Log.v("msg", msg)
//
//
//            }
//        })
//    }

//    private fun pushNotification() {
//        myAPI.pushFCM(jwt_token).enqueue(object : Callback<ResponseData> {
//            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
//                t.printStackTrace()
//            }
//
//            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
//                Log.v("realcode", response.code().toString())
//                val code: Int = response.body()?.code ?: 0
//                val msg: String = response.body()?.msg ?: "no msg"
//                val succeed: Boolean = response.body()?.succeed ?: false
//
//
//
//                Log.v("code", code.toString())
//                Log.v("success", succeed.toString())
//                Log.v("msg", msg)
//
//
//            }
//        })
//    }
//
//
//    private fun startRegisterReceiver() {
//        if (!mIsReceiverRegistered) {
//            if (receiver == null) {
//                receiver = object : BroadcastReceiver() {
//                    override fun onReceive(context: Context?, intent: Intent?) {
////                        notify_icon.setVisibility(View.VISIBLE)
//                        Log.v("received", "ss")
//
////                        val mf: HomeFragment = supportFragmentManager.findFragmentById(R.id.homeFragment) as HomeFragment
////                        mf.newNoti()
//
////                        ((HomeFragment) Suppor.findFragmentByTag("fragmentHome")).testFunction();
//
//
//                    }
//                }
//            }
//            registerReceiver(receiver, IntentFilter("com.package.notification"))
//            mIsReceiverRegistered = true
//        }
//    }
//
//    private fun finishRegisterReceiver() {
//        if (mIsReceiverRegistered) {
//            unregisterReceiver(receiver)
//            receiver = null
//            mIsReceiverRegistered = false
//        }
//    }
//
//    private fun pauseRegisterReceiver() {
//        if (mIsReceiverRegistered) {
//            mIsReceiverRegistered = false
//        }
//    }
//
//
//    override fun onResume() {
//        super.onResume()
//        startRegisterReceiver()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        pauseRegisterReceiver()
//    }
//
//
//
////    override fun onDestroy() {
////        super.onDestory()
////        finishRegisterReceiver()
////    }
//
//    override fun onStart() {
//        super.onStart()
//        startRegisterReceiver()
//    }
}