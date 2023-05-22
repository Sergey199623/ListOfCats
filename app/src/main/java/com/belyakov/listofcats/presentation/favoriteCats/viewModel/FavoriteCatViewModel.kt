package com.belyakov.listofcats.presentation.favoriteCats.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belyakov.listofcats.data.database.Cat
import com.belyakov.listofcats.domain.CatInteractor
import com.belyakov.listofcats.domain.DownloadProgressCallback
import com.belyakov.listofcats.navigation.Navigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

internal class FavoriteCatViewModel(
    private val catsInteractor: CatInteractor,
    private val navigator: Navigator
) : ViewModel(), FavoriteCatViewOutput, DownloadProgressCallback {

    override val favoriteCatsFlow: Flow<List<Cat>>
        get() = catsInteractor.getFavoriteCats()

    override val downloadCatsFlow = MutableStateFlow(false)
    override val downloadProgressFlow = MutableStateFlow<Int>(0)
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

    override fun onProgressUpdated(progress: Int) {
        downloadProgressFlow.value = progress
    }
}