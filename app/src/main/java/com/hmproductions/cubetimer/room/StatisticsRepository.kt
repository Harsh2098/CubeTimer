package com.hmproductions.cubetimer.room

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.hmproductions.cubetimer.data.CubeType
import com.hmproductions.cubetimer.data.Statistic

class StatisticsRepository(private val statisticsDao: StatisticsDao) {

    val revengeStatistics: LiveData<List<Statistic>> = statisticsDao.get4x4Statistics()
    val rubikStatistics: LiveData<List<Statistic>> = statisticsDao.get3x3Statistics()
    val professorStatistics: LiveData<List<Statistic>> = statisticsDao.get5x5Statistics()

    val bestTime: LiveData<Long> = statisticsDao.getBestTime()

    @WorkerThread
    fun insert(statistic: Statistic) {
        statisticsDao.insertStatistic(statistic)
    }

    @WorkerThread
    fun delete(dbId: Long) {
        statisticsDao.deleteStatistic(dbId)
    }

    @WorkerThread
    fun deleteCubeType(cubeType: CubeType) {
        statisticsDao.deleteCubeTypeStatistics(cubeType)
    }
}