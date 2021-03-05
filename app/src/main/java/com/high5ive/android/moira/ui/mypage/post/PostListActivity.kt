package com.high5ive.android.moira.ui.mypage.post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.PostAdapter
import com.high5ive.android.moira.data.Post
import com.high5ive.android.moira.ui.applicant.ApplicantListActivity
import com.high5ive.android.moira.ui.teamfinding.recruit.RecruitDetailActivity
import kotlinx.android.synthetic.main.activity_recruit_list.*

class PostListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)


        val postList = arrayListOf<Post>()
        for (i in 0..20){
            postList.add(Post("모집글 제목 모집글 제목 모집글 제목 모집글 제목 $i"))
        }

        recycler_view.apply{
            layoutManager = LinearLayoutManager(this@PostListActivity)
            adapter =
                PostAdapter(postList) { post, type ->
                    Toast.makeText(this@PostListActivity, "$post", Toast.LENGTH_SHORT).show()
                    if (type==0) {
                        startActivity(Intent(this@PostListActivity, RecruitDetailActivity::class.java))
                    }
                    else if(type==1){
                        startActivity(Intent(this@PostListActivity, ApplicantListActivity::class.java))
                    }

                }
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
