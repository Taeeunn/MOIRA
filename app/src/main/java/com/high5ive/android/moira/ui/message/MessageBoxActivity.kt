package com.high5ive.android.moira.ui.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.high5ive.android.moira.R
import kotlinx.android.synthetic.main.activity_message_box.*

class MessageBoxActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_box)

        close_button.setOnClickListener {
            finish()
        }
    }
}