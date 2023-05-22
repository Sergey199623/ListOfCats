package com.belyakov.listofcats.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CatDao {
    @Query("SELECT * FROM cats")
    fun getAllCats(): Flow<List<Cat>>

    @Query("SELECT * FROM cats WHERE isFavorite = 1")
    fun getFavoriteCats(): Flow<List<Cat>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cat: Cat)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cats: List<Cat>)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(cat: Cat)

    @Delete
    suspend fun delete(cat: Cat)
}