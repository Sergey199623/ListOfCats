package com.belyakov.listofcats.presentation.favoriteCats.viewModel

//import com.belyakov.listofcats.TestViewModelScopeRule
//import com.belyakov.listofcats.R
//import com.belyakov.listofcats.data.database.Cat
//import com.belyakov.listofcats.domain.CatInteractor
//import com.belyakov.listofcats.navigation.Navigator
//import junit.framework.TestCase.assertEquals
//import junit.framework.TestCase.assertFalse
//import junit.framework.TestCase.assertTrue
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.advanceTimeBy
//import kotlinx.coroutines.test.runTest
//import org.junit.Rule
//import org.junit.Test
//import org.mockito.Mockito
//import org.mockito.kotlin.mock
//import org.mockito.kotlin.whenever
//
//@ExperimentalCoroutinesApi
//class FavoriteCatViewModelTest {
//
//    @get:Rule
//    val mainDispatcherRule = TestViewModelScopeRule()
//
//    private val navigator = mock<Navigator>()
//    private val catsInteractor = mock<CatInteractor>()
//
//    @Test
//    fun test_onRemoveFromFavoriteCats() {
//        val cat = Cat(id = "1", url = "http://example.com/cat1.jpg", isFavorite = true)
//        val viewModel = FavoriteCatViewModel(
//            navigator = navigator,
//            catsInteractor = catsInteractor
//        )
//        runTest {
//            Mockito.`when`(catsInteractor.removeCatFromFavorites(cat)).thenReturn(Unit)
//        }
//        viewModel.onRemoveFromFavoriteCats(cat)
//    }
//
//    @Test
//    fun test_onDownloadFavoriteCat() {
//        val testUrl = "http://example.com/cat1.jpg"
//        val testMessage = R.string.cats_has_been_downloaded
//        val viewModel = FavoriteCatViewModel(
//            navigator = navigator,
//            catsInteractor = catsInteractor
//        )
//
//        viewModel.onDownloadFavoriteCat(testUrl)
//
//        runTest {
//            viewModel.progressBarFlow.value = true
//            val progressBarBeforeTest = viewModel.progressBarFlow.value
//            assertTrue(progressBarBeforeTest)
//
//            val downloaded = true
//            whenever(catsInteractor.downloadImage(testUrl)).thenReturn(downloaded)
//
//            advanceTimeBy(1000)
//
//            assertEquals(downloaded, viewModel.downloadCatsFlow.value)
//
//            viewModel.progressBarFlow.value = false
//            val progressBarAfterTest = viewModel.progressBarFlow.value
//            assertFalse(progressBarAfterTest)
//
//            Mockito.doNothing().`when`(navigator).toast(testMessage)
//        }
//    }
//}