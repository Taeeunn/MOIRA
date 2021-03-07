package com.high5ive.android.moira.ui.teamfinding.recruit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.CommentAdapter
import com.high5ive.android.moira.data.Comment
import kotlinx.android.synthetic.main.activity_comment.*


class CommentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)

        close_button.setOnClickListener {
            finish()
        }

        val commentList = arrayListOf<Comment>()
        for (i in 0..10){
            commentList.add(
                Comment(
                    "사용자 닉네임 $i",
                    "2021.01.12",
                    "댓글 내용입니다. 댓글 내용입니다. 댓글 내용입니다. 댓글 내용입니다. $i"
                )
            )
        }

        recycler_view.apply{
            layoutManager = LinearLayoutManager(this@CommentActivity)
            adapter =
                CommentAdapter(commentList) { comment ->
                    Toast.makeText(this@CommentActivity, "$comment", Toast.LENGTH_SHORT).show()
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