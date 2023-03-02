package com.belyakov.listofcats.domain

import com.belyakov.listofcats.data.database.Cat
import com.belyakov.listofcats.data.database.CatDao
import com.belyakov.listofcats.data.network.CatApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CatInteractorImpl(
    private val catsDao: CatDao
) : CatInteractor {

    private val catApi = Retrofit.Builder()
        .baseUrl("https://api.thecatapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CatApi::class.java)

    override suspend fun getCats(limit: Int, page: Int) = catApi.getCats(limit, page)

    override suspend fun getCatList(page: Int, limit: Int): List<Cat> {
        // Получаем список котов с сервера и сохраняем в базу данных
        val catList = catApi.getCats(limit, page)
        catsDao.insertAll(catList)
        return catList
    }

    override fun getFavoriteCats(): Flow<List<Cat>> = catsDao.getFavoriteCats()

    override suspend fun addCatToFavorites(cat: Cat) {
        // Добавляем кота в список избранных в базе данных
        cat.isFavorite = true
        catsDao.update(cat)
    }

    override suspend fun removeCatFromFavorites(cat: Cat) {
        // Удаляем кота из списка избранных в базе данных
        cat.isFavorite = false
        catsDao.update(cat)
    }
}