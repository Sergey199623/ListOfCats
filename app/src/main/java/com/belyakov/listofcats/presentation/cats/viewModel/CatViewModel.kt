package com.belyakov.listofcats.presentation.cats.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.belyakov.listofcats.data.database.Cat
import com.belyakov.listofcats.domain.CatInteractor
import com.belyakov.listofcats.domain.paging.CatPagingSource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

internal class CatViewModel(
    private val catsInteractor: CatInteractor
) : ViewModel(), CatViewOutput {

    override val catsFlow = MutableStateFlow<List<Cat>>(emptyList())
    override val favoriteCatsFlow = MutableStateFlow<List<Cat>>(emptyList())

    private val pageSize = 10

    override val catsPagingFlow: Flow<PagingData<Cat>> = Pager(
        config = PagingConfig(pageSize = pageSize, enablePlaceholders = false),
        pagingSourceFactory = { CatPagingSource(catsInteractor) }
    ).flow.cachedIn(viewModelScope)

    override fun onChangeFavoriteStatusCat(cat: Cat) {
        viewModelScope.launch {
            catsInteractor.changeFavoriteStatusCate(cat)
        }
    }
}