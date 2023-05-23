package com.belyakov.listofcats.data.network

import com.belyakov.listofcats.data.database.Cat
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {
    @GET("images/search")
    suspend fun getCats(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): List<Cat>
}