package com.high5ive.android.moira.ui.mypage.scrap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.my_team_fragment.*

class ScrapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrap)

        viewPager.adapter = ScrapViewPagerAdapter(this)
        val tabLayoutTextArray = arrayOf("모집글","인재풀")
        TabLayoutMediator(tabLayout,viewPager){tab,position->
            tab.text = tabLayoutTextArray[position]

        }.attach()
    }
}