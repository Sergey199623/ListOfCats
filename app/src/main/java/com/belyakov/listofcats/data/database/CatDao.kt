package com.belyakov.listofcats.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CatDao {
    @Query("SELECT * FROM cats")
    fun getAllCats(): LiveData<List<Cat>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cat: Cat)

    @Delete
    suspend fun delete(cat: Cat)
}