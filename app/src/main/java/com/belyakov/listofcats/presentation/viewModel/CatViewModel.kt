package com.belyakov.listofcats.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belyakov.listofcats.data.database.Cat
import com.belyakov.listofcats.data.database.CatDatabase
import com.belyakov.listofcats.domain.CatInteractor
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

internal class CatViewModel(
    applicationContext: Application,
    private val catsInteractor: CatInteractor
) : ViewModel(), CatViewOutput {

    override val catsFlow = MutableStateFlow<List<Cat>>(emptyList())
    override val favoriteCatsFlow = MutableStateFlow<List<Cat>>(emptyList())

    private val catDatabase = CatDatabase.getDatabase(applicationContext)
    private val catDao = catDatabase.catDao()

    override fun getAllCats(page: Int) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable -> Timber.e(throwable) }
        viewModelScope.launch(exceptionHandler) {
            val newCats = catsInteractor.getCats(10, page)
            catsFlow.value = newCats
        }
    }

    override fun addToFavoriteCats(cat: Cat) {
        viewModelScope.launch {
            catDao.insert(cat)
        }
    }

    override fun removeFromFavoritesCats(cat: Cat) {
        viewModelScope.launch {
            catDao.delete(cat)
        }
    }
}