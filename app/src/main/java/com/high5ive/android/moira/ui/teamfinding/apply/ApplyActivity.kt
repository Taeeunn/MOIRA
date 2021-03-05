package com.high5ive.android.moira.ui.teamfinding.apply

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.high5ive.android.moira.R
import kotlinx.android.synthetic.main.activity_apply.*

class ApplyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply)

        complete_button.setOnClickListener {
            startActivity(Intent(this@ApplyActivity, ApplyCompleteActivity::class.java))
        }
    }
}