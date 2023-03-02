package com.belyakov.listofcats.data.network.ext

import com.belyakov.listofcats.data.network.BaseResponse
import com.belyakov.listofcats.data.network.ServerException

fun <U, T : BaseResponse<U>> T.mapResponseExceptions(): U {
    val result = this.result
    return if (error == BaseResponse.RESULT_SUCCESS && result != null) {
        result
    } else {
        throw ServerException(error, errorText)
    }
}