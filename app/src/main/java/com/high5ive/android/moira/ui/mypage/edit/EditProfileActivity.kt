package com.high5ive.android.moira.ui.mypage.edit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.high5ive.android.moira.R
import com.high5ive.android.moira.ui.addinfo.award.AddAwardHistoryActivity
import com.high5ive.android.moira.ui.addinfo.career.AddCareerActivity
import com.high5ive.android.moira.ui.addinfo.certificate.AddCertificateActivity
import com.high5ive.android.moira.ui.addinfo.education.AddEducationActivity
import com.high5ive.android.moira.ui.addinfo.link.AddLinkActivity
import com.high5ive.android.moira.ui.addinfo.tag.AddTagActivity
import com.high5ive.android.moira.ui.mypage.MyPageFragment
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.edit_info.*
import kotlinx.android.synthetic.main.edit_info.view.*


class EditProfileActivity : AppCompatActivity(), View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        var tagList = mutableListOf<String>()
        tagList.add("관련태그1")
        tagList.add("관련태그2")
        tagList.add("관련태그3")
        setTag(tagList);



        register_button.setOnClickListener(this)
        add_education_btn.setOnClickListener(this)
        add_career_btn.setOnClickListener(this)
        add_certificate_btn.setOnClickListener(this)
        add_award_btn.setOnClickListener(this)
        add_link_btn.setOnClickListener(this)
        add_tag_btn.setOnClickListener(this)
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

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.register_button -> finish()

            R.id.add_education_btn -> startActivity(Intent(this, AddEducationActivity::class.java))

            R.id.add_career_btn -> startActivity(Intent(this, AddCareerActivity::class.java))

            R.id.add_certificate_btn -> startActivity(Intent(this, AddCertificateActivity::class.java))

            R.id.add_award_btn -> startActivity(Intent(this, AddAwardHistoryActivity::class.java))

            R.id.add_link_btn -> startActivity(Intent(this, AddLinkActivity::class.java))

            R.id.add_tag_btn -> startActivity(Intent(this, AddTagActivity::class.java))
        }
    }
}