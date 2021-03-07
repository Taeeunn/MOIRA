package com.high5ive.android.moira.ui.mypage.edit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.high5ive.android.moira.R
import com.high5ive.android.moira.ui.mypage.MyPageFragment
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.edit_info.view.*


class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        register_button.setOnClickListener{
            startActivity(Intent(this, MyPageFragment::class.java))
        }

        var tagList = mutableListOf<String>()
        tagList.add("관련태그1")
        tagList.add("관련태그2")
        tagList.add("관련태그3")
        setTag(tagList);
    }

    private fun setTag(tagList: MutableList<String>) {
        for (index in tagList.indices) {
            val tagName = tagList[index]
            //val chip = Chip(ContextThemeWrapper(context, R.style.MaterialChipsAction))
            val chip = Chip(this)
            val drawable = ChipDrawable.createFromAttributes(this, null, 0, R.style.MaterialChipsAction)
            chip.setChipDrawable(drawable)

            chip.text = tagName
            chip.setTextAppearance(R.style.tag_text)
            chip.setCloseIconResource(R.drawable.ic_baseline_close_24)
            chip.isCloseIconEnabled = true
            //Added click listener on close icon to remove tag from ChipGroup
            chip.setOnCloseIconClickListener {
                tagList.remove(tagName)
                edit_info.interest_tag_group.removeView(chip)
            }
            edit_info.interest_tag_group.addView(chip)
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