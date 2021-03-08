package com.high5ive.android.moira.ui.mypage.edit.addinfo.link

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.high5ive.android.moira.R
import kotlinx.android.synthetic.main.activity_add_education.*

class AddLinkActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_link)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        register_button.setOnClickListener(this)

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
            R.id.register_button -> finish()
        }
    }
}