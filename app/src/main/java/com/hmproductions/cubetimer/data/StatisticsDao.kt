package com.hmproductions.cubetimer.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hmproductions.cubetimer.Statistic

@Dao
interface StatisticsDao {

    @Query("SELECT * from cube_statistics WHERE cubeType=\"RUBIK\"")
    fun get3x3Statistics() : LiveData<List<Statistic>>

    @Query("SELECT * from cube_statistics WHERE cubeType=\"REVENGE\"")
    fun get4x4Statistics() : LiveData<List<Statistic>>

    @Query("SELECT * from cube_statistics WHERE cubeType=\"PROFESSOR\"")
    fun get5x5Statistics() : LiveData<List<Statistic>>

    @Insert
    fun insertStatistic(statistic: Statistic)

    @Delete
    fun deleteStatistic(statistic: Statistic)
}