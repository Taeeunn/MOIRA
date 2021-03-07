package com.high5ive.android.moira.ui.teamfinding.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.google.android.material.tabs.TabLayoutMediator
import com.high5ive.android.moira.R
import com.high5ive.android.moira.common.MemberViewPagerAdapter
import com.high5ive.android.moira.ui.message.MessageHistoryActivity
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.my_team_fragment.tabLayout
import kotlinx.android.synthetic.main.my_team_fragment.viewPager

class UserProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        viewPager.adapter =
            MemberViewPagerAdapter(this)
        val tabLayoutTextArray = arrayOf("사용자 정보","사용자 평가")
        TabLayoutMediator(tabLayout,viewPager){tab,position->
            tab.text = tabLayoutTextArray[position]

        }.attach()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        send_msg_btn.setOnClickListener {
            startActivity(Intent(this, MessageHistoryActivity::class.java))
        }
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
}
