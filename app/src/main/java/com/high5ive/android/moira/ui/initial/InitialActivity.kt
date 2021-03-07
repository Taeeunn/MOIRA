package com.high5ive.android.moira.ui.initial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.high5ive.android.moira.MainActivity
import com.high5ive.android.moira.R

class InitialActivity : AppCompatActivity(), OnTransitionListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

    }

    override fun OnTransitionListener() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}