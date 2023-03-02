package com.belyakov.listofcats.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cat::class], version = 1)
abstract class CatDatabase : RoomDatabase() {

    abstract fun catDao(): CatDao

    companion object {
        @Volatile
        private var INSTANCE: CatDatabase? = null

        fun getDatabase(context: Context): CatDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CatDatabase::class.java,
                    "cat_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}