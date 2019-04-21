package com.hmproductions.cubetimer.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.hmproductions.cubetimer.Statistic

class StatisticsRepository(private val statisticsDao: StatisticsDao) {

    val revengeStatistics: LiveData<List<Statistic>> = statisticsDao.get4x4Statistics()
    val rubikStatistics: LiveData<List<Statistic>> = statisticsDao.get3x3Statistics()
    val professorStatistics: LiveData<List<Statistic>> = statisticsDao.get5x5Statistics()

    @WorkerThread
    fun insert(statistic: Statistic) {
        statisticsDao.insertStatistic(statistic)
    }
}