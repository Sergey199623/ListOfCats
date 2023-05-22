package com.belyakov.listofcats.presentation.favoriteCats.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belyakov.listofcats.data.database.Cat
import com.belyakov.listofcats.domain.CatInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

internal class FavoriteCatViewModel(
    private val catsInteractor: CatInteractor
) : ViewModel(), FavoriteCatViewOutput {

    override val favoriteCatsFlow: Flow<List<Cat>>
        get() = catsInteractor.getFavoriteCats()

    override val downloadCatsFlow = MutableStateFlow(false)
    override val progressBarFlow = MutableStateFlow(false)

    override fun onRemoveFromFavoriteCats(cat: Cat) {
        viewModelScope.launch {
            catsInteractor.removeCatFromFavorites(cat)
        }
    }

    override fun onDownloadFavoriteCat(url: String) {
        viewModelScope.launch {
            progressBarFlow.value = true
            downloadCatsFlow.value = catsInteractor.downloadImage(url)
            if (downloadCatsFlow.value) progressBarFlow.value = false
        }
    }
}