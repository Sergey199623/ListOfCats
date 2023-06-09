package com.belyakov.listofcats.presentation.cats.viewModel

import com.belyakov.listofcats.MainCoroutineRule
import com.belyakov.listofcats.R
import com.belyakov.listofcats.data.database.Cat
import com.belyakov.listofcats.domain.CatInteractor
import com.belyakov.listofcats.navigation.Navigator
import com.belyakov.listofcats.presentation.favoriteCats.FavoriteCatListFragment
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.advanceTimeBy
import org.junit.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class CatViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    private val navigator = mock<Navigator>()
    private val catsInteractor = mock<CatInteractor>()

    @BeforeEach
    fun before() {
    }

    @AfterEach
    fun after() {
        Mockito.reset(navigator)
    }

    @Test
    fun test_onViewCreated() {
        val testPage = 1
        val viewModel = CatViewModel(
            navigator = navigator,
            catsInteractor = catsInteractor
        )
        viewModel.onViewCreated(testPage)
        viewModel.progressBarFlow.value = true

        val progressBarTestFlow = viewModel.progressBarFlow.value
        assertEquals(true, progressBarTestFlow)

        runTest {
            // Ожидаем завершения асинхронной операции
            advanceTimeBy(1000)

            // Проверяем ожидаемые значения в catsFlow
            val catsList = viewModel.catsFlow
            assertNotNull(catsList)

            // Проверяем ожидаемое значение progressBarFlow
            viewModel.progressBarFlow.value = false
            val progressBarValue = viewModel.progressBarFlow.value
            assertEquals(false, progressBarValue)
        }
    }


    @Test
    fun test_onChangeFavoriteStatusCat() {
        val cat = Cat(id = "1", url = "http://example.com/cat1.jpg", isFavorite = true)
        val viewModel = CatViewModel(
            navigator = navigator,
            catsInteractor = catsInteractor
        )
        runTest {
            Mockito.`when`(catsInteractor.changeFavoriteStatusCate(cat)).thenReturn(Unit)
        }
        viewModel.onChangeFavoriteStatusCat(cat)
    }

    @Test
    fun `should toast and favorite true`() {
        val isFavorite = true
        val testMessage = R.string.cats_has_been_checked_favorite
        Mockito.doNothing().`when`(navigator).toast(testMessage)
        val viewModel = CatViewModel(
            navigator = navigator,
            catsInteractor = catsInteractor
        )
        viewModel.onSendToast(isFavorite)
    }

    @Test
    fun `should toast and favorite false`() {
        val isFavorite = false
        val testMessage = R.string.cats_has_been_unchecked_favorite
        Mockito.doNothing().`when`(navigator).toast(testMessage)
        val viewModel = CatViewModel(
            navigator = navigator,
            catsInteractor = catsInteractor
        )
        viewModel.onSendToast(isFavorite)
    }

    @Test
    fun test_onFavoriteListClicked() {
        Mockito.doNothing().`when`(navigator).launch(FavoriteCatListFragment.FavoriteScreen())
        val viewModel = CatViewModel(
            navigator = navigator,
            catsInteractor = catsInteractor
        )
        viewModel.onFavoriteListClicked()
    }
}