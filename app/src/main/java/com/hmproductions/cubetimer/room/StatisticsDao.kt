package com.hmproductions.cubetimer.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hmproductions.cubetimer.data.Statistic

@Dao
interface StatisticsDao {

    @Query("SELECT * from cube_statistics WHERE cubeType=\"RUBIK\" ORDER BY realTimeInMillis DESC")
    fun get3x3Statistics(): LiveData<List<Statistic>>

    @Query("SELECT * from cube_statistics WHERE cubeType=\"REVENGE\" ORDER BY realTimeInMillis DESC")
    fun get4x4Statistics(): LiveData<List<Statistic>>

    @Query("SELECT * from cube_statistics WHERE cubeType=\"PROFESSOR\" ORDER BY realTimeInMillis DESC")
    fun get5x5Statistics(): LiveData<List<Statistic>>

    @Insert
    fun insertStatistic(statistic: Statistic)

    @Query("DELETE FROM cube_statistics WHERE id=:dbId")
    fun deleteStatistic(dbId: Long)
}