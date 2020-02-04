package com.example.testtask.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testtask.constants.DB_NAME
import com.example.testtask.entity.MovieDBEntity
import com.example.testtask.repository.database.MovieDao

@Database(
    version = 1,
    entities = [
        MovieDBEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(AppDatabase::class.java) {
                INSTANCE ?: buildDatabase(context)
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .build()
        }
    }
}
