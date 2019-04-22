package com.hmproductions.cubetimer.room

import android.content.Context
import androidx.room.*
import com.hmproductions.cubetimer.data.Statistic
import com.hmproductions.cubetimer.utils.CubeConverter

@Database(entities = [Statistic::class], version = 1)
@TypeConverters(CubeConverter::class)
abstract class StatisticsRoomDatabase : RoomDatabase() {

    abstract fun statisticsDao(): StatisticsDao

    companion object {
        @Volatile
        private var INSTANCE: StatisticsRoomDatabase? = null

        fun getDatabase(context: Context): StatisticsRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StatisticsRoomDatabase::class.java,
                    "statistics_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}