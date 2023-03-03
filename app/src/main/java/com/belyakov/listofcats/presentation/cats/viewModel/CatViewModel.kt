package com.belyakov.listofcats.presentation.cats.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belyakov.listofcats.data.database.Cat
import com.belyakov.listofcats.domain.CatInteractor
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

internal class CatViewModel(
    private val catsInteractor: CatInteractor
) : ViewModel(), CatViewOutput {

    override val catsFlow = MutableStateFlow<List<Cat>>(emptyList())
    override val favoriteCatsFlow = MutableStateFlow<List<Cat>>(emptyList())

    override fun getAllCats(page: Int) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable -> Timber.e(throwable) }
        viewModelScope.launch(exceptionHandler) {
            val newCats = catsInteractor.getCatList(page, 10)
            catsFlow.value = newCats
        }
    }

    override fun addToFavoriteCats(cat: Cat) {
        viewModelScope.launch {
            catsInteractor.addCatToFavorites(cat)
        }
    }

    override fun removeFromFavoritesCats(cat: Cat) {
        viewModelScope.launch {
            catsInteractor.removeCatFromFavorites(cat)
        }
    }
}