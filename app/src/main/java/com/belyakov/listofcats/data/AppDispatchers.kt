package com.belyakov.listofcats.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

data class AppDispatchers(
    val IO: CoroutineDispatcher = Dispatchers.IO
)