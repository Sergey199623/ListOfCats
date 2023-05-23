package com.belyakov.listofcats.presentation.cats.viewModel

import androidx.lifecycle.viewModelScope
import com.belyakov.listofcats.R
import com.belyakov.listofcats.base.BaseViewModel
import com.belyakov.listofcats.data.database.Cat
import com.belyakov.listofcats.domain.CatInteractor
import com.belyakov.listofcats.navigation.Navigator
import com.belyakov.listofcats.presentation.cats.CatsListFragment
import com.belyakov.listofcats.presentation.favoriteCats.FavoriteCatListFragment
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import timber.log.Timber

class CatViewModel(
    private val navigator: Navigator,
) : BaseViewModel() {

    private val catsInteractor : CatInteractor by inject(CatInteractor::class.java)

    private val _catsFlow = MutableSharedFlow<List<Cat>>()
    private val _progressBarFlow = MutableStateFlow(false)

    val catsFlow = _catsFlow
    val progressBarFlow = _progressBarFlow


     fun onViewCreated(page: Int) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable -> Timber.e(throwable) }
        progressBarFlow.value = true
        viewModelScope.launch(exceptionHandler) {
            val newCats = catsInteractor.getCatList(page, 10)
            catsFlow.emit(newCats)
            delay(1000)
            progressBarFlow.value = false
        }
    }

    fun onChangeFavoriteStatusCat(cat: Cat) {
        viewModelScope.launch {
            catsInteractor.changeFavoriteStatusCate(cat)
        }
    }

    fun onSendToast(isFavorite: Boolean) {
        val message =
            if (isFavorite) R.string.cats_has_been_checked_favorite else R.string.cats_has_been_unchecked_favorite
        navigator.toast(message)
    }

    fun onFavoriteListClicked() {
        navigator.launch(FavoriteCatListFragment.FavoriteScreen())
    }
}