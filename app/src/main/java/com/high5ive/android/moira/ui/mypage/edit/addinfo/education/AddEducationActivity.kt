package com.high5ive.android.moira.ui.mypage.edit.addinfo.education

import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.high5ive.android.moira.R
import com.high5ive.android.moira.YearMonthPickerDialog
import com.high5ive.android.moira.databinding.ActivityAddEducationBinding
import kotlinx.android.synthetic.main.activity_add_education.*
import kotlinx.android.synthetic.main.activity_add_education.view.*


class AddEducationActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAddEducationBinding

    var listener: OnDateSetListener =
        OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            Log.i("earMonthPickerTest", "year = $year, month = $monthOfYear, day = $dayOfMonth")

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,
            R.layout.activity_add_education
        )

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)


        register_button.setOnClickListener(this)
        school_name_et.setOnClickListener(this)
        admission_et.setOnClickListener(this)
        graduation_et.setOnClickListener(this)
        status_et.setOnClickListener(this)


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

            R.id.school_name_et -> startActivity(Intent(this, AddSchoolActivity::class.java))

            R.id.admission_et -> {
                val pd: YearMonthPickerDialog<View> = YearMonthPickerDialog()
                pd.setListener(listener)
                pd.show(supportFragmentManager, "YearMonthPickerTest")
            }

            R.id.graduation_et -> {
                val pd: YearMonthPickerDialog<View> = YearMonthPickerDialog()
                pd.setListener(listener)
                pd.show(supportFragmentManager, "YearMonthPickerTest")
            }

            R.id.status_et -> {
                MaterialDialog(this).show {
                    listItemsSingleChoice(R.array.education_status_array) { _, _, text ->
                        Log.v("dismiss2", "dismiss")
                        v.status_et.setText(text)
                    }


                }
            }

        }
    }
}