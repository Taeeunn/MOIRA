package com.high5ive.android.moira.ui.teamfinding.apply

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.high5ive.android.moira.MainActivity
import com.high5ive.android.moira.R
import com.high5ive.android.moira.ui.mypage.apply.ApplyListActivity
import com.high5ive.android.moira.ui.teamfinding.recruit.CommentActivity
import kotlinx.android.synthetic.main.activity_apply_complete.*

class ApplyCompleteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_complete)

        to_home_btn.setOnClickListener {
            startActivity(Intent(this@ApplyCompleteActivity, MainActivity::class.java))
        }

        show_apply_list_btn.setOnClickListener {
            startActivity(Intent(this@ApplyCompleteActivity, ApplyListActivity::class.java))
        }


    }
}