package com.estacionvital.patienthub.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by kevin on 5/3/2018.
 */
class DateUtil {
    companion object {
        fun parseDateToFormat(year: Int, month: Int, day: Int, format: String): String{
            val formatter = SimpleDateFormat(format)
            val c = Calendar.getInstance()
            c.set(year, month, day)
            val date = c.time as Date
            return formatter.format(date)
        }
    }
}