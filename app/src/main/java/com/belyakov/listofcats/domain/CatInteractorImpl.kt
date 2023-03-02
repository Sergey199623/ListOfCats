package com.belyakov.listofcats.domain

import com.belyakov.listofcats.data.network.CatApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CatInteractorImpl : CatInteractor {

    private val catApi = Retrofit.Builder()
        .baseUrl("https://api.thecatapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CatApi::class.java)

    override suspend fun getCats(limit: Int, page: Int) = catApi.getCats(limit, page)
}