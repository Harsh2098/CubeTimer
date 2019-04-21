package com.hmproductions.cubetimer

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cube_statistics")
class Statistic(@PrimaryKey(autoGenerate = true) val id: Long, val time: String, val ao5: String, val ao12: String, val cubeType: CubeType)

enum class CubeType {
    RUBIK, REVENGE, PROFESSOR
}