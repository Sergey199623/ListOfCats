package com.belyakov.listofcats.presentation.cats.viewModel

import com.belyakov.listofcats.R
import com.belyakov.listofcats.ViewModelTest
import com.belyakov.listofcats.data.database.Cat
import com.belyakov.listofcats.domain.CatInteractor
import com.belyakov.listofcats.navigation.Navigator
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.just
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CatViewModelTest : ViewModelTest() {

    @InjectMockKs
    lateinit var viewModel: CatViewModel

    @RelaxedMockK
    lateinit var navigator: Navigator

    @RelaxedMockK
    lateinit var catsInteractor: CatInteractor

    private lateinit var catsFlow: MutableSharedFlow<List<Cat>>
    private lateinit var progressBarFlow: MutableStateFlow<Boolean>

    @Before
    fun setUp() {
        progressBarFlow = MutableStateFlow(false)
        catsFlow = MutableStateFlow(emptyList())
    }

    @Test
    fun test_onViewCreated() = runTest {
        val page = 1
        val limit = 10
        val listOfCats = listOf(
            Cat(id = "1", url = "http://example.com/cat1.jpg"),
            Cat(id = "2", url = "http://example.com/cat1.jpg")
        )
        coEvery { catsInteractor.getCatList(page, limit) } returns listOfCats
        viewModel.onViewCreated(page)

//        verify(exactly = 1) { viewModel.onViewCreated(page) }

        progressBarFlow.value = true
        assertTrue(progressBarFlow.value)

        advanceTimeBy(1000)

        // Todo разобраться
        catsFlow.emit(listOfCats)
//            coVerify { catsFlow.emit(listOfCats) }

        progressBarFlow.value = false
        assertFalse(progressBarFlow.value)
    }

    @Test
    fun test_onSendToast() {
        val isFavoriteFirstValue = true
        val isFavoriteSecondValue = false

        val testMessage = R.string.cats_has_been_checked_favorite

        assertEquals(true, isFavoriteFirstValue)
        assertEquals(false, isFavoriteSecondValue)

        viewModel.onSendToast(isFavoriteFirstValue)
        verify { viewModel.onSendToast(isFavoriteFirstValue) }

        navigator.toast(testMessage)
        coEvery { navigator.toast(testMessage) } just Runs
    }

    @Test
    fun test_onChangeFavoriteStatusCat() = runTest {
        val cat = Cat(id = "1", url = "http://example.com/cat1.jpg", isFavorite = true)

        coEvery { catsInteractor.changeFavoriteStatusCat(cat) } just Runs

        viewModel.onChangeFavoriteStatusCat(cat)

        coVerify { catsInteractor.changeFavoriteStatusCat(cat) }
    }

    @Test
    fun test_onFavoriteListClicked() {
        coEvery { navigator.launch(any()) } just Runs

        viewModel.onFavoriteListClicked()

        verify { navigator.launch(any()) }
    }
}