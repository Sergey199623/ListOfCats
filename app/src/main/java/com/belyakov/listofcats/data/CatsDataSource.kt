package com.belyakov.listofcats.data


import androidx.paging.PagingSource
import com.belyakov.listofcats.data.database.Cat
import com.belyakov.listofcats.data.network.CatApi

//class CatDataSource(private val apiService: CatApi) : PagingSource<Int, Cat>() {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> {
//        val page = params.key ?: 1
//        val limit = params.loadSize
//
//        return try {
//            val response = apiService.getCats(page, limit)
//            val cats = response.map { catResponse ->
//                Cat(catResponse.id, catResponse.url)
//            }
//            LoadResult.Page(
//                data = cats,
//                prevKey = if (page == 1) null else page - 1,
//                nextKey = if (cats.isEmpty()) null else page + 1
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//}