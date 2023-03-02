package com.belyakov.listofcats.presentation.viewModel

import com.belyakov.listofcats.data.database.Cat
import kotlinx.coroutines.flow.Flow

interface CatViewOutput {

    val catsFlow: Flow<List<Cat>>
    val favoriteCatsFlow: Flow<List<Cat>>

    fun getAllCats(page: Int)

    fun addToFavoriteCats(cat: Cat)

    fun removeFromFavoritesCats(cat: Cat)
}