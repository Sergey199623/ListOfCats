package com.belyakov.listofcats.data

import android.app.Application
import android.os.Environment
import com.belyakov.listofcats.data.database.Cat
import com.belyakov.listofcats.data.database.CatDatabase
import com.belyakov.listofcats.data.network.CatApi
import com.belyakov.listofcats.domain.CatInteractor
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
    applicationContext: Application
) : CatInteractor {

    private val catDatabase = CatDatabase.getDatabase(applicationContext)
    private val catsDao = catDatabase.catDao()

    private val catApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CatApi::class.java)

    override suspend fun getCatList(page: Int, limit: Int): List<Cat> {
        // Получаем список котов с сервера и сохраняем в базу данных
        val catList = catApi.getCats(limit, page)
        catsDao.insertAll(catList)
        return catList
    }

    override fun getFavoriteCats(): Flow<List<Cat>> = catsDao.getFavoriteCats()

    override suspend fun changeFavoriteStatusCate(cat: Cat) {
        val newCat = cat.copy(isFavorite = cat.isFavorite)
        catsDao.update(newCat)
    }

    override suspend fun removeCatFromFavorites(cat: Cat) {
        // Удаляем кота из списка избранных в базе данных
        val newCat = cat.copy(isFavorite = !cat.isFavorite)
        catsDao.update(newCat)
    }

    override suspend fun downloadImage(url: String) = withContext(Dispatchers.IO) {
        delay(5000)
        try {
            val imageUrl = URL(url)
            val connection = imageUrl.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream

            val filename = "cat_${System.currentTimeMillis()}.jpg"
            val directory =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(directory, filename)
            val output = FileOutputStream(file)

            val buffer = ByteArray(1024)
            var bytesRead = input.read(buffer)
            while (bytesRead != -1) {
                output.write(buffer, 0, bytesRead)
                bytesRead = input.read(buffer)
            }

            output.close()
            input.close()

            true
        } catch (e: Exception) {
            false
        }
    }

    private companion object {
        const val BASE_URL = "https://api.thecatapi.com/v1/"
    }
}