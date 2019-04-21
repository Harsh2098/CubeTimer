package com.hmproductions.cubetimer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.hmproductions.cubetimer.data.StatisticsRepository
import com.hmproductions.cubetimer.data.StatisticsRoomDatabase

class StatisticsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: StatisticsRepository
    val rubikStatistics: LiveData<List<Statistic>>
    val revengeStatistics: LiveData<List<Statistic>>
    val professorStatistics: LiveData<List<Statistic>>

    init {
        val statisticsDao = StatisticsRoomDatabase.getDatabase(application).statisticsDao()
        repository = StatisticsRepository(statisticsDao)
        rubikStatistics = repository.rubikStatistics
        revengeStatistics = repository.revengeStatistics
        professorStatistics = repository.professorStatistics
    }

    fun insertStatistic(statistic: Statistic) {
        repository.insert(statistic)
    }
}