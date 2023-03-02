package com.belyakov.listofcats.domain

import com.belyakov.listofcats.data.database.Cat

interface CatInteractor {

    suspend fun getCats(limit: Int, page: Int): List<Cat>
}