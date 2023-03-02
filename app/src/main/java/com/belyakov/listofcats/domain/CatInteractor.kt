package com.belyakov.listofcats.domain

import com.belyakov.listofcats.data.database.Cat
import kotlinx.coroutines.flow.Flow

interface CatInteractor {

    suspend fun getCats(limit: Int, page: Int): List<Cat>

    suspend fun getCatList(page: Int, limit: Int = 10): List<Cat>

    fun getFavoriteCats(): Flow<List<Cat>>

    suspend fun addCatToFavorites(cat: Cat)

    suspend fun removeCatFromFavorites(cat: Cat)
}