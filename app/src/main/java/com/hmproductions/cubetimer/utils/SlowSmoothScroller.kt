package com.hmproductions.cubetimer.utils

import android.content.Context
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearSmoothScroller

class SlowSmoothScroller(context: Context) : LinearSmoothScroller(context) {

    init {
       targetPosition = 0
    }

    override fun getVerticalSnapPreference(): Int {
        return SNAP_TO_START
    }

    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
        return 1000F / displayMetrics.densityDpi;
    }
}