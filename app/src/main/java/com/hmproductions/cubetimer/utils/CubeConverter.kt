package com.hmproductions.cubetimer.utils

import androidx.room.TypeConverter
import com.hmproductions.cubetimer.data.CubeType

class CubeConverter {

    @TypeConverter
    fun fromCubeType(cubeType: CubeType): String {
        return when (cubeType) {
            CubeType.RUBIK -> "RUBIK"
            CubeType.REVENGE -> "REVENGE"
            CubeType.PROFESSOR -> "PROFESSOR"
        }
    }

    @TypeConverter
    fun fromCubeTypeString(cubeTypeString: String): CubeType {
        return when (cubeTypeString) {
            "RUBIK" -> CubeType.RUBIK
            "REVENGE" -> CubeType.REVENGE
            else -> CubeType.PROFESSOR
        }
    }
}