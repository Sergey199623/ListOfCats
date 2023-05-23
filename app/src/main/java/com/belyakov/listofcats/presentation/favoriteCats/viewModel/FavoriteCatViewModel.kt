package com.belyakov.listofcats.presentation.favoriteCats.viewModel

import androidx.lifecycle.viewModelScope
import com.belyakov.listofcats.R
import com.belyakov.listofcats.base.BaseViewModel
import com.belyakov.listofcats.data.database.Cat
import com.belyakov.listofcats.domain.CatInteractor
import com.belyakov.listofcats.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

class FavoriteCatViewModel(
    private val navigator: Navigator,
) : BaseViewModel(){

    private val catsInteractor : CatInteractor by KoinJavaComponent.inject(CatInteractor::class.java)

    private val _downloadCatsFlow = MutableStateFlow(false)
    private val _progressBarFlow = MutableStateFlow(false)
    private val _favoriteCatsFlow = catsInteractor.getFavoriteCats()

    val downloadCatsFlow = _downloadCatsFlow
    val progressBarFlow = _progressBarFlow
    val favoriteCatsFlow = _favoriteCatsFlow

    fun onRemoveFromFavoriteCats(cat: Cat) {
        viewModelScope.launch {
            catsInteractor.removeCatFromFavorites(cat)
        }
    }

    fun onDownloadFavoriteCat(url: String) {
        viewModelScope.launch {
            progressBarFlow.value = true
            downloadCatsFlow.value = catsInteractor.downloadImage(url)
            if (downloadCatsFlow.value) {
                progressBarFlow.value = false
                navigator.toast(R.string.cats_has_been_downloaded)
            }
        }
    }
}