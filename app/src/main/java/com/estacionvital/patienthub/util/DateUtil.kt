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
        fun parseDateToFormat(date: Date, format: String): String{
            val formatter = SimpleDateFormat(format)
            return formatter.format(date)
        }
        fun parseDateStringToFormat(date: String, originFormat: String, targetFormat: String): String{
            var formatter = SimpleDateFormat(originFormat)
            val date = formatter.parse(date)
            formatter = SimpleDateFormat(targetFormat)
            return formatter.format(date)
        }
    }
}