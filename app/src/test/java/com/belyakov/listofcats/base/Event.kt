package com.belyakov.listofcats.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Rule
import org.junit.Test

class EventTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun getReturnsValueOnlyOnce() {
        val event = Event("some-event")

        val value1 = event.getValue()
        val value2 = event.getValue()

        assertEquals("some-event", value1)
        assertNull(value2)
    }
}