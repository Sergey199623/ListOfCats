package com.belyakov.listofcats.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.belyakov.listofcats.data.database.Cat
import com.belyakov.listofcats.domain.CatInteractor

class CatPagingSource(
    private val catsInteractor: CatInteractor
) : PagingSource<Int, Cat>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> {
        val page = params.key ?: 0
        val pageSize = params.loadSize

        return try {
            val cats = catsInteractor.getCatListWithPagination(page, pageSize)
            LoadResult.Page(
                data = cats,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (cats.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Cat>): Int? {
        return null
    }
}