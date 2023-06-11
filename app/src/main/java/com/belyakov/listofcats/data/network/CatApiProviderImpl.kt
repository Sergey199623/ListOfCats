package com.belyakov.listofcats.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class CatApiProviderImpl : CatApiProvider {

    override fun provideCatApi(): CatApi {
        return Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatApi::class.java)
    }
}