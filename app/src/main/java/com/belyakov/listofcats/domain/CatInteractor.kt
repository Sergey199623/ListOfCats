package com.belyakov.listofcats.domain

import com.belyakov.listofcats.data.database.Cat
import kotlinx.coroutines.flow.Flow

interface CatInteractor {

    suspend fun getCatList(page: Int, limit: Int): List<Cat>

    fun getFavoriteCats(): Flow<List<Cat>>

    suspend fun changeFavoriteStatusCat(cat: Cat)

    suspend fun removeCatFromFavorites(cat: Cat)

    suspend fun downloadImage(url: String): Boolean
}