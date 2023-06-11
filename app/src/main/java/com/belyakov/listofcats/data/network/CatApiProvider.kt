package com.belyakov.listofcats.data.network

interface CatApiProvider {

    fun provideCatApi(): CatApi
}