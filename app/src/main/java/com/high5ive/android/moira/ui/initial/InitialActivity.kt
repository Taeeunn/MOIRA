package com.high5ive.android.moira.ui.initial

import android.content.BroadcastReceiver
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.high5ive.android.moira.MainActivity
import com.high5ive.android.moira.R
import com.high5ive.android.moira.network.RetrofitService
import retrofit2.Retrofit

class InitialActivity : AppCompatActivity(), OnTransitionListener{


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

//        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(
//            this,
//            OnSuccessListener<InstanceIdResult> { instanceIdResult ->
//                val token = instanceIdResult.token
//
//                fcm_token = token
//                Log.i("FCM Token", token)
//
//                registerFCM(fcm_token)
//            })

    }

    override fun OnTransitionListener() {
        startActivity(Intent(this, MainActivity::class.java))
    }

}