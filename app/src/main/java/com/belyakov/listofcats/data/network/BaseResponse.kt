package com.belyakov.listofcats.data.network

import com.fasterxml.jackson.annotation.JsonProperty

abstract class BaseResponse<T>(delegate: Map<String, Any>) {

    companion object {
        const val RESULT_SUCCESS = 0
    }

    @JsonProperty
    val error: Int

    @JsonProperty
    val errorText: String

    @JsonProperty
    val ts: String

    @JsonProperty
    var result: T? = null

    @JsonProperty
    val b: String

    init {
        error = delegate["error"].toString().toInt()
        if (error == RESULT_SUCCESS) {
            val data = delegate["result"]
            result = data?.let { this.handleResult(it) }
        }

        errorText = (delegate["errorText"] as String?).orEmpty()
        ts = delegate["ts"] as String
        b = delegate["b"] as String
    }

    protected abstract fun handleResult(result: Any): T
}