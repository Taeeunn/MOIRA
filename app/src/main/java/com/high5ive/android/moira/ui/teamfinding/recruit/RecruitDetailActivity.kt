package com.high5ive.android.moira.ui.teamfinding.recruit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.google.android.material.snackbar.Snackbar
import com.high5ive.android.moira.R
import com.high5ive.android.moira.adapter.PositionAdapter
import com.high5ive.android.moira.data.Position
import com.high5ive.android.moira.ui.teamfinding.apply.ApplyActivity
import kotlinx.android.synthetic.main.activity_recruit_detail.*
import kotlinx.android.synthetic.main.activity_team_detail.*
import kotlinx.android.synthetic.main.recruit_info.*

class RecruitDetailActivity : AppCompatActivity(), View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recruit_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)


        comment_img_btn.setOnClickListener(this)
        apply_btn.setOnClickListener(this)
        more_button.setOnClickListener(this)


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

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.comment_img_btn -> startActivity(Intent(this@RecruitDetailActivity, CommentActivity::class.java))

            R.id.apply_btn -> startActivity(Intent(this@RecruitDetailActivity, ApplyActivity::class.java))

            R.id.more_button -> {
                MaterialDialog(this).show {
                    title(R.string.report_reason)
                    positiveButton(R.string.cancle)
                    positiveButton(R.string.report)

                    listItemsSingleChoice(R.array.report_reason) { _, _, text ->
                        val msg = resources.getString(R.string.report_completed) + " " + text
                        Snackbar.make(v, msg, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
