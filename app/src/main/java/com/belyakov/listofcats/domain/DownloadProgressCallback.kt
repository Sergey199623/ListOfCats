package com.belyakov.listofcats.domain

interface DownloadProgressCallback {
    fun onProgressUpdated(progress: Int)
}