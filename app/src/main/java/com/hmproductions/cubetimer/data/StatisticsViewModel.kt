package com.hmproductions.cubetimer.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.hmproductions.cubetimer.room.StatisticsRepository
import com.hmproductions.cubetimer.room.StatisticsRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class StatisticsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: StatisticsRepository
    val rubikStatistics: LiveData<List<Statistic>>
    val revengeStatistics: LiveData<List<Statistic>>
    val professorStatistics: LiveData<List<Statistic>>

    var running = false
    var ready = false
    var currentTime = 0L
    var currentCubeType = CubeType.RUBIK // It will be set from MainActivity's onCreate()

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    init {
        val statisticsDao = StatisticsRoomDatabase.getDatabase(application).statisticsDao()
        repository = StatisticsRepository(statisticsDao)
        rubikStatistics = repository.rubikStatistics
        revengeStatistics = repository.revengeStatistics
        professorStatistics = repository.professorStatistics
    }

    fun setCubeTypeFromPreferences(cubeType: CubeType) {
        currentCubeType = cubeType
    }

    fun insertStatistic(time: String, scramble: String, timerInMillis: Long, currentTimeInMillis: Long) =
        scope.launch(Dispatchers.IO) {
        repository.insert(Statistic(0, timerInMillis, time, scramble, currentTimeInMillis, currentCubeType))
    }
}