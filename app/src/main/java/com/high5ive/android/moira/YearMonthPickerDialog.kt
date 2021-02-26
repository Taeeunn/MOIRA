package com.high5ive.android.moira

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import java.util.*

class YearMonthPickerDialog<Button : View?>: DialogFragment() {
    private var listener: DatePickerDialog.OnDateSetListener? = null
    private final val MAX_YEAR = 2099
    private final val MIN_YEAR = 1980

    var cal = Calendar.getInstance()
    fun setListener(listener: DatePickerDialog.OnDateSetListener?) {
        this.listener = listener
    }

    var btnConfirm: Button? = null
    var btnCancel: Button? = null
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder =
            AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        val dialog: View = inflater.inflate(R.layout.year_month_picker, null).also {
            btnConfirm = it.findViewById<Button>(R.id.confirm_btn)
            btnCancel = it.findViewById<Button>(R.id.cancle_btn)
        }

        val monthPicker =
            dialog.findViewById<View>(R.id.picker_month) as NumberPicker
        val yearPicker =
            dialog.findViewById<View>(R.id.picker_year) as NumberPicker
        btnConfirm?.setOnClickListener(View.OnClickListener {

            listener!!.onDateSet(null, yearPicker.value, monthPicker.value, 0)

            dismiss()

        })
        monthPicker.minValue = 1
        monthPicker.maxValue = 12
        monthPicker.value = cal[Calendar.MONTH] + 1
        val year = cal[Calendar.YEAR]
        yearPicker.minValue = MIN_YEAR
        yearPicker.maxValue = MAX_YEAR
        yearPicker.value = year
        builder.setView(dialog)

        return builder.create()
    }

}