package com.aran.githubapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataRoom::class], version = 2, exportSchema = false)
abstract class DatabaseRoom : RoomDatabase() {
    abstract fun dataDao(): DataDao

    companion object {
        @Volatile
        private var INSTANCE: DatabaseRoom? = null

        @JvmStatic
        fun getDatabase(context: Context): DatabaseRoom {
            if (INSTANCE == null) {
                synchronized(DatabaseRoom::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            DatabaseRoom::class.java, "database"
                        )
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE as DatabaseRoom
        }
    }
}