package com.high5ive.android.moira.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.high5ive.android.moira.R
import kotlinx.android.synthetic.main.activity_set_nickname.*

class SetNicknameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_nickname)

        to_next_btn.setOnClickListener {
            startActivity(Intent(this, SetPositionActivity::class.java))
        }
    }
}