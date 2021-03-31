package com.high5ive.android.moira.ui.teamfinding.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.high5ive.android.moira.R
import com.high5ive.android.moira.common.MemberViewPagerAdapter
import kotlinx.android.synthetic.main.activity_user_profile_detail.*
import kotlinx.android.synthetic.main.my_team_fragment.tabLayout
import kotlinx.android.synthetic.main.my_team_fragment.viewPager

class UserProfileDetailActivity : AppCompatActivity() {

    var userId: Int = 1
    var imageUrl: String = ""
    var nickname: String = ""
    var positionName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile_detail)

        userId = intent.getIntExtra("index", 1)
        imageUrl = intent.getStringExtra("image")?: ""
        nickname = intent.getStringExtra("nickname")?: ""
        positionName = intent.getStringExtra("position")?: ""

        Glide.with(this)
            .load(imageUrl)
            .override(20, 20)
            .error(R.drawable.ic_baseline_person_24) // ex) error(R.drawable.error)
            .into(member_image)

        member_position.text = positionName
        member_nickname.text = nickname


        viewPager.adapter =
            MemberViewPagerAdapter(this, userId, userId, "user")
        val tabLayoutTextArray = arrayOf("사용자 정보","사용자 평가")
        TabLayoutMediator(tabLayout,viewPager){tab,position->
            tab.text = tabLayoutTextArray[position]

        }.attach()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)


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
