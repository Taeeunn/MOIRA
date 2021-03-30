package com.high5ive.android.moira.ui.mypage.cs

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import com.high5ive.android.moira.R
import com.high5ive.android.moira.ui.initial.InitialActivity
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_account_setting.*

class AccountSettingActivity : AppCompatActivity(), View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_setting)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        logout.setOnClickListener(this)
        exit.setOnClickListener(this)
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
            R.id.logout -> {
                val message = "로그아웃이 완료되었습니다!"
                Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()

                val preferences: SharedPreferences =
                    this.getSharedPreferences("moira", Context.MODE_PRIVATE)
                preferences.edit().putString("jwt_token", "").apply()
                executeSignOut()
            }

            R.id.exit -> {
                // 연결 끊기
                UserApiClient.instance.unlink { error ->
                    if (error != null) {
                        val message = "탈퇴가 불가능합니다!"
                        Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()
                        Log.e("ttt", "연결 끊기 실패", error)
                        executeSignOut()
                    }
                    else {
                        val message = "탈퇴가 완료되었습니다!"
                        Snackbar.make(v, message, Snackbar.LENGTH_SHORT).show()
                        Log.i("ttt", "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                        val preferences: SharedPreferences =
                            this.getSharedPreferences("moira", Context.MODE_PRIVATE)
                        preferences.edit().putString("jwt_token", "").apply()

                        executeSignOut()
                    }
                }
            }
        }
    }

    private fun executeSignOut() {

        val intent = Intent(this, InitialActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}