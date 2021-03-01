package com.high5ive.android.moira.ui.notify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.my_team_fragment.*

class NotifyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notify)

        viewPager.adapter = ViewPagerAdapter(this)
        val tabLayoutTextArray = arrayOf("지원 소식","받은 초대")
        TabLayoutMediator(tabLayout,viewPager){tab,position->
            tab.text = tabLayoutTextArray[position]

        }.attach()
    }
}