package com.hmproductions.cubetimer.utils

import java.text.SimpleDateFormat
import java.util.*

fun getTimerFormatString(elapsed: Long): String {

    if (elapsed == -1L) return "-"

    var temp = if (elapsed / 60000 > 0) {
        if (elapsed / 60000 < 10) {
            "0" + (elapsed / 60000) + ":"
        } else
            (elapsed / 60000).toString() + ":"
    } else
        ""

    temp += (if (elapsed % 60000 / 1000 < 10) "0" else "") + (elapsed % 60000 / 1000).toString() + "."

    temp += if (elapsed % 1000 < 10) "0" else ""
    temp += if (elapsed % 1000 < 100) "0" else ""
    temp += elapsed % 1000
    return temp
}

fun getDateFromTimeInMillis(timeInMillis: Long) : String {
    return SimpleDateFormat("dd MMM HH:mm", Locale.US).format(Date(timeInMillis))
}