package com.high5ive.android.moira.ui.addinfo

import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.high5ive.android.moira.R
import com.high5ive.android.moira.YearMonthPickerDialog
import com.high5ive.android.moira.databinding.ActivityAddEducationBinding
import kotlinx.android.synthetic.main.activity_add_education.*


class AddEducationActivity : AppCompatActivity() {

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


        admission_et.setOnClickListener {
            val pd: YearMonthPickerDialog<View> =
                YearMonthPickerDialog()
            pd.setListener(listener)
            pd.show(supportFragmentManager, "YearMonthPickerTest")
        }


        graduation_et.setOnClickListener {
            val pd: YearMonthPickerDialog<View> =
                YearMonthPickerDialog()
            pd.setListener(listener)
            pd.show(supportFragmentManager, "YearMonthPickerTest")

        }



        status_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

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