package com.belyakov.listofcats.data.network

import java.io.IOException

class ServerException(
    val statusCode: Int,
    val errorMessage: String?
) : IOException("statusCode: $statusCode, errorMessage: $errorMessage")