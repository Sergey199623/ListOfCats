package com.belyakov.listofcats

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

@OptIn(ExperimentalCoroutinesApi::class)
open class ViewModelTest {

    @get:Rule
    val testViewModelScopeRule = TestViewModelScopeRule()

    // Позволяет корректно работать с LiveData / Flow
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockkRule = MockKRule(this)
}