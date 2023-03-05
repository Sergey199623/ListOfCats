package com.belyakov.listofcats.presentation.cats.viewModel

import com.belyakov.listofcats.data.database.Cat
import kotlinx.coroutines.flow.Flow

interface CatViewOutput {

    val catsFlow: Flow<List<Cat>>
    val favoriteCatsFlow: Flow<List<Cat>>

    fun onViewCreated(page: Int)

    fun onChangeFavoriteStatusCat(cat: Cat)
}