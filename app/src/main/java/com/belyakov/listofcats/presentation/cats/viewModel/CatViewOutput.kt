package com.belyakov.listofcats.presentation.cats.viewModel

import androidx.paging.PagingData
import com.belyakov.listofcats.data.database.Cat
import kotlinx.coroutines.flow.Flow

interface CatViewOutput {

    val catsFlow: Flow<List<Cat>>
    val favoriteCatsFlow: Flow<List<Cat>>
    val catsPagingFlow: Flow<PagingData<Cat>>

    fun onChangeStatusFavoriteCate(cat: Cat)
}