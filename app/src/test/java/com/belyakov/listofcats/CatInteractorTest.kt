package com.belyakov.listofcats

import android.content.Context
import com.belyakov.listofcats.data.CatInteractorImpl
import com.belyakov.listofcats.data.database.Cat
import com.belyakov.listofcats.data.database.CatDao
import com.belyakov.listofcats.data.network.CatApiProvider
import com.belyakov.listofcats.domain.CatInteractor
import com.belyakov.listofcats.domain.utils.DownloadImageProvider
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CatInteractorTest {

    @get:Rule
    val rule = MockKRule(testSubject = this)

    @RelaxedMockK
    private val catDaoMock: CatDao = mockk()

    @Test
    fun justReturnListOfCat() = runTest {
        val page = 1
        val limit = 10

        val catApiProvider = mockk<CatApiProvider>(relaxed = true)
        val mockData = listOf(Cat(id = "1", url = "https://example.com"))

        coEvery { catApiProvider.provideCatApi().getCats(any(), any()) } returns mockData

        val listCats = catApiProvider.provideCatApi().getCats(page, limit)

        coVerify(exactly = 1) { catApiProvider.provideCatApi().getCats(any(), any()) }
        coEvery { catDaoMock.insertAll(listCats) } just runs
        catDaoMock.insertAll(listCats)

        coVerify(exactly = 1) { catDaoMock.insertAll(listCats) }

        Assert.assertSame(mockData, listCats)
    }

    @Test
    fun justReturnFavoriteListOfCat() {
        val cat1 = Cat(
            id = "1",
            url = "https://example.com"
        )
        val cat2 = Cat(
            id = "2",
            url = "https://example.com"
        )
        val mockData = flowOf(listOf(cat1, cat2))

        coEvery { catDaoMock.getFavoriteCats() } returns mockData
        val listOfFavoriteCats = catDaoMock.getFavoriteCats()

        coVerify(exactly = 1) { catDaoMock.getFavoriteCats() }

        Assert.assertSame(mockData, listOfFavoriteCats)
    }

    @Test
    fun justChangeFavoriteStatusOfCat() = runTest {
        val cat1 = Cat(
            id = "1",
            url = "https://example.com",
            isFavorite = false
        )

        val cat2 = cat1.copy(
            isFavorite = true
        )

        coEvery { catDaoMock.update(cat2) } just runs
        catDaoMock.update(cat2)
        coVerify { catDaoMock.update(cat2) }
    }

    @Test
    fun justRemoveCatFromFavorites() = runTest {
        val cat1 = Cat(
            id = "1",
            url = "https://example.com",
            isFavorite = true
        )

        val cat2 = cat1.copy(
            isFavorite = false
        )

        coEvery { catDaoMock.update(cat2) } just runs
        catDaoMock.update(cat2)
        coVerify { catDaoMock.update(cat2) }
    }

    @Test
    fun justReturnBooleanFromDownloadImageMethod() = runTest {
        val downloadImageProvider : DownloadImageProvider = mockk(relaxed = true)
        val valueBeforeDownload = true
        val url = "https://example.com"

        coEvery { downloadImageProvider.downloadImage(url) } returns true

        val valueAfterDownload = downloadImageProvider.downloadImage(url)

        coVerify(exactly = 1) { downloadImageProvider.downloadImage(url) }

        Assert.assertSame(valueBeforeDownload, valueAfterDownload)
    }

//    private fun createCatInteractor(
//        context: Context = mockk(relaxed = true),
//        catApiProvider: CatApiProvider = mockk()
//    ): CatInteractor = CatInteractorImpl(
//        context,
//        catApiProvider
//    )
}