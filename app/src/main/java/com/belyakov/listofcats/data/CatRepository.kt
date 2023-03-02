package com.belyakov.listofcats.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.belyakov.listofcats.data.database.Cat
import com.belyakov.listofcats.data.network.CatApi
import kotlinx.coroutines.flow.Flow

//class CatRepository(private val catApi: CatApi) {
//
//    fun getCats(): Flow<PagingData<Cat>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = 10,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = { CatDataSource(catApi) }
//        ).flow
//    }
//}