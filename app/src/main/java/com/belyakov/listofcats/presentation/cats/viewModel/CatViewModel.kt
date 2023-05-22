package com.belyakov.listofcats.presentation.cats.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belyakov.listofcats.data.database.Cat
import com.belyakov.listofcats.domain.CatInteractor
import com.belyakov.listofcats.navigation.Navigator
import com.belyakov.listofcats.presentation.favoriteCats.FavoriteCatListFragment
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

internal class CatViewModel(
    private val catsInteractor: CatInteractor,
    private val navigator: Navigator
) : ViewModel(), CatViewOutput {

    override val catsFlow = MutableSharedFlow<List<Cat>>()
    override val favoriteCatsFlow = MutableStateFlow<List<Cat>>(emptyList())
    override val progressBarFlow = MutableStateFlow(false)

    override fun onViewCreated(page: Int) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable -> Timber.e(throwable) }
        progressBarFlow.value = true
        viewModelScope.launch(exceptionHandler) {
            val newCats = catsInteractor.getCatList(page, 10)
            catsFlow.emit(newCats)
            delay(2000)
            progressBarFlow.value = false
        }
    }

    override fun onChangeFavoriteStatusCat(cat: Cat) {
        viewModelScope.launch {
            catsInteractor.changeFavoriteStatusCate(cat)
        }
    }

    override fun onFavoriteListClicked() {
        navigator.launch(FavoriteCatListFragment.FavoriteScreen())
    }
}