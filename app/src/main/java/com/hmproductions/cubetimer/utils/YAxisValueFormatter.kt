package com.hmproductions.cubetimer.utils

import com.github.mikephil.charting.formatter.ValueFormatter

class YAxisValueFormatter : ValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        val timeStamp = getTimerFormatString(value.toLong())
        return timeStamp.substring(0, timeStamp.length - 4)
    }
}