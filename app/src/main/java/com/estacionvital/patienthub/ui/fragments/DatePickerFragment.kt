package com.estacionvital.patienthub.ui.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import com.estacionvital.patienthub.R
import java.util.*

/**
 * Created by kevin on 5/3/2018.
 */
/*Fragment para implementacion de datepicker*/
class DatePickerFragment: DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var mListener: DatePickerListener? = null

    interface DatePickerListener{
        fun OnDateSelected(year: Int, month: Int, day: Int)
    }

    private fun setListener(listener: DatePickerListener) {
        this.mListener = listener
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c : Calendar = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(activity,  R.style.CustomDatePickerDialog, this, year, month, day)
        return datePickerDialog
    }
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        mListener?.OnDateSelected(year, month, day)
    }
    companion object {
        fun newInstance(listener: DatePickerListener): DatePickerFragment{
            val fragment = DatePickerFragment()
            fragment.setListener(listener)
            return fragment
        }
    }
}