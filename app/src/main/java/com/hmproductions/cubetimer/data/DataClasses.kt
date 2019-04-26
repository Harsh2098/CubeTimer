package com.hmproductions.cubetimer.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cube_statistics")
class Statistic(@PrimaryKey(autoGenerate = true) val id: Long, val timeInMillis: Long, val timeString: String, val scramble: String,
                val realTimeInMillis: Long, val cubeType: CubeType)

data class Record(val dbId: Long, val number: Int, val time: String, val mo3: String, val ao5: String, val ao12: String,
                  val scramble: String, val dateString: String)

enum class CubeType {
    RUBIK, REVENGE, PROFESSOR
}