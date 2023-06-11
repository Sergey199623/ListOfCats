package com.belyakov.listofcats.data

import android.content.Context
import android.os.Environment
import com.belyakov.listofcats.data.database.Cat
import com.belyakov.listofcats.data.database.CatDatabase
import com.belyakov.listofcats.data.network.CatApi
import com.belyakov.listofcats.data.network.CatApiProvider
import com.belyakov.listofcats.domain.CatInteractor
import com.belyakov.listofcats.domain.utils.DownloadImageProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

internal class CatInteractorImpl(
    context: Context,
    private val catApiProvider: CatApiProvider,
    private val downloadImageProvider: DownloadImageProvider
) : CatInteractor {

    private val catDatabase = CatDatabase.getDatabase(context)
    private val catsDao = catDatabase.catDao()

    override suspend fun getCatList(page: Int, limit: Int): List<Cat> {
        // Получаем список котов с сервера и сохраняем в базу данных
        val catList = catApiProvider.provideCatApi().getCats(limit, page)
        catsDao.insertAll(catList)
        return catList
    }

    override fun getFavoriteCats(): Flow<List<Cat>> = catsDao.getFavoriteCats()

    override suspend fun changeFavoriteStatusCat(cat: Cat) {
        val newCat = cat.copy(isFavorite = cat.isFavorite)
        catsDao.update(newCat)
    }

    override suspend fun removeCatFromFavorites(cat: Cat) {
        // Удаляем кота из списка избранных в базе данных
        val newCat = cat.copy(isFavorite = !cat.isFavorite)
        catsDao.update(newCat)
    }

    override suspend fun downloadImage(url: String) = downloadImageProvider.downloadImage(url)
}