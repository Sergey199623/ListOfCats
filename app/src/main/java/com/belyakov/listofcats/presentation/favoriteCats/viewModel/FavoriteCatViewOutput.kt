package com.belyakov.listofcats.presentation.favoriteCats.viewModel

import com.belyakov.listofcats.data.database.Cat
import kotlinx.coroutines.flow.Flow

interface FavoriteCatViewOutput {

    val favoriteCatsFlow: Flow<List<Cat>>

    fun removeFromFavoritesCats(cat: Cat)
}