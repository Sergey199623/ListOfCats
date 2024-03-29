package com.belyakov.listofcats

import com.belyakov.listofcats.domain.CatInteractor
import com.belyakov.listofcats.data.CatInteractorImpl
import com.belyakov.listofcats.data.DownloadImageProviderImpl
import com.belyakov.listofcats.data.network.CatApiProvider
import com.belyakov.listofcats.data.network.CatApiProviderImpl
import com.belyakov.listofcats.domain.utils.DownloadImageProvider
import com.belyakov.listofcats.presentation.cats.viewModel.CatViewModel
import com.belyakov.listofcats.presentation.favoriteCats.viewModel.FavoriteCatViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object CatsModule {

    fun create() = module {
        viewModel { CatViewModel(get(), get()) }
        viewModel { FavoriteCatViewModel(get(), get()) }

        single<CatInteractor> { CatInteractorImpl(get(), get(), get()) }
        single<CatApiProvider> { CatApiProviderImpl() }
        single<DownloadImageProvider> { DownloadImageProviderImpl() }
    }
}