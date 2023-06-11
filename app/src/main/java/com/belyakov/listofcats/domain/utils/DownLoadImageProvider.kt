package com.belyakov.listofcats.domain.utils

interface DownloadImageProvider {

    suspend fun downloadImage(url: String) : Boolean
}