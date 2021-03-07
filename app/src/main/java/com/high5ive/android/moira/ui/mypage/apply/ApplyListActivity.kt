package com.high5ive.android.moira.ui.mypage.apply

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.ApplyAdapter
import com.high5ive.android.moira.data.Apply
import kotlinx.android.synthetic.main.activity_apply_list.*


class ApplyListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)


        val applyList = arrayListOf<Apply>()
        for (i in 0..10){
            applyList.add(Apply("모집글 제목 모집글 제목 모집글 제목 모집글 제목 $i"))
        }

        recycler_view.apply{
            layoutManager = LinearLayoutManager(this@ApplyListActivity)
            adapter =
                ApplyAdapter(applyList) { apply, type ->
                    Toast.makeText(this@ApplyListActivity, "$apply", Toast.LENGTH_SHORT).show()

//                    if (type==0) {
//                        startActivity(Intent(this@ApplyListActivity, RecruitDetailActivity::class.java))
//                    }
//                    else if(type==1){
//                        startActivity(Intent(this@ApplyListActivity, ApplicantListActivity::class.java))
//                    }
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
