package com.belyakov.listofcats.utils

import androidx.room.Database
import androidx.room.RoomDatabase
import com.belyakov.listofcats.data.database.Cat

@Database(entities = [Cat::class], version = 1)
abstract class CatDatabaseTest : RoomDatabase() {


}