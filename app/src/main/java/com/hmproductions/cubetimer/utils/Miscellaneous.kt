package com.hmproductions.cubetimer.utils

import com.github.mikephil.charting.data.Entry
import java.text.SimpleDateFormat
import java.util.*

fun getTimerFormatString(elapsed: Long): String {

    if (elapsed == -1L || elapsed == Long.MAX_VALUE) return "-"

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

fun getDateFromTimeInMillis(timeInMillis: Long): String {
    return SimpleDateFormat("dd MMM HH:mm a", Locale.US).format(Date(timeInMillis))
}

fun getRefinedEntries(oldEntries: MutableList<Entry>): MutableList<Entry> {
    val newEntries = mutableListOf<Entry>()

    for (i in 0 until oldEntries.size - 1) {
        newEntries.add(oldEntries[i])

        val x1 = newEntries[newEntries.size - 1].x
        val x2 = oldEntries[i + 1].x
        val y1 = newEntries[newEntries.size - 1].y
        val y2 = oldEntries[i + 1].y

        for (j in 1 until 20) {
            val tempX = x1 + j / 20f
            newEntries.add(Entry(tempX, (y2 - y1) / (x2 - x1) * (tempX - x1) + y1))
        }
    }

    newEntries.add(oldEntries[oldEntries.size - 1])

    return newEntries
}