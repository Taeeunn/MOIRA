package com.high5ive.android.moira.ui.teamfinding.recruit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.PositionAdapter
import com.high5ive.android.moira.data.Position
import kotlinx.android.synthetic.main.recruit_info.*

class RecruitDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recruit_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)


        val positionList = arrayListOf(
            Position("개발자", 1), Position("디자이너", 1), Position("PM", 1)
        )

      

        position_recycler_view.apply{
            layoutManager = LinearLayoutManager(this@RecruitDetailActivity)
            adapter =
                PositionAdapter(positionList) { position ->
                    Toast.makeText(this@RecruitDetailActivity, "$position", Toast.LENGTH_SHORT).show()
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
