package com.belyakov.listofcats.data.database

import com.belyakov.listofcats.data.database.Cat
import junit.framework.TestCase.assertEquals
import org.junit.Test

class CatTest {

    @Test
    fun `test Cat properties`() {
        val cat = Cat("1", "http://example.com/cat.jpg", true)

        assertEquals("1", cat.id)
        assertEquals("http://example.com/cat.jpg", cat.url)
        assertEquals(true, cat.isFavorite)
    }
}